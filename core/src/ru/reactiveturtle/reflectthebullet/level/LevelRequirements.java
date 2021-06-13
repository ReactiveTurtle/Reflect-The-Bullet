package ru.reactiveturtle.reflectthebullet.level;

public class LevelRequirements {
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
}
