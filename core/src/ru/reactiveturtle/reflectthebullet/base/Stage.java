package ru.reactiveturtle.reflectthebullet.base;

public class Stage extends com.badlogic.gdx.scenes.scene2d.Stage {
    private GameContext gameContext;

    public Stage(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void draw() {
        super.draw();
    }

    public GameContext getGameContext() {
        return gameContext;
    }
}
