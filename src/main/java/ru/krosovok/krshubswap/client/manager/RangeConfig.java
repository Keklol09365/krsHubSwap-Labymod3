package ru.krosovok.krshubswap.client.manager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Управление конфигурацией диапазонов анархий
 */
public class RangeConfig {

    private static RangeConfig instance;
    private static final String CONFIG_FILE_NAME = "krshubswap-ranges.txt";
    private final List<RangeEntry> ranges = new ArrayList<>();
    private int total = 66;

    public static synchronized RangeConfig getInstance() {
        if (instance == null) {
            instance = new RangeConfig();
        }
        return instance;
    }

    public RangeConfig() {
        this.resetDefaults();
        this.load();
    }

    private void resetDefaults() {
        this.ranges.clear();
        this.ranges.add(new RangeEntry("solo", "Соло", 1, 15));
        this.ranges.add(new RangeEntry("duo", "Дуо", 16, 31));
        this.ranges.add(new RangeEntry("trio", "Трио", 32, 47));
        this.ranges.add(new RangeEntry("clans", "Кланы", 48, 66));
        this.total = 66;
    }

    public boolean isValid(int number) {
        return number >= 1 && number <= this.total;
    }

    public String getCategoryOf(int number) {
        for (RangeEntry entry : this.ranges) {
            if (number >= entry.min && number <= entry.max) {
                return entry.key;
            }
        }
        throw new IllegalArgumentException("Некорректный номер лайт анархии: " + number);
    }

    public String getKeyOf(int number) {
        return number == 1 ? "lanarchy" : "lanarchy" + number;
    }

    public String getNameOf(int number) {
        for (RangeEntry entry : this.ranges) {
            if (number >= entry.min && number <= entry.max) {
                return entry.name + " #" + number;
            }
        }
        return "Анархия #" + number;
    }

    public int getMinNumber() {
        return 1;
    }

    public int getMaxNumber() {
        return this.total;
    }

    public List<RangeEntry> getRanges() {
        return Collections.unmodifiableList(this.ranges);
    }

    public int getTotal() {
        return this.total;
    }

    public void setRanges(List<RangeEntry> newRanges, int newTotal) {
        this.total = Math.max(1, newTotal);
        List<RangeEntry> sorted = new ArrayList<>(newRanges);
        sorted.sort(Comparator.comparingInt(e -> e.min));
        this.ranges.clear();
        this.ranges.addAll(sorted);
        this.save();
    }

    private void load() {
        try {
            Path configPath = Paths.get(CONFIG_FILE_NAME);
            if (Files.exists(configPath)) {
                List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
                for (String line : lines) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        if (line.startsWith("total=")) {
                            try {
                                this.total = Integer.parseInt(line.substring(6).trim());
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    public void save() {
        try {
            Path configPath = Paths.get(CONFIG_FILE_NAME);
            List<String> lines = new ArrayList<>();
            lines.add("total=" + this.total);
            for (RangeEntry entry : this.ranges) {
                lines.add(entry.key + "=" + entry.min + "-" + entry.max);
            }
            Files.write(configPath, lines, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }

    public static class RangeEntry {
        public String key;
        public String name;
        public int min;
        public int max;

        public RangeEntry(String key, String name, int min, int max) {
            this.key = key;
            this.name = name;
            this.min = min;
            this.max = max;
        }
    }
}
