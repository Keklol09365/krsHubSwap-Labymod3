package ru.krosovok.krshubswap.client;

import ru.krosovok.krshubswap.client.manager.TeleportManager;
import ru.krosovok.krshubswap.client.manager.RangeConfig;
import ru.krosovok.krshubswap.client.manager.AliasConfig;
import ru.krosovok.krshubswap.client.listener.ChatListener;

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
        if (instance == null) {
            instance = new KrshubswapClient();
        }
        return instance;
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