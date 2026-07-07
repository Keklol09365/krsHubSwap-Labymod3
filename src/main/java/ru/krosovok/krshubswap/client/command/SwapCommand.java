package ru.krosovok.krshubswap.client.command;

import ru.krosovok.krshubswap.client.manager.TeleportManager;
import ru.krosovok.krshubswap.client.manager.AliasConfig;
import ru.krosovok.krshubswap.client.manager.RangeConfig;

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
            System.out.println("[HubSwap] Использование: /" + alias + " <номер 1-" + RangeConfig.getInstance().getMaxNumber() + ">");
            return;
        }
        
        try {
            int number = Integer.parseInt(parts[1]);
            String error = this.teleportManager.startTeleport(number);
            if (error != null) {
                System.err.println("[HubSwap] " + error);
            } else {
                System.out.println("[HubSwap] Телепортирование на " + RangeConfig.getInstance().getNameOf(number));
            }
        } catch (NumberFormatException e) {
            System.err.println("[HubSwap] Укажите корректный номер анархии");
        }
    }
}