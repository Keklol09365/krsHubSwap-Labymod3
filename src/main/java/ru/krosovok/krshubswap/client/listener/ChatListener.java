package ru.krosovok.krshubswap.client.listener;

import ru.krosovok.krshubswap.client.manager.TeleportManager;

/**
 * Слушатель сообщений чата для отслеживания статуса телепортирования
 */
public class ChatListener {

    private final TeleportManager teleportManager;

    public ChatListener(TeleportManager teleportManager) {
        this.teleportManager = teleportManager;
    }

    public void onMessage(String message) {
        if (message.contains("уже подключен")) {
            this.teleportManager.onMessage(message);
        }
    }
}
