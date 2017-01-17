package com.gans.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class UserSettings {

    final File file;
    final Properties props;

    public UserSettings() {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }

            file = new File("data/u-pref.dat");
            if (!file.exists()) {
                file.createNewFile();
            }

            props = new Properties();
            try (InputStream is = new FileInputStream(file)) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSetting(String key) {
        String hash = hash(key);
        String value = props.getProperty(hash);
        return value != null ? value : "";
    }

    public void setSetting(String key, String value) {
        if (value == null) {
            return;
        }

        String currentValue = getSetting(key);
        if (!currentValue.equals(value)) {
            props.setProperty(hash(key), value);
            persist();
        }
    }

    private void persist() {
        try (OutputStream out = new FileOutputStream(file)) {
            props.store(out, new Date().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String hash(String st) {
        if (st == null) {
            st = "";
        }
        return String.valueOf(st.hashCode());
    }
}
