package ru.reactiveturtle.reflectthebullet.base.repository;

import java.util.List;

import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelStoreData;
import ru.reactiveturtle.reflectthebullet.level.LevelType;

public interface LevelRepositoryImpl {
    void setLastLevel(LevelStoreData levelStoreData);

    LevelStoreData getLastLevel();

    List<LevelData> getLevels(LevelType levelType);

    void setLevelData(LevelStoreData levelStoreData, int score, boolean isFinished);

    LevelData getLevelData(String levelFile);

    LevelData getNextLevel(String levelFile);
}
