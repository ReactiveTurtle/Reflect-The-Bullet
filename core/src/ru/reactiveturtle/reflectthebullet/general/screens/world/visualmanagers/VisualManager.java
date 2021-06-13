package ru.reactiveturtle.reflectthebullet.general.screens.world.visualmanagers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface VisualManager {
    void draw(SpriteBatch spriteBatch, float deltaTime);

    void dispose();
}
