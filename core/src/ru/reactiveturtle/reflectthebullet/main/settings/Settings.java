package ru.reactiveturtle.reflectthebullet.main.settings;

import org.json.JSONObject;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class Settings implements JSONSerializable {
    private final boolean isMusicPlaying;
    private final float musicVolume;
    private final boolean isSoundFxPlaying;
    private final float soundFxVolume;

    public Settings(boolean isMusicPlaying,
                    float musicVolume,
                    boolean isSoundFxPlaying,
                    float soundFxVolume) {
        this.isMusicPlaying = isMusicPlaying;
        this.musicVolume = musicVolume;
        this.isSoundFxPlaying = isSoundFxPlaying;
        this.soundFxVolume = soundFxVolume;
    }

    public boolean isMusicPlaying() {
        return isMusicPlaying;
    }

    public float getMusicVolume() {
        return Math.round(musicVolume * 100) / 100f;
    }

    public boolean isSoundFxPlaying() {
        return isSoundFxPlaying;
    }

    public float getSoundFxVolume() {
        return Math.round(soundFxVolume * 100) / 100f;
    }

    @Override
    public String serialize() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("isMusicPlaying", isMusicPlaying());
        jsonObject.put("musicVolume", getMusicVolume());
        jsonObject.put("isSoundFxPlaying", isSoundFxPlaying());
        jsonObject.put("soundFxVolume", getSoundFxVolume());
        return jsonObject.toString();
    }

    public static Settings deserialize(String serialized) {
        JSONObject jsonObject = new JSONObject(serialized);
        return new Settings(
                jsonObject.getBoolean("isMusicPlaying"),
                jsonObject.getFloat("musicVolume"),
                jsonObject.getBoolean("isSoundFxPlaying"),
                jsonObject.getFloat("soundFxVolume")
        );
    }
}
