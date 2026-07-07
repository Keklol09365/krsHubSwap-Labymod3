package ru.krosovok.krshubswap.client;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.gameplay.ReceiveChatEvent;
import net.labymod.api.event.client.tick.ClientTickEvent;
import ru.krosovok.krshubswap.client.listener.ChatListener;
import ru.krosovok.krshubswap.client.manager.TeleportManager;
import ru.krosovok.krshubswap.client.manager.RangeConfig;
import ru.krosovok.krshubswap.client.manager.AliasConfig;

/**
 * Основной класс для управления клиентской логикой
 */
public class KrshubswapClient {

    private static KrshubswapClient instance;
    private TeleportManager teleportManager;
    private RangeConfig rangeConfig;
    private AliasConfig aliasConfig;
    private ChatListener chatListener;

    public KrshubswapClient() {
        this.rangeConfig = new RangeConfig();
        this.aliasConfig = new AliasConfig();
        this.teleportManager = new TeleportManager();
        this.chatListener = new ChatListener(this.teleportManager);
        instance = this;
    }

    public static KrshubswapClient getInstance() {
        return instance;
    }

    @Subscribe
    public void onClientTick(ClientTickEvent event) {
        this.teleportManager.tick();
    }

    @Subscribe
    public void onChatMessage(ReceiveChatEvent event) {
        String message = event.getMessage().getUnformattedText();
        this.chatListener.onMessage(message);
    }

    public TeleportManager getTeleportManager() {
        return this.teleportManager;
    }

    public RangeConfig getRangeConfig() {
        return this.rangeConfig;
    }

    public AliasConfig getAliasConfig() {
        return this.aliasConfig;
    }
}
