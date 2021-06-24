package ru.reactiveturtle.reflectthebullet.base.repository;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.level.LastLevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelRequirements;
import ru.reactiveturtle.reflectthebullet.level.LevelType;

public interface LevelRepositoryImpl {
    File getAppDataDirectory();

    void setLastLevelData(LastLevelData lastLevelData);

    LastLevelData getLastLevelData();

    List<String> getLevelDirectoriesList(LevelType levelType);

    List<LevelData> getLevelDataList(LevelType levelType);

    List<LevelRequirements> getLevelRequirementsList(LevelType levelType);

    void setLevelData(String relativeLevelDirectory, int score, boolean isFinished);

    LevelData getLevelData(String relativeLevelDirectory);

    LevelRequirements getLevelRequirements(String relativeLevelDirectory);

    String getNextLevelDirectory(String relativeLevelDirectory);

    JSONObject getLevelStructure(String relativeLevelDirectory);
}
