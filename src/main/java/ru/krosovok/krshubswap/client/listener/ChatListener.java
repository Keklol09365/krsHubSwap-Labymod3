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

    /**
     * Обработка сообщения из чата
     */
    public void onMessage(String message) {
        if (message != null && message.contains("уже подключен")) {
            this.teleportManager.onMessage(message);
        }
    }
}
