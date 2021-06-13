package ru.reactiveturtle.reflectthebullet.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface StaticObject {
    Physical getPhysical();

    Sprite getSprite();
}
