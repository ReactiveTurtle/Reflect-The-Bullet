package ru.reactiveturtle.reflectthebullet.level.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.RectangleReflector;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.TriangleReflector;
import ru.reactiveturtle.reflectthebullet.game.objects.targets.TrainTarget;

public class LevelHelper {
    public static RectangleReflector getRectangleReflector(GameContext gameContext, World world, int x, int y, String textureName) {
        Vector2 blockSize = getBlockSize(gameContext.getDisplayMetrics());
        Texture reflectorTexture = new Texture(Gdx.files.internal(textureName));
        RectangleReflector rectangleReflector = new RectangleReflector(gameContext, world, reflectorTexture);
        rectangleReflector.setSize(blockSize.x, blockSize.y);
        rectangleReflector.setPosition(blockSize.x * x, blockSize.y * y);
        return rectangleReflector;
    }

    public static TriangleReflector getTriangleReflector(GameContext gameContext, World world, int x, int y, float rotation, Texture texture) {
        Vector2 blockSize = getBlockSize(gameContext.getDisplayMetrics());
        TriangleReflector triangleReflector = new TriangleReflector(gameContext, world, blockSize.x, blockSize.y, texture);
        triangleReflector.setPosition(blockSize.x * x, blockSize.y * y);
        triangleReflector.setRotation(rotation);
        return triangleReflector;
    }

    public static TrainTarget getTarget(GameContext gameContext, World world, float width, float height) {
        Vector2 blockSize = getBlockSize(gameContext.getDisplayMetrics());
        TrainTarget target = new TrainTarget(gameContext, world, width, height);
        target.setPosition(0, gameContext.getDisplayMetrics().heightPixels() - blockSize.y * 4f - target.getHeight());
        return target;
    }

    public static Vector2 getBlockSize(DisplayMetrics displayMetrics) {
        float blockWidth = displayMetrics.widthPixels() / 12f;
        float blockHeight = (float) displayMetrics.heightPixels() / (int) (displayMetrics.heightPixels() / blockWidth);
        return new Vector2(blockWidth, blockHeight);
    }
}
