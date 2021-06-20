package ru.reactiveturtle.reflectthebullet.desktop;

import org.json.JSONException;

import java.io.File;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelStoreData;
import ru.reactiveturtle.reflectthebullet.level.LevelType;
import ru.reactiveturtle.reflectthebullet.toolkit.FileUtils;

import static ru.reactiveturtle.reflectthebullet.toolkit.FileUtils.createDirectory;
import static ru.reactiveturtle.reflectthebullet.toolkit.FileUtils.throwFileInitializingError;

public class LevelRepository implements LevelRepositoryImpl {
    private String appDataPath;
    private static final String LAST_LEVEL_DATA_FILE_NAME = "lastLevelData.json";

    public LevelRepository(String appDataPath) {
        this.appDataPath = appDataPath;
        tryInitLevels();
    }

    @Override
    public void setLastLevel(LevelStoreData levelStoreData) {
        FileUtils.writeJsonFile(getLastLevelDataFile(), levelStoreData);
    }

    @Override
    public LevelStoreData getLastLevel() {
        File file = getLastLevelDataFile();
        if (!file.exists()) {
            return setDefaultLastLevelData();
        }
        try {
            return LevelStoreData.deserialize(FileUtils.readJsonFile(file));
        } catch (JSONException e) {
            return setDefaultLastLevelData();
        }
    }

    @Override
    public List<LevelData> getLevels(LevelType levelType) {
        return null;
    }

    @Override
    public void setLevelData(LevelStoreData levelStoreData, int score, boolean isFinished) {

    }

    @Override
    public LevelData getLevelData(String levelFile) {
        return null;
    }

    @Override
    public LevelData getNextLevel(String levelFile) {
        return null;
    }


    private void tryInitLevels() {
        File appDirectory = FileUtils.getFileObject(appDataPath, DesktopLauncher.APP_NAME);
        File levelsDirectory = tryInitLevelsDirectory();
    }

    private File tryInitLevelsDirectory() {
        File levelsDirectory = FileUtils.getFileObject(appDataPath, "levels");
        if (!createDirectory(levelsDirectory)) {
            throwFileInitializingError(levelsDirectory);
        }
        return levelsDirectory;
    }

    private LevelStoreData setDefaultLastLevelData() {
        LevelStoreData defaultLevelStoreData = new LevelStoreData(
                LevelType.DESERT,
                FileUtils.getFileObject(appDataPath, "levels", "desert", "1").getAbsolutePath());
        setLastLevel(defaultLevelStoreData);
        return defaultLevelStoreData;
    }

    private File getLastLevelDataFile() {
        return FileUtils.getFileObject(appDataPath, LAST_LEVEL_DATA_FILE_NAME);
    }
}
