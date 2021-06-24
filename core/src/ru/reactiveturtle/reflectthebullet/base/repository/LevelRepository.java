package ru.reactiveturtle.reflectthebullet.base.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.level.LastLevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelRequirements;
import ru.reactiveturtle.reflectthebullet.level.LevelType;
import ru.reactiveturtle.reflectthebullet.toolkit.FileException;
import ru.reactiveturtle.reflectthebullet.toolkit.FileUtils;
import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;
import ru.reactiveturtle.reflectthebullet.toolkit.Objects;

import static ru.reactiveturtle.reflectthebullet.toolkit.FileUtils.createDirectory;

public class LevelRepository implements LevelRepositoryImpl {
    private final String appDataPath;
    private static final String LAST_LEVEL_DATA_FILENAME = "lastLevelData.json";
    private static final String STRUCTURE_FILENAME = "structure.json";
    private static final String REQUIREMENTS_FILENAME = "requirements.json";
    private static final String LEVEL_DATA_FILENAME = "levelData.json";
    private static final String LEVELS_DIR_NAME = "levels";

    public LevelRepository(String appDataPath) {
        this.appDataPath = appDataPath;
    }

    @Override
    public File getAppDataDirectory() {
        return new File(appDataPath);
    }

    @Override
    public void setLastLevelData(LastLevelData lastLevelData) {
        tryInitAppDataDirectory();
        FileUtils.writeJsonFile(getLastLevelDataFile(), lastLevelData);
    }

    @Override
    public LastLevelData getLastLevelData() {
        File file = getLastLevelDataFile();
        if (!file.exists()) {
            return setDefaultLastLevelData();
        }
        try {
            return LastLevelData.deserialize(FileUtils.readJsonFile(file));
        } catch (JSONException e) {
            return setDefaultLastLevelData();
        }
    }

    @Override
    public List<String> getLevelDirectoriesList(LevelType levelType) {
        File levelsDirectory = tryInitLevelsDirectory();
        switch (levelType) {
            case DESERT:
            case CELT:
                LevelsInitializer.tryInitLevelDataFiles(levelType, levelsDirectory);
                LevelsInitializer.tryInitLevelRequirementsFiles(levelType, levelsDirectory);
                return getLevelDirectoriesList(levelType, levelsDirectory);
            default:
                throw new EnumConstantNotPresentException(LevelType.class, "levelType");
        }
    }

    @Override
    public List<LevelData> getLevelDataList(LevelType levelType) {
        File levelsDirectory = tryInitLevelsDirectory();
        switch (levelType) {
            case DESERT:
            case CELT:
                LevelsInitializer.tryInitLevelDataFiles(levelType, levelsDirectory);
                return getLevelJSONObjectList(
                        levelType, levelsDirectory, LEVEL_DATA_FILENAME, LevelData.class);
            default:
                throw new EnumConstantNotPresentException(LevelType.class, "levelType");
        }
    }

    @Override
    public List<LevelRequirements> getLevelRequirementsList(LevelType levelType) {
        tryInitAppDataDirectory();
        File levelsDirectory = tryInitLevelsDirectory();
        switch (levelType) {
            case DESERT:
            case CELT:
                LevelsInitializer.tryInitLevelRequirementsFiles(levelType, levelsDirectory);
                return getLevelJSONObjectList(
                        levelType, levelsDirectory, REQUIREMENTS_FILENAME, LevelRequirements.class);
            default:
                throw new EnumConstantNotPresentException(LevelType.class, "levelType");
        }
    }

    @Override
    public void setLevelData(String relativeLevelDirectory, int score, boolean isFinished) {
        LevelData oldLevelData = getLevelData(relativeLevelDirectory);
        LevelData newLevelData = new LevelData(
                oldLevelData.getLevelType(),
                Math.max(oldLevelData.getBestScore(), score),
                isFinished);
        File levelDataFile = FileUtils.getFileObject(relativeLevelDirectory, LEVEL_DATA_FILENAME);
        FileUtils.writeJsonFile(levelDataFile, newLevelData);
    }

    @Override
    public LevelData getLevelData(String relativeLevelDirectory) {
        File levelDataFile = FileUtils.getFileObject(
                getLevelsDirectory(),
                relativeLevelDirectory,
                LEVEL_DATA_FILENAME);
        LevelType levelType = LevelType.valueOf(
                levelDataFile.getParentFile().getParentFile().getName().toUpperCase());
        FileUtils.writeJsonFile(levelDataFile, new LevelData(levelType, 0, false));
        return FileUtils.readJson(levelDataFile, LevelData.class);
    }

    @Override
    public LevelRequirements getLevelRequirements(String relativeLevelDirectory) {
        File requirementsFile = FileUtils.getFileObject(getLevelsDirectory(), relativeLevelDirectory, REQUIREMENTS_FILENAME);
        FileHandle fileHandle = Gdx.files.internal(LEVELS_DIR_NAME + "/" + relativeLevelDirectory);
        if (!fileHandle.exists()) {
            throw new FileException(fileHandle.file(), "Level requirements not found for this level");
        }
        FileUtils.copy(fileHandle, requirementsFile);
        return FileUtils.readJson(requirementsFile, LevelRequirements.class);
    }

    @Override
    public String getNextLevelDirectory(String levelDirectory) {
        return null;
    }

    @Override
    public JSONObject getLevelStructure(String relativeLevelDirectory) {
        String internalLevelFile = LEVELS_DIR_NAME + "/"+ relativeLevelDirectory + "/" + STRUCTURE_FILENAME;
        FileHandle fileHandle = Gdx.files.internal(internalLevelFile);
        File levelStructureFile = FileUtils.getFileObject(
                getLevelsDirectory(),
                relativeLevelDirectory,
                STRUCTURE_FILENAME);
        if (!levelStructureFile.exists()) {
            if (!fileHandle.exists()) {
                throw new FileException(fileHandle.file(), "File structure source not found");
            }
            FileUtils.copy(fileHandle, levelStructureFile);
        }
        return new JSONObject(FileUtils.readJsonFile(levelStructureFile));
    }

    private File tryInitLevelsDirectory() {
        File levelsDirectory = FileUtils.getFileObject(appDataPath, LEVELS_DIR_NAME);
        createDirectory(levelsDirectory);
        return levelsDirectory;
    }

    private LastLevelData setDefaultLastLevelData() {
        LastLevelData defaultLevelStoreData = new LastLevelData(
                LevelType.DESERT.toString().toLowerCase() + "/1");
        setLastLevelData(defaultLevelStoreData);
        return defaultLevelStoreData;
    }

    private List<String> getLevelDirectoriesList(
            LevelType levelType,
            File levelsDirectory) {
        String levelTypeInLowerCase = levelType.toString().toLowerCase();
        String[] levelDirectories = FileUtils.getFileObject(
                levelsDirectory, levelTypeInLowerCase).list();
        Objects.requireNonNull(levelDirectories);
        for (int i = 0; i < levelDirectories.length; i++) {
            levelDirectories[i] = levelTypeInLowerCase + "/" + levelDirectories[i];
        }
        return new ArrayList<>(Arrays.asList(levelDirectories));
    }

    private <T extends JSONSerializable> List<T> getLevelJSONObjectList(
            LevelType levelType,
            File levelsDirectory,
            String jsonFilename,
            Class<T> jsonSerializableClass) {
        List<T> levelDataList = new ArrayList<>();
        File desertLevelsDirectory = FileUtils.getFileObject(
                levelsDirectory,
                levelType.toString().toLowerCase());
        File[] levelDirectories = desertLevelsDirectory.listFiles();
        Objects.requireNonNull(levelDirectories);
        for (File levelDirectory : levelDirectories) {
            File levelDataFile = FileUtils.getFileObject(levelDirectory, jsonFilename);
            T object = FileUtils.readJson(levelDataFile, jsonSerializableClass);
            levelDataList.add(object);
        }
        return levelDataList;
    }

    private void tryInitAppDataDirectory() {
        FileUtils.createFile(getAppDataDirectory());
    }

    private File getLastLevelDataFile() {
        return FileUtils.getFileObject(getAppDataDirectory(), LAST_LEVEL_DATA_FILENAME);
    }

    private File getLevelsDirectory() {
        return FileUtils.getFileObject(getAppDataDirectory(), LEVELS_DIR_NAME);
    }
}
