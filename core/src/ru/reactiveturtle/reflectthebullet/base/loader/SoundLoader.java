package ru.reactiveturtle.reflectthebullet.base.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.reactiveturtle.reflectthebullet.toolkit.Value;

public final class SoundLoader {
    private Value<Sound> mShotSound = new Value<>();
    public Sound getShotSound() {
        if (!mShotSound.hasValue()) {
            mShotSound.setValue(Gdx.audio.newSound(Gdx.files.internal("revolver_shot.ogg")));
        }
        return mShotSound.getValue();
    }

    private Value<Sound> mBulletToTargetSound = new Value<>();
    public Sound getBulletToTargetSound() {
        if (!mBulletToTargetSound.hasValue()) {
            mBulletToTargetSound.setValue(Gdx.audio.newSound(Gdx.files.internal("bullet_to_target.ogg")));
        }
        return mBulletToTargetSound.getValue();
    }

    private Value<Sound> mRikoshetSound1 = new Value<>();
    public Sound getRikoshetSound1() {
        if (!mRikoshetSound1.hasValue()) {
            mRikoshetSound1.setValue(Gdx.audio.newSound(Gdx.files.internal("rikoshet1.ogg")));
        }
        return mRikoshetSound1.getValue();
    }

    private Value<Sound> mRikoshetSound2 = new Value<>();
    public Sound getRikoshetSound2() {
        if (!mRikoshetSound2.hasValue()) {
            mRikoshetSound2.setValue(Gdx.audio.newSound(Gdx.files.internal("rikoshet2.ogg")));
        }
        return mRikoshetSound2.getValue();
    }

    private Value<Sound> mRikoshetSound3 = new Value<>();
    public Sound getRikoshetSound3() {
        if (!mRikoshetSound3.hasValue()) {
            mRikoshetSound3.setValue(Gdx.audio.newSound(Gdx.files.internal("rikoshet3.ogg")));
        }
        return mRikoshetSound3.getValue();
    }

    private Value<Sound> mRikoshetSound4 = new Value<>();
    public Sound getRikoshetSound4() {
        if (!mRikoshetSound4.hasValue()) {
            mRikoshetSound4.setValue(Gdx.audio.newSound(Gdx.files.internal("rikoshet4.ogg")));
        }
        return mRikoshetSound4.getValue();
    }

    private Value<Sound> mHitSound = new Value<>();
    public Sound getHitSound() {
        if (!mHitSound.hasValue()) {
            mHitSound.setValue(Gdx.audio.newSound(Gdx.files.internal("hit.ogg")));
        }
        return mHitSound.getValue();
    }
}
