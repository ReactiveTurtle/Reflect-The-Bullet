package ru.reactiveturtle.reflectthebullet.desktop;

import java.io.File;
import java.nio.file.FileSystemException;

import ru.reactiveturtle.reflectthebullet.base.App;
import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.base.repository.SettingsRepositoryImpl;

public class AppDesktopProvider implements App {
    private final String appDataPath;

    public AppDesktopProvider() throws FileSystemException {
        appDataPath = System.getenv("APPDATA");
        init();
    }

    private void init() throws FileSystemException {
        tryInitAppDirectory();
        tryInitLevels();
    }

    @Override
    public void showMessage(String text) {

    }

    @Override
    public SettingsRepositoryImpl getSettingsRepository() {
        return null;
    }

    @Override
    public LevelRepositoryImpl getLevelRepository() {
        return null;
    }

    private void tryInitAppDirectory() throws FileSystemException {
        File appDirectory = new File(appDataPath, DesktopLauncher.APP_NAME);
        if (!initDirectory(appDirectory)) {
            throwFileSystemException(appDirectory);
        }
    }

    private void tryInitLevels() throws FileSystemException {
        File appDirectory = createFile(appDataPath, DesktopLauncher.APP_NAME);
        File levelsDirectory = tryInitLevelsDirectory();
    }

    private File tryInitLevelsDirectory() throws FileSystemException {
        File levelsDirectory = createFile(appDataPath, DesktopLauncher.APP_NAME, "levels");
        if (!initDirectory(levelsDirectory)) {
            throwFileSystemException(levelsDirectory);
        }
        return levelsDirectory;
    }

    private boolean initDirectory(File file) {
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    private File createFile(String path, String... childs) {
        return createFile(new File(path), childs);
    }

    private File createFile(File file, String... childs) {
        return new File(file, String.join("/", childs));
    }

    private void throwFileSystemException(File file) throws FileSystemException {
        throw new FileSystemException(
                file.getAbsolutePath(),
                file.getAbsolutePath(),
                "File can't be initialized");
    }
}
