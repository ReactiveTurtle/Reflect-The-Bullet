package ru.reactiveturtle.reflectthebullet.desktop;

import org.json.JSONException;

import java.io.File;

import ru.reactiveturtle.reflectthebullet.base.repository.SettingsRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.main.settings.Settings;
import ru.reactiveturtle.reflectthebullet.toolkit.FileUtils;

public class SettingsRepository implements SettingsRepositoryImpl {
    private String appDataPath;
    private static final String SETTINGS_FILE_NAME = "settings.json";

    public SettingsRepository(String appDataPath) {
        this.appDataPath = appDataPath;
    }

    @Override
    public void setSettings(Settings settings) {
        FileUtils.writeJsonFile(getSettingsFile(), settings);
    }

    @Override
    public Settings getSettings() {
        File file = getSettingsFile();
        if (!file.exists()) {
            return setDefaultSettings();
        }
        try {
            return Settings.deserialize(FileUtils.readJsonFile(file));
        } catch (JSONException e) {
            return setDefaultSettings();
        }
    }

    private Settings setDefaultSettings() {
        Settings defaultSettings = new Settings(true, 0.8f, true, 0.8f);
        setSettings(defaultSettings);
        return defaultSettings;
    }

    private File getSettingsFile() {
        return FileUtils.getFileObject(appDataPath, SETTINGS_FILE_NAME);
    }
}
