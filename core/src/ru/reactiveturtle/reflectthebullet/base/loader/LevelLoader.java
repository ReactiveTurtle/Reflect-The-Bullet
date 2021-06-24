package ru.reactiveturtle.reflectthebullet.base.loader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.repository.LevelsInitializer;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.RectangleReflector;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.Reflector;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.ReflectorType;
import ru.reactiveturtle.reflectthebullet.level.world.Level;
import ru.reactiveturtle.reflectthebullet.toolkit.FileUtils;

public final class LevelLoader {
    public Level load(GameContext gameContext, World world, String relativeLevelDirectory) {
        JSONObject jsonObject = gameContext.getLevelRepository()
                .getLevelStructure(relativeLevelDirectory);
        Texture texture = gameContext.getTextureLoader()
                .getTexture(jsonObject.getString("textureName"));
        List<Reflector> reflectors = loadReflector(gameContext, world, jsonObject);
        return new Level(
                gameContext,
                relativeLevelDirectory,
                texture,
                reflectors);
    }

    private List<Reflector> loadReflector(GameContext gameContext, World world, JSONObject levelJson) {
        JSONArray reflectorsJson = levelJson.getJSONArray("reflectors");
        List<Reflector> reflectors = new ArrayList<>();
        for (int i = 0; i < reflectorsJson.length(); i++) {
            JSONObject reflectorJson = reflectorsJson.getJSONObject(i);
            Texture texture = gameContext.getTextureLoader().getTexture(reflectorJson.getString("texture"));
            int x = reflectorJson.getInt("x");
            int y = reflectorJson.getInt("y");
            String typeStr = reflectorJson.getString("type");
            ReflectorType reflectorType = ReflectorType.valueOf(typeStr.toUpperCase());
            Vector2 blockSize = getBlockSize(gameContext.getDisplayMetrics());
            switch (reflectorType) {
                case RECTANGLE:
                    RectangleReflector rectangleReflector = new RectangleReflector(gameContext, world, texture);
                    rectangleReflector.setSize(blockSize.x, blockSize.y);
                    rectangleReflector.setPosition(blockSize.x * x, blockSize.y *y);
                    reflectors.add(rectangleReflector);
                    break;
            }
        }
        return reflectors;
    }

    public static Vector2 getBlockSize(DisplayMetrics displayMetrics) {
        float blockWidth = displayMetrics.widthPixels() / 12f;
        float blockHeight = (float) displayMetrics.heightPixels() / (int) (displayMetrics.heightPixels() / blockWidth);
        return new Vector2(blockWidth, blockHeight);
    }
}
