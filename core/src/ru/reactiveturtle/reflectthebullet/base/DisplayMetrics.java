package ru.reactiveturtle.reflectthebullet.base;

import com.badlogic.gdx.Gdx;

public class DisplayMetrics {
    public int widthPixels() {
        return Gdx.graphics.getWidth();
    }

    public int heightPixels() {
        return Gdx.graphics.getHeight();
    }

    public float getOneMeterPixels() {
        return widthPixels() / 2f;
    }
}
