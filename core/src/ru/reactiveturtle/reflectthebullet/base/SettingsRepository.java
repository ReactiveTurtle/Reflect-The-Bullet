package ru.reactiveturtle.reflectthebullet.base;

import ru.reactiveturtle.reflectthebullet.main.settings.Settings;

public interface SettingsRepository {
    void setSettings(Settings settings);

    Settings getSettings();
}
