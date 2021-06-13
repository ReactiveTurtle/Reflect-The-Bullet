package ru.reactiveturtle.reflectthebullet.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public interface Physical {

    void createBody(World world);

    void syncSprite(float oneMeter);

    Body getBody();

    void disposeObject();
}
