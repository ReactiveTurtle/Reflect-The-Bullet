package ru.reactiveturtle.reflectthebullet.base;

import java.util.List;

import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelType;

public interface LevelRepository {
    void setLastLevel(String levelFile);

    LevelData getLastLevel();

    List<LevelData> getLevels(LevelType levelType);

    void setLevelData(String levelTypeName, int levelIndex, int score, boolean isFinished);

    LevelData getLevelData(String levelFile);

    LevelData getNextLevel(String levelFile);
}
