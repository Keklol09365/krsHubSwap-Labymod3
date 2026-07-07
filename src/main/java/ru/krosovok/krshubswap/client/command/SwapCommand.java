package ru.krosovok.krshubswap.client.command;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import ru.krosovok.krshubswap.client.manager.TeleportManager;
import ru.krosovok.krshubswap.client.manager.AliasConfig;
import ru.krosovok.krshubswap.client.manager.RangeConfig;

/**
 * Обработчик команды swap
 */
public class SwapCommand {

    private final TeleportManager teleportManager;
    private final AliasConfig aliasConfig;

    public SwapCommand(TeleportManager teleportManager, AliasConfig aliasConfig) {
        this.teleportManager = teleportManager;
        this.aliasConfig = aliasConfig;
    }

    public void handleCommand(String fullCommand, String alias) {
        String[] parts = fullCommand.split(" ");
        if (parts.length < 2) {
            this.sendMessage(
                "Использование: /" + alias + " <номер 1-" + RangeConfig.getInstance().getMaxNumber() + ">",
                Formatting.YELLOW
            );
            return;
        }

        try {
            int number = Integer.parseInt(parts[1]);
            String error = this.teleportManager.startTeleport(number);
            if (error != null) {
                this.sendMessage(error, Formatting.RED);
            } else {
                this.sendMessage(
                    "Телепортирование на " + RangeConfig.getInstance().getNameOf(number),
                    Formatting.GREEN
                );
            }
        } catch (NumberFormatException e) {
            this.sendMessage("Укажите корректный номер анархии", Formatting.RED);
        }
    }

    private void sendMessage(String message, Formatting color) {
        Text text = Text.literal("[HubSwap] " + message).formatted(color);
        // Отправка сообщения в чат
    }
}
