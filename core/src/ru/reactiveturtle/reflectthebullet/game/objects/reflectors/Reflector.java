package ru.reactiveturtle.reflectthebullet.game.objects.reflectors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.Entity;

public abstract class Reflector extends Entity {
    public Reflector(GameContext gameContext, World world, Texture texture) {
        super(gameContext, world, texture);
    }
}
