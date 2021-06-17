package ru.reactiveturtle.reflectthebullet.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface StaticObject {
    Physical getPhysical();

    Sprite getSprite();
}
