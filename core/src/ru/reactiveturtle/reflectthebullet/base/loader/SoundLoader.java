package ru.reactiveturtle.reflectthebullet.base.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public final class SoundLoader {
    public SoundLoader() {
    }

    private Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("revolver_shot.ogg"));
    private Sound bulletToTargetSound = Gdx.audio.newSound(Gdx.files.internal("bullet_to_target.ogg"));
    private Sound rikoshetSound1 = Gdx.audio.newSound(Gdx.files.internal("rikoshet1.ogg"));
    private Sound rikoshetSound2 = Gdx.audio.newSound(Gdx.files.internal("rikoshet2.ogg"));
    private Sound rikoshetSound3 = Gdx.audio.newSound(Gdx.files.internal("rikoshet3.ogg"));
    private Sound rikoshetSound4 = Gdx.audio.newSound(Gdx.files.internal("rikoshet4.ogg"));
    private Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));

    public Sound getShotSound() {
        return shotSound;
    }
}
