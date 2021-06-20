package ru.reactiveturtle.reflectthebullet.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage extends com.badlogic.gdx.scenes.scene2d.Stage {
    private GameContext gameContext;

    public Stage(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public SpriteBatch getSpriteBatch() {
        return (SpriteBatch) getBatch();
    }
}
