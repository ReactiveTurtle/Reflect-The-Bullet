package ru.reactiveturtle.reflectthebullet.base;

import com.badlogic.gdx.Gdx;

public class DisplayMetrics {
    private final float oneMeter;

    public DisplayMetrics() {
        oneMeter = widthPixels() / 2f;
    }

    public int widthPixels() {
        return Gdx.graphics.getWidth();
    }

    public int heightPixels() {
        return Gdx.graphics.getHeight();
    }

    public float getOneMeterPixels() {
        return oneMeter;
    }
}
