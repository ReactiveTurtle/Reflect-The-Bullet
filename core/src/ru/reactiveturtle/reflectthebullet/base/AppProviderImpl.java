package ru.reactiveturtle.reflectthebullet.base;

import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.base.repository.SettingsRepositoryImpl;

public interface AppProviderImpl {
    void showMessage(String text);

    SettingsRepositoryImpl getSettingsRepository();

    LevelRepositoryImpl getLevelRepository();
}
