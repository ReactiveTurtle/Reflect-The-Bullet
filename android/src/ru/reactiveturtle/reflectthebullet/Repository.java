package ru.reactiveturtle.reflectthebullet;

import android.content.Context;
import android.content.SharedPreferences;

import ru.reactiveturtle.reflectthebullet.general.GameData;

public class Repository {
    private static final String NAME = "preferences";

    private static final String CURRENT_LEVEL_PARAMS = "current_level_params";
    private static final String LEVELS_INFO = "levels_info";
    private static final String SETTINGS = "settings";

    private SharedPreferences preferences;

    public Repository(Context context) {
        preferences = context.getSharedPreferences(NAME, 0);
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    public void setCurrentLevelParams(String levelType, int levelIndex) {
        getEditor().putString(CURRENT_LEVEL_PARAMS, levelType + "&" +  levelIndex).commit();
    }

    public String getCurrentLevelParams() {
        return preferences.getString(CURRENT_LEVEL_PARAMS, GameData.DEFAULT_CURRENT_LEVEL_PARAMS);
    }

    public void setLevelsInfo(String levelsString) {
        getEditor().putString(LEVELS_INFO, levelsString).commit();
    }

    public String getLevelsInfo() {
        return preferences.getString(LEVELS_INFO, GameData.DEFAULT_LEVELS_INFO);
    }

    public void setSettings(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
        getEditor().putString(SETTINGS, (isMusicPlaying ? 1 : 0) + ":" +
                musicVolume + ":" +
                (isSoundFxPlaying ? 1 : 0) + ":" +
                soundFxVolume).commit();
    }

    public String getSettings() {
        return preferences.getString(SETTINGS, GameData.DEFAULT_SETTINGS);
    }
}