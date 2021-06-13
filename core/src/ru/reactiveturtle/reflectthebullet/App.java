package ru.reactiveturtle.reflectthebullet;

import ru.reactiveturtle.reflectthebullet.base.LevelRepository;
import ru.reactiveturtle.reflectthebullet.base.SettingsRepository;
import ru.reactiveturtle.reflectthebullet.main.settings.Settings;

public interface App {
    void showMessage(String text);

    SettingsRepository getSettingsRepository();

    LevelRepository getLevelRepository();
}
