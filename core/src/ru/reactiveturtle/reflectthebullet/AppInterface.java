package ru.reactiveturtle.reflectthebullet;

import java.util.List;

import ru.reactiveturtle.reflectthebullet.general.screens.main.level.LevelInfo;

public interface AppInterface {
    void showMessage(String text);

    void setCurrentLevelParams(String levelType, int levelIndex);

    String[] getCurrentLevelParams();

    List<LevelInfo> getLevelsInfo(String levelTypeName);

    void setLevelInfo(String levelTypeName, int levelIndex, int score, boolean isFinished);

    LevelInfo getLevelInfo(String levelTypeName, int levelIndex);

    LevelInfo getNextLevel(String levelTypeName, int levelIndex);

    void setSettings(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume);

    String[] getSettings();
}
