package ru.reactiveturtle.reflectthebullet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.reactiveturtle.reflectthebullet.Button;
import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;

public class PauseMenu extends Stage {
    private ActionListener actionListener;
    private static final Color BUTTON_UP_COLOR = Color.valueOf("#1faa00");
    private static final Color BUTTON_DOWN_COLOR = Color.FIREBRICK;
    private final int BUTTON_WIDTH;
    private final int BUTTON_HEIGHT;

    public PauseMenu(GameContext gameContext) {
        super(gameContext);

        DisplayMetrics displayMetrics = gameContext.getDisplayMetrics();

        BUTTON_WIDTH = displayMetrics.widthPixels() / 2;
        BUTTON_HEIGHT = displayMetrics.widthPixels() / 8;

        createButton("Продолжить", Action.CONTINUE,
                Gdx.graphics.getHeight() / 2f + BUTTON_HEIGHT / 2f + BUTTON_HEIGHT / 8f);

        createButton("Настройки", Action.SETTINGS,
                Gdx.graphics.getHeight() / 2f - BUTTON_HEIGHT / 2f);

        createButton("Выйти в меню", Action.EXIT,
                Gdx.graphics.getHeight() / 2f - BUTTON_HEIGHT / 2f - BUTTON_HEIGHT / 8f - BUTTON_HEIGHT);
    }

    private void createButton(String text, Action action, float y) {
        Button button = new Button(getGameContext().getDisplayMetrics(), text);
        button.setUserObject(action);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setX(getGameContext().getDisplayMetrics().widthPixels() / 2f - button.getWidth() / 2f);
        button.setY(y);
        button.setColor(BUTTON_UP_COLOR, Button.TouchState.UP);
        button.setColor(BUTTON_DOWN_COLOR, Button.TouchState.DOWN);
        button.addListener(mClickListener);
        addActor(button);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {
            if (actionListener != null) {
                actionListener.onAction(Action.ESCAPE);
            }
        }
        return super.keyDown(keyCode);
    }

    private ClickListener mClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if (actionListener != null) {
                actionListener.onAction((Action) event.getListenerActor().getUserObject());
            }
        }
    };

    void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public interface ActionListener {
        void onAction(Action action);
    }

    public enum Action {
        CONTINUE,
        SETTINGS,
        ESCAPE,
        EXIT
    }
}
