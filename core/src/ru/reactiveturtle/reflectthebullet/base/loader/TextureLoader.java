package ru.reactiveturtle.reflectthebullet.base.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

import ru.reactiveturtle.reflectthebullet.toolkit.Value;

public final class TextureLoader implements Disposable {
    private Value<Texture> mTrainTargetTexture = new Value<>();

    public Texture getTrainTargetTexture() {
        if (!mTrainTargetTexture.hasValue()) {
            mTrainTargetTexture.setValue(new Texture(Gdx.files.internal("target.png")));
        }
        return mTrainTargetTexture.getValue();
    }

    private Value<Texture> mBulletTexture = new Value<>();

    public Texture getBulletTexture() {
        if (!mBulletTexture.hasValue()) {
            mBulletTexture.setValue(new Texture(Gdx.files.internal("bullet.png")));
        }
        return mBulletTexture.getValue();
    }

    private Value<Texture> mRevolverTexture = new Value<>();

    public Texture getRevolverTexture() {
        if (!mRevolverTexture.hasValue()) {
            mRevolverTexture.setValue(new Texture(Gdx.files.internal("revolver.png")));
        }
        return mRevolverTexture.getValue();
    }

    private Map<String, Texture> reflectorTextures = new HashMap<>();

    public Texture getTexture(String textureName) {
        if (!reflectorTextures.containsKey(textureName)) {
            reflectorTextures.put(textureName, new Texture(Gdx.files.internal(textureName)));
        }
        return reflectorTextures.get(textureName);
    }

    @Override
    public void dispose() {
        disposeTexture(mTrainTargetTexture);
        disposeTexture(mBulletTexture);
        disposeTexture(mRevolverTexture);

        for (Texture texture : reflectorTextures.values()) {
            texture.dispose();
        }
        reflectorTextures.clear();
    }

    private void disposeTexture(Value<Texture> textureValue) {
        if (textureValue.hasValue()) {
            textureValue.getValue().dispose();
            textureValue.setValue(null);
        }
    }
}
