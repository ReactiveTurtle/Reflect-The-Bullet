package ru.reactiveturtle.reflectthebullet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.reactiveturtle.reflectthebullet.Button;

import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class PauseMenu extends Stage {
    private ActionListener actionListener;
    private static final Color BUTTON_UP_COLOR = Color.valueOf("#1faa00");
    private static final Color BUTTON_DOWN_COLOR = Color.FIREBRICK;
    private static final int BUTTON_WIDTH = width() / 2;
    private static final int BUTTON_HEIGHT = width() / 8;

    public PauseMenu() {
        createButton("Продолжить", "pause_continue",
                Gdx.graphics.getHeight() / 2f + BUTTON_HEIGHT / 2f + BUTTON_HEIGHT / 8f);

        createButton("Настройки", "pause_settings",
                Gdx.graphics.getHeight() / 2f - BUTTON_HEIGHT / 2f);

        createButton("Выйти в меню", "pause_exit",
                Gdx.graphics.getHeight() / 2f - BUTTON_HEIGHT / 2f - BUTTON_HEIGHT / 8f - BUTTON_HEIGHT);
    }

    private void createButton(String text, String id, float y) {
        Button button = new Button(text);
        button.setUserObject(id);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setX(width() / 2f - button.getWidth() / 2f);
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
                actionListener.onAction("escape");
            }
        }
        return super.keyDown(keyCode);
    }

    private ClickListener mClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if (actionListener != null) {
                actionListener.onAction((String) event.getListenerActor().getUserObject());
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
        void onAction(String id);
    }
}
