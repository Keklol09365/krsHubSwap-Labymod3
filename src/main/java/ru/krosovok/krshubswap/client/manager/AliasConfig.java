package ru.krosovok.krshubswap.client.manager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Управление алиасами команд
 */
public class AliasConfig {

    private static final String CONFIG_FILE_NAME = "krshubswap-aliases.txt";
    private static final List<String> DEFAULT_ALIASES = List.of("lt", "an");
    private final List<String> aliases = new ArrayList<>();

    public AliasConfig() {
        this.load();
    }

    public List<String> getAliases() {
        return new ArrayList<>(this.aliases);
    }

    public void setAliases(List<String> newAliases) {
        this.aliases.clear();
        Set<String> seen = new LinkedHashSet<>();

        for (String raw : newAliases) {
            String alias = sanitize(raw);
            if (!alias.isEmpty()) {
                seen.add(alias);
            }
        }

        this.aliases.addAll(seen);
        if (this.aliases.isEmpty()) {
            this.aliases.addAll(DEFAULT_ALIASES);
        }

        this.save();
    }

    private static String sanitize(String raw) {
        if (raw == null) {
            return "";
        }
        String s = raw.trim();
        while (s.startsWith("/")) {
            s = s.substring(1);
        }
        return s.replaceAll(" ", "");
    }

    private void load() {
        this.aliases.clear();
        try {
            Path configPath = Paths.get(CONFIG_FILE_NAME);
            if (Files.exists(configPath)) {
                List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
                Set<String> seen = new LinkedHashSet<>();
                for (String line : lines) {
                    String alias = sanitize(line);
                    if (!alias.isEmpty()) {
                        seen.add(alias);
                    }
                }
                this.aliases.addAll(seen);
            }
        } catch (IOException ignored) {
        }

        if (this.aliases.isEmpty()) {
            this.aliases.addAll(DEFAULT_ALIASES);
        }
    }

    public void save() {
        try {
            Path configPath = Paths.get(CONFIG_FILE_NAME);
            Files.write(configPath, this.aliases, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }
}
