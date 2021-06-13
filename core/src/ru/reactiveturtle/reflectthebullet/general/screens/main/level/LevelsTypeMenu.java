package ru.reactiveturtle.reflectthebullet.general.screens.main.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.Button;
import ru.reactiveturtle.reflectthebullet.general.helpers.PixmapHelper;

import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class LevelsTypeMenu extends Stage {
    private final int xDelta = width() / 8;
    private final int imageWidth = width() * 4 / 8;
    private final int imageHeight = width() * 4 / 8 * 16 / 9;
    private static final Color BUTTON_UP_COLOR = Color.valueOf("#1faa00");
    private static final Color BUTTON_OVER_COLOR = Color.ORANGE;
    private static final Color BUTTON_DOWN_COLOR = Color.FIREBRICK;

    private ActionListener actionListener;

    public LevelsTypeMenu() {
        Table container = new Table();
        container.align(Align.center);
        container.setSize(width(), Gdx.graphics.getHeight());
        Table table = new Table();
        table.setHeight(Gdx.graphics.getHeight());
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.layout();
        scrollPane.setTransform(true);
        scrollPane.setSmoothScrolling(true);
        scrollPane.setSize(width(), Gdx.graphics.getHeight());
        container.add(scrollPane);
        addActor(container);
        addLevelType(table, "desert_back.png", "Пустыня", "desert_open");
        addLevelType(table, "celt_back.png", "Кельтская долина", "celt_open");
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

    public void addLevelType(Table container, String textureName, String levelName, String actionId) {
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setSize(width(), Gdx.graphics.getHeight());
        verticalGroup.setTransform(true);
        Image image = new Image(PixmapHelper.getLevelTypeMenuItem(textureName, imageWidth, imageHeight));
        image.setSize(width(), imageHeight);
        image.setUserObject(actionId);
        image.addListener(mClickListener);
        verticalGroup.addActor(image);

        Button button = new Button("Открыть");
        button.setSize(width() / 2f, width() / 8f);
        button.setColor(BUTTON_UP_COLOR, Button.TouchState.UP);
        button.setColor(BUTTON_DOWN_COLOR, Button.TouchState.DOWN);
        button.setColor(BUTTON_OVER_COLOR, Button.TouchState.OVER);
        button.setUserObject(actionId);
        button.addListener(mClickListener);
        verticalGroup.addActor(button);
        verticalGroup.space(width() / 16);
        container.add(verticalGroup);
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

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onAction(String id);
    }
}
