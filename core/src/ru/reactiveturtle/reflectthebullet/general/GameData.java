package ru.reactiveturtle.reflectthebullet.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameData {
    public static OrthographicCamera CAMERA;
    public static boolean IS_MUSIC_PLAYING = true;
    public static float MUSIC_VOLUME = 0.8f;
    public static boolean IS_SOUND_FX_PLAYING = true;
    public static float SOUND_FX_VOLUME = 0.8f;
    public static String GAME_FONT = "roboto_medium.fnt";

    public static String CURRENT_LOCATION;
    public static int CURRENT_LEVEL;
    public static float ONE_METER = width() / 2f;


    public static int width() {
        return Gdx.graphics.getWidth();
    }

    public static int height() {
        return (int) (Gdx.graphics.getWidth() * 16f / 9f);
    }

    public static final String DEFAULT_CURRENT_LEVEL_PARAMS = "desert_open&1";
    public static final String DEFAULT_LEVELS_INFO =
            "desert_open=0:0-20#20#20&" +
                    "0:0-20#30#40&" +
                    "0:0-20#40#50&" +
                    "0:0-30#50#60&" +
                    "0:0-40#50#70&" +
                    "0:0-40#60#80&" +
                    "0:0-50#60#70&" +
                    "0:0-50#70#80&" +
                    "0:0-50#70#90&" +
                    "0:0-60#80#100;" +
            "celt_open=0:0-20#20#20";
    public static final String DEFAULT_SETTINGS = "1:0.8:1:0.7";
}
