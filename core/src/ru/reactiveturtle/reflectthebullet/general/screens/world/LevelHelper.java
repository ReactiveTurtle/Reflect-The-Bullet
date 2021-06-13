package ru.reactiveturtle.reflectthebullet.general.screens.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import javax.annotation.Resource;

import ru.reactiveturtle.reflectthebullet.general.screens.world.visualmanagers.CloudsManager;
import ru.reactiveturtle.reflectthebullet.objects.reflectors.RectangleReflector;
import ru.reactiveturtle.reflectthebullet.objects.reflectors.TriangleReflector;
import ru.reactiveturtle.reflectthebullet.objects.targets.TrainTarget;

import static ru.reactiveturtle.reflectthebullet.general.GameData.height;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class LevelHelper {
    public static CloudsManager getClouds() {
        return new CloudsManager(width(), width() / 8f,
                3, 8,
                0, width(),
                Gdx.graphics.getHeight() * 2 / 3f, Gdx.graphics.getHeight() - width() / 5,
                0.75f, 1.25f, width() / 128f, width() / 16f,
                "clouds/cloud1.png", "clouds/cloud2.png", "clouds/cloud3.png",
                "clouds/cloud4.png", "clouds/cloud5.png", "clouds/cloud6.png", "clouds/cloud7.png");
    }

    public static RectangleReflector getRectangleReflector(int x, int y, String textureName) {
        Vector2 blockSize = getBlockSize();
        Texture reflectorTexture = new Texture(Gdx.files.internal(textureName));
        RectangleReflector rectangleReflector = new RectangleReflector(reflectorTexture);
        rectangleReflector.setSize(blockSize.x, blockSize.y);
        rectangleReflector.setPosition(blockSize.x * x, blockSize.y * y);
        return rectangleReflector;
    }

    public static TriangleReflector getTriangleReflector(int x, int y, float rotation, Texture texture) {
        Vector2 blockSize = getBlockSize();
        TriangleReflector triangleReflector = new TriangleReflector(blockSize.x, blockSize.y, texture);
        triangleReflector.setPosition(blockSize.x * x, blockSize.y * y);
        triangleReflector.setRotation(rotation);
        return triangleReflector;
    }

    public static TrainTarget getTarget(float width, float height, String textureName) {
        Vector2 blockSize = getBlockSize();
        Texture texture = new Texture(Gdx.files.internal(textureName));
        TrainTarget target = new TrainTarget(width, height, texture);
        target.setPosition(0, height() - blockSize.y * 4f - target.getHeight());
        return target;
    }

    public static Vector2 getBlockSize() {
        float blockWidth = width() / 12f;
        float blockHeight = (float) height() / (int) (height() / blockWidth);
        return new Vector2(blockWidth, blockHeight);
    }
}
