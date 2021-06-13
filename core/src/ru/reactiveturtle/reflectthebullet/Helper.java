package ru.reactiveturtle.reflectthebullet;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Helper {
    public static boolean isVisibleInScreen(Sprite sprite, float width, float height) {
        float[] vertices = sprite.getVertices();
        for (int i = 0; i < 4; i++) {
            if (vertices[i * 5] < width && vertices[i * 5] > 0 &&
                    vertices[i * 5 + 1] < height && vertices[i * 5 + 1] > 0) {
                return true;
            }
        }
        return false;
    }

    public static void syncSpriteWithBody(Sprite sprite, Body body, float oneMeter) {
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        Vector2 center1 = new Vector2(body.getLocalCenter().x * oneMeter, body.getLocalCenter().y * oneMeter);
        Vector2 center2 = new Vector2(sprite.getOriginX(), sprite.getOriginY());
        sprite.setPosition(body.getWorldCenter().x * oneMeter + center1.x - center2.x,
                body.getWorldCenter().y * oneMeter + center1.y - center2.y);
    }

    public static Music loadLevelMusic(String locationName) {
        Music music = null;
        switch (locationName) {
            case "desert_open":
                music = Gdx.audio.newMusic(Gdx.files.internal("music/desert_theme.mp3"));
                music.setLooping(true);
                break;
            case "celt_open":
                music = Gdx.audio.newMusic(Gdx.files.internal("music/celt_theme.mp3"));
                music.setLooping(true);
                break;
        }
        return music;
    }
}
