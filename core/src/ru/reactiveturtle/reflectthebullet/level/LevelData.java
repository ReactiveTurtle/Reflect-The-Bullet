package ru.reactiveturtle.reflectthebullet.level;

public class LevelData {
    private String levelFile;

    private LevelRequirements requirements;

    private boolean isFinished;
    private int bestScore;

    public LevelData(String levelFile,
                     LevelRequirements requirements,
                     int bestScore,
                     boolean isFinished) {
        this.levelFile = levelFile;
        this.requirements = requirements;
        this.isFinished = isFinished;
        this.bestScore = bestScore;
    }

    public String getLevelFile() {
        return levelFile;
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
}
