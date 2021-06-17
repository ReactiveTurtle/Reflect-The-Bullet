package ru.reactiveturtle.reflectthebullet.level.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.Revolver;
import ru.reactiveturtle.reflectthebullet.game.objects.Bullet;

public interface GameWorld {
    void loadLevel(String levelName, int levelIndex);

    String getLoadedLocation();

    void drawBackground(SpriteBatch spriteBatch);

    void drawObjects(SpriteBatch spriteBatch);

    void syncPhysics();

    World getWorld();

    Sprite getAim();

    Revolver getRevolver();

    Bullet getBullet();

    int getBestScore();

    void dispose();
}
