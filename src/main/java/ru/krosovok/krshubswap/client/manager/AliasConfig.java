package ru.krosovok.krshubswap.client.manager;

import java.io.*;
import java.util.*;

public class AliasConfig {
    private static AliasConfig instance;
    private Map<String, String> aliases;
    
    private AliasConfig() {
        this.aliases = new HashMap<>();
        loadDefaultAliases();
    }
    
    public static AliasConfig getInstance() {
        if (instance == null) {
            instance = new AliasConfig();
        }
        return instance;
    }
    
    private void loadDefaultAliases() {
        aliases.put("lt", "lt");
        aliases.put("an", "an");
    }
    
    public String getAlias(String name) {
        return aliases.getOrDefault(name.toLowerCase(), name);
    }
    
    public Map<String, String> getAllAliases() {
        return new HashMap<>(aliases);
    }
}