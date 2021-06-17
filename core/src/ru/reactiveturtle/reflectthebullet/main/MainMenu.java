package ru.reactiveturtle.reflectthebullet.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.reactiveturtle.reflectthebullet.Button;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;

public class MainMenu extends Stage {
    private ActionListener actionListener;
    private static final Color BUTTON_UP_COLOR = Color.valueOf("#1faa00");
    private static final Color BUTTON_DOWN_COLOR = Color.FIREBRICK;
    private final int BUTTON_WIDTH;
    private final int BUTTON_HEIGHT;

    public MainMenu(GameContext gameContext) {
        super(gameContext);
        BUTTON_WIDTH =  gameContext.getDisplayMetrics().widthPixels() / 2;
        BUTTON_HEIGHT = gameContext.getDisplayMetrics().heightPixels() / 8;

        createButton(
                "Продолжить",
                Action.CONTINUE,
                gameContext.getDisplayMetrics().heightPixels() / 2f + BUTTON_HEIGHT + BUTTON_HEIGHT * 3 / 16f);

        createButton(
                "Уровни",
                Action.SELECT_LEVEL,
                gameContext.getDisplayMetrics().heightPixels() / 2f + BUTTON_HEIGHT / 16f);

        createButton(
                "Настройки",
                Action.SETTINGS,
                gameContext.getDisplayMetrics().heightPixels() / 2f - BUTTON_HEIGHT - BUTTON_HEIGHT / 16f);

        createButton(
                "Выйти из игры",
                Action.EXIT,
                gameContext.getDisplayMetrics().heightPixels() / 2f - BUTTON_HEIGHT * 2f - BUTTON_HEIGHT * 3 / 16f);
    }

    private void createButton(String text, Action action, float y) {
        Button button = new Button(text);
        button.setUserObject(action);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setX(getGameContext().getDisplayMetrics().widthPixels() / 2f - button.getWidth() / 2f);
        button.setY(y);
        button.setColor(BUTTON_UP_COLOR, Button.TouchState.UP);
        button.setColor(BUTTON_DOWN_COLOR, Button.TouchState.DOWN);
        button.addListener(mClickListener);
        addActor(button);
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

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onAction(Action action);
    }

    public enum Action {
        CONTINUE,
        SELECT_LEVEL,
        SETTINGS,
        EXIT
    }
}
