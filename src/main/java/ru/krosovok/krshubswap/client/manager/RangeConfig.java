package ru.krosovok.krshubswap.client.manager;

import java.io.*;
import java.util.*;

public class RangeConfig {
    private static RangeConfig instance;
    private Map<String, String> ranges;
    
    private RangeConfig() {
        this.ranges = new HashMap<>();
        loadDefaultRanges();
    }
    
    public static RangeConfig getInstance() {
        if (instance == null) {
            instance = new RangeConfig();
        }
        return instance;
    }
    
    private void loadDefaultRanges() {
        ranges.put("solo", "1-15");
        ranges.put("duo", "16-31");
        ranges.put("trio", "32-47");
        ranges.put("clan", "48-66");
    }
    
    public boolean isValid(int number) {
        return number >= 1 && number <= 66;
    }
    
    public int getMaxNumber() {
        return 66;
    }
    
    public String getCategoryOf(int number) {
        if (number >= 1 && number <= 15) return "solo";
        if (number >= 16 && number <= 31) return "duo";
        if (number >= 32 && number <= 47) return "trio";
        if (number >= 48 && number <= 66) return "clan";
        return "unknown";
    }
    
    public String getKeyOf(int number) {
        return String.valueOf(number);
    }
    
    public String getNameOf(int number) {
        String category = getCategoryOf(number);
        String categoryName = "";
        switch (category) {
            case "solo": categoryName = "Соло"; break;
            case "duo": categoryName = "Дуо"; break;
            case "trio": categoryName = "Трио"; break;
            case "clan": categoryName = "Клан"; break;
        }
        return categoryName + " анархию #" + number;
    }
}