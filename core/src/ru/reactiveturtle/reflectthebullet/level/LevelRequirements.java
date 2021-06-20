package ru.reactiveturtle.reflectthebullet.level;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class LevelRequirements implements JSONSerializable {
    private int firstStarScore;
    private int secondStarScore;
    private int thirdStarScore;

    public LevelRequirements(int firstStarScore, int secondStarScore, int thirdStarScore) {
        this.firstStarScore = firstStarScore;
        this.secondStarScore = secondStarScore;
        this.thirdStarScore = thirdStarScore;
    }

    public int getFirstStarScore() {
        return firstStarScore;
    }

    public int getSecondStarScore() {
        return secondStarScore;
    }

    public int getThirdStarScore() {
        return thirdStarScore;
    }

    @Override
    public String serialize() {
        return null;
    }
}
