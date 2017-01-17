package com.gans.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SystemSettings {

    public enum Setting {
        DELIM("delimiter");

        private static final Map<String, Setting> ALL = Stream.of(Setting.values()).collect(Collectors.toMap(s -> s.property, s -> s));

        private String property;

        Setting(String property) {
            this.property = property;
        }
    }

    private final Map<Setting, String> settings;

    public SystemSettings() {
        settings = new HashMap<>();

        File file = new File("merge-csv.conf");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                } else if (line.startsWith("#")) {
                    continue;
                }

                int delim = line.indexOf(":");
                if (delim < 0) {
                    continue;
                }

                String property = line.substring(0, delim);
                Setting setting = Setting.ALL.get(property.trim());
                if (setting != null) {
                    String value = line.substring(delim + 1).trim();
                    settings.put(setting, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
