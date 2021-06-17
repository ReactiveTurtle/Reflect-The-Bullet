package ru.reactiveturtle.reflectthebullet.level.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.RectangleReflector;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.TriangleReflector;
import ru.reactiveturtle.reflectthebullet.game.objects.targets.TrainTarget;

public class LevelHelper {
    public static RectangleReflector getRectangleReflector(int x, int y, String textureName) {
        Vector2 blockSize = getBlockSize();
        Texture reflectorTexture = new Texture(Gdx.files.internal(textureName));
        RectangleReflector rectangleReflector = new RectangleReflector(reflectorTexture, oneMeter);
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
