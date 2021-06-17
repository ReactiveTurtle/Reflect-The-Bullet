package ru.reactiveturtle.reflectthebullet.level;

import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;

public class LevelRepository implements LevelRepositoryImpl {
    @Override
    public void setLastLevel(String levelFile) {

    }

    @Override
    public LevelData getLastLevel() {
        return null;
    }

    @Override
    public List<LevelData> getLevels(LevelType levelType) {
        return null;
    }

    @Override
    public void setLevelData(String levelTypeName, int levelIndex, int score, boolean isFinished) {

    }

    @Override
    public LevelData getLevelData(String levelFile) {
        return null;
    }

    @Override
    public LevelData getNextLevel(String levelFile) {
        return null;
    }
}
