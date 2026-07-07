package ru.krosovok.krshubswap.client.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Управление телепортированием между лайт анархиями
 */
public class TeleportManager {

    private static final int TIMEOUT = 200; // тики
    private State state = State.IDLE;
    private int tickCounter = 0;
    private int targetNumber = 0;
    private String targetCategory = "";
    private String targetKey = "";
    private Object previousWorld = null;

    public enum State {
        IDLE,
        WAITING_HUB_WORLD,
        WAITING_MENU_1,
        WAITING_MENU_2,
        COMPLETED
    }

    public boolean isBusy() {
        return this.state != State.IDLE;
    }

    /**
     * Начать процесс телепортирования
     */
    public String startTeleport(int number) {
        if (this.isBusy()) {
            return "Уже выполняется переход, дождитесь его завершения";
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null || client.getNetworkHandler() == null) {
            return "Вы не подключены к серверу";
        }

        RangeConfig ranges = RangeConfig.getInstance();
        if (!ranges.isValid(number)) {
            return "Лайт анархии #" + number + " не существует (доступно 1-" + ranges.getMaxNumber() + ")";
        }

        this.targetNumber = number;
        this.targetCategory = ranges.getCategoryOf(number);
        this.targetKey = ranges.getKeyOf(number);
        this.previousWorld = client.world;
        this.state = State.WAITING_HUB_WORLD;
        this.tickCounter = 0;

        // Отправляем команду для перехода на хаб
        if (client.player != null) {
            client.player.sendChatMessage("/hub");
        }

        return null; // успех
    }

    /**
     * Обработка сообщений чата
     */
    public void onMessage(String text) {
        if (this.state == State.WAITING_HUB_WORLD && text.contains("уже подключен")) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.player != null) {
                client.player.sendChatMessage("/lite");
                this.state = State.WAITING_MENU_1;
                this.tickCounter = 0;
            }
        }
    }

    /**
     * Обновление каждый тик
     */
    public void tick() {
        if (this.state == State.IDLE) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null || client.getNetworkHandler() == null) {
            this.reset();
            return;
        }

        this.tickCounter++;

        if (this.tickCounter > TIMEOUT) {
            this.sendError(client, "Не удалось выполнить переход на Лайт #" + this.targetNumber + " (истекло время ожидания)");
            this.reset();
            return;
        }

        switch (this.state) {
            case WAITING_HUB_WORLD:
                this.waitForHubWorld(client);
                break;
            case WAITING_MENU_1:
                this.scanMenu(client, "advancedserverselecter:server-type", this.targetCategory, true);
                break;
            case WAITING_MENU_2:
                this.scanMenu(client, "advancedserverselecter:server", this.targetKey, false);
                break;
        }
    }

    private void waitForHubWorld(MinecraftClient client) {
        if (client.world != null && client.world != this.previousWorld) {
            if (client.player != null) {
                client.player.sendChatMessage("/lite");
            }
            this.state = State.WAITING_MENU_1;
            this.tickCounter = 0;
        }
    }

    private void scanMenu(MinecraftClient client, String nbtKey, String expectedValue, boolean isFirstMenu) {
        // Этот метод требует работы с контейнером (инвентарем)
        // Упрощенная версия для Labymod 3
        if (isFirstMenu) {
            this.state = State.WAITING_MENU_2;
        } else {
            this.state = State.COMPLETED;
        }
        this.tickCounter = 0;
    }

    private void sendError(MinecraftClient client, String message) {
        if (client.player != null) {
            client.player.sendSystemMessage(
                Text.literal("[HubSwap] " + message).formatted(Formatting.RED)
            );
        }
    }

    private void reset() {
        this.state = State.IDLE;
        this.tickCounter = 0;
        this.targetNumber = 0;
        this.targetCategory = "";
        this.targetKey = "";
        this.previousWorld = null;
    }
}
