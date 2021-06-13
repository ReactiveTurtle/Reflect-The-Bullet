package ru.reactiveturtle.reflectthebullet.level;

public class LevelTypeMapper {
    public static LevelType map(LevelsTypeMenu.Action action) {
        switch (action) {
            case DESERT:
                return LevelType.DESERT;
            case CELT:
                return LevelType.CELT;
            default:
                throw new EnumConstantNotPresentException(LevelsTypeMenu.Action.class, action.name());
        }
    }
}
