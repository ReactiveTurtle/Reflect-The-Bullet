package ru.reactiveturtle.reflectthebullet.base.repository;

import ru.reactiveturtle.reflectthebullet.main.settings.Settings;

public interface SettingsRepositoryImpl {
    void setSettings(Settings settings);

    Settings getSettings();
}
