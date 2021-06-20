package ru.reactiveturtle.reflectthebullet.desktop;

import java.io.File;

import ru.reactiveturtle.reflectthebullet.base.AppProviderImpl;
import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.base.repository.SettingsRepositoryImpl;

import static ru.reactiveturtle.reflectthebullet.toolkit.FileUtils.getFileObject;
import static ru.reactiveturtle.reflectthebullet.toolkit.FileUtils.createDirectory;
import static ru.reactiveturtle.reflectthebullet.toolkit.FileUtils.throwFileInitializingError;

public class AppDesktopProvider implements AppProviderImpl {
    private final String appDataPath;
    private final SettingsRepository settingsRepository;
    private final LevelRepository levelRepository;

    public AppDesktopProvider() {
        appDataPath = System.getenv("APPDATA");
        File appDirectory = tryInitAppDirectory();
        settingsRepository = new SettingsRepository(appDirectory.getAbsolutePath());
        levelRepository = new LevelRepository(appDirectory.getAbsolutePath());
    }

    @Override
    public void showMessage(String text) {

    }

    @Override
    public SettingsRepositoryImpl getSettingsRepository() {
        return settingsRepository;
    }

    @Override
    public LevelRepositoryImpl getLevelRepository() {
        return levelRepository;
    }


    private File tryInitAppDirectory() {
        File appDirectory = new File(appDataPath, DesktopLauncher.APP_NAME);
        if (!createDirectory(appDirectory)) {
            throwFileInitializingError(appDirectory);
        }
        return appDirectory;
    }
}
