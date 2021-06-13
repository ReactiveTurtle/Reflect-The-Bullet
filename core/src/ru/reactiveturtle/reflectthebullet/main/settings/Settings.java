package ru.reactiveturtle.reflectthebullet.main.settings;

public class Settings {
    private boolean isMusicPlaying;
    private float musicVolume;
    private boolean isSoundFxPlaying;
    private float soundFxVolume;

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
        return musicVolume;
    }

    public boolean isSoundFxPlaying() {
        return isSoundFxPlaying;
    }

    public float getSoundFxVolume() {
        return soundFxVolume;
    }
}
