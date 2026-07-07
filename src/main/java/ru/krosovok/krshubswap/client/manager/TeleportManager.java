package ru.krosovok.krshubswap.client.manager;

import java.util.Queue;
import java.util.LinkedList;

public class TeleportManager {
    private static final int TIMEOUT = 200;
    private State state = State.IDLE;
    private int tickCounter = 0;
    private int targetNumber = 0;
    private String targetCategory = "";
    private String targetKey = "";
    
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
    
    public String startTeleport(int number) {
        if (this.isBusy()) {
            return "Уже выполняется переход, дождитесь его завершения";
        }
        
        RangeConfig ranges = RangeConfig.getInstance();
        if (!ranges.isValid(number)) {
            return "Лайт анархии #" + number + " не существует (доступно 1-" + ranges.getMaxNumber() + ")";
        }
        
        this.targetNumber = number;
        this.targetCategory = ranges.getCategoryOf(number);
        this.targetKey = ranges.getKeyOf(number);
        this.state = State.WAITING_HUB_WORLD;
        this.tickCounter = 0;
        
        System.out.println("[HubSwap] Начинаю телепортирование на анархию #" + number);
        return null;
    }
    
    public void onMessage(String text) {
        if (this.state == State.WAITING_HUB_WORLD && text.contains("уже подключен")) {
            System.out.println("[HubSwap] Получено сообщение, переходим на меню");
            this.state = State.WAITING_MENU_1;
            this.tickCounter = 0;
        }
    }
    
    public void tick() {
        if (this.state == State.IDLE) {
            return;
        }
        
        this.tickCounter++;
        if (this.tickCounter > TIMEOUT) {
            System.err.println("[HubSwap] Ошибка: истекло время ожидания");
            this.reset();
            return;
        }
    }
    
    private void reset() {
        this.state = State.IDLE;
        this.tickCounter = 0;
        this.targetNumber = 0;
        this.targetCategory = "";
        this.targetKey = "";
    }
}