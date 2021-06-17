package ru.reactiveturtle.reflectthebullet.level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.Button;
import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.toolkit.PixmapExtensions;

public class LevelsTypeMenu extends Stage {
    private final int imageWidth;
    private final int imageHeight;
    private static final Color BUTTON_UP_COLOR = Color.valueOf("#1faa00");
    private static final Color BUTTON_OVER_COLOR = Color.ORANGE;
    private static final Color BUTTON_DOWN_COLOR = Color.FIREBRICK;

    private ActionListener actionListener;

    public LevelsTypeMenu(GameContext gameContext) {
        super(gameContext);
        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        imageWidth = displayMetrics.widthPixels() * 4 / 8;
        imageHeight = displayMetrics.widthPixels() * 4 / 8 * 16 / 9;
        Table container = new Table();
        container.align(Align.center);
        container.setSize(displayMetrics.widthPixels(), displayMetrics.heightPixels());
        Table table = new Table();
        table.setHeight(displayMetrics.heightPixels());
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.layout();
        scrollPane.setTransform(true);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setSize(displayMetrics.widthPixels(), displayMetrics.heightPixels());
        container.add(scrollPane);
        addActor(container);
        addLevelType(table, "desert_back.png", "Пустыня", Action.DESERT);
        addLevelType(table, "celt_back.png", "Кельтская долина", Action.CELT);
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

    public void addLevelType(Table container, String textureName, String levelName, Action action) {
        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setSize(displayMetrics.widthPixels(), displayMetrics.heightPixels());
        verticalGroup.setTransform(true);
        Image image = new Image(PixmapExtensions.getLevelTypeMenuItem(textureName, imageWidth, imageHeight));
        image.setSize(displayMetrics.widthPixels(), imageHeight);
        image.setUserObject(action);
        image.addListener(mClickListener);
        verticalGroup.addActor(image);

        Button button = new Button("Открыть");
        button.setSize(displayMetrics.widthPixels() / 2f, displayMetrics.widthPixels() / 8f);
        button.setColor(BUTTON_UP_COLOR, Button.TouchState.UP);
        button.setColor(BUTTON_DOWN_COLOR, Button.TouchState.DOWN);
        button.setColor(BUTTON_OVER_COLOR, Button.TouchState.OVER);
        button.setUserObject(action);
        button.addListener(mClickListener);
        verticalGroup.addActor(button);
        verticalGroup.space(displayMetrics.widthPixels() / 16f);
        container.add(verticalGroup);
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
        DESERT,
        CELT,
        ESCAPE
    }
}
