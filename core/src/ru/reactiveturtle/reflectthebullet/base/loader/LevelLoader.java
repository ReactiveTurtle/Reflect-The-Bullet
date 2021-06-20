package ru.reactiveturtle.reflectthebullet.base.loader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.RectangleReflector;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.Reflector;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.ReflectorType;
import ru.reactiveturtle.reflectthebullet.level.world.Level;
import ru.reactiveturtle.reflectthebullet.toolkit.FileUtils;

public final class LevelLoader {
    public Level load(GameContext gameContext, World world, File levelFile) {
        String serializedLevel = FileUtils.readJsonFile(levelFile);
        JSONObject jsonObject = new JSONObject(serializedLevel);
        List<Reflector> reflectors = loadReflector(gameContext, world, jsonObject);
        return new Level(gameContext, reflectors);
    }

    private List<Reflector> loadReflector(GameContext gameContext, World world, JSONObject levelJson) {
        JSONArray reflectorsJson = levelJson.getJSONArray("reflectors");
        List<Reflector> reflectors = new ArrayList<>();
        for (int i = 0; i < reflectorsJson.length(); i++) {
            JSONObject reflector = reflectorsJson.getJSONObject(i);
            String typeStr = reflector.getString("type");
            ReflectorType reflectorType = ReflectorType.valueOf(typeStr.toUpperCase());
            switch (reflectorType) {
                case RECTANGLE:
                    Texture texture = gameContext.getTextureLoader().getTexture(reflector.getString("texture"));
                    reflectors.add(new RectangleReflector(gameContext, world, texture));
                    break;
            }
        }
        return reflectors;
    }
}
