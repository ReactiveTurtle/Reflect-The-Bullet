package ru.reactiveturtle.reflectthebullet.general.screens.world;

import com.badlogic.gdx.physics.box2d.World;

interface Level {
    void loadTo(Keeper keeper, World world);
}