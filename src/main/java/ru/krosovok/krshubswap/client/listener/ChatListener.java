package ru.krosovok.krshubswap.client.listener;

import ru.krosovok.krshubswap.client.manager.TeleportManager;

public class ChatListener {
    private final TeleportManager teleportManager;
    
    public ChatListener(TeleportManager teleportManager) {
        this.teleportManager = teleportManager;
    }
    
    public void onMessage(String message) {
        if (message != null && message.contains("уже подключен")) {
            this.teleportManager.onMessage(message);
        }
    }
}