package ru.reactiveturtle.reflectthebullet.level;

public final class StarExtensions {
    public static Star getStars(int bestScore, LevelRequirements requirements) {
        if (bestScore < requirements.getFirstStarScore()) {
            return Star.ZERO;
        }
        if (bestScore < requirements.getSecondStarScore()) {
            return Star.ONE;
        }
        if (bestScore < requirements.getThirdStarScore()) {
            return Star.TWO;
        }
        return Star.THREE;
    }

    public enum Star {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3);

        private int anInt;

        Star(int anInt) {
            this.anInt = anInt;
        }

        public int getInt() {
            return anInt;
        }

        @Override
        public String toString() {
            return name();
        }
    }
}

