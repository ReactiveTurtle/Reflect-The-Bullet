package ru.reactiveturtle.reflectthebullet.level;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class LevelData implements JSONSerializable {
    private LevelStoreData levelStoreData;
    private LevelRequirements requirements;

    private boolean isFinished;
    private int bestScore;

    public LevelData(LevelStoreData levelStoreData, LevelRequirements requirements,
                     int bestScore,
                     boolean isFinished) {
        this.levelStoreData = levelStoreData;
        this.requirements = requirements;
        this.isFinished = isFinished;
        this.bestScore = bestScore;
    }

    public LevelStoreData getLevelStoreData() {
        return levelStoreData;
    }

    public LevelRequirements getRequirements() {
        return requirements;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getBestScore() {
        return bestScore;
    }

    @Override
    public String serialize() {
        return null;
    }
}
