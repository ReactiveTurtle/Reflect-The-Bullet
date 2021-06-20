package ru.reactiveturtle.reflectthebullet.level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;


public class LevelsMenu extends Stage {
    private List<LevelBox> mLevelBoxes = new ArrayList<>();
    private ActionListener actionListener;

    public LevelsMenu(GameContext gameContext) {
        super(gameContext);
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

    public void showLevels(LevelType levelType, List<LevelData> levelDataList) {
        clear();

        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        for (int i = 0; i < mLevelBoxes.size(); i++) {
            mLevelBoxes.get(i).dispose();
        }
        mLevelBoxes.clear();
        int levelBoxSize = (int) (displayMetrics.widthPixels() / 4f);
        int space = (int) (levelBoxSize / 4f);
        Color color = getLevelTypeColor(levelType);
        for (int i = 0; i < levelDataList.size() / 3f; i++) {
            for (int j = 0; j < (levelDataList.size() / 3 == i ? levelDataList.size() % 3 : 3); j++) {
                LevelData levelData = levelDataList.get(i * 3 + j);
                mLevelBoxes.add(new LevelBox(this, levelType, color,
                        levelData, i, j, levelBoxSize, space, mClickListener));
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        for (int i = 0; i < mLevelBoxes.size(); i++) {
            mLevelBoxes.get(i).dispose();
        }
    }

    private ClickListener mClickListener = new ClickListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            mLevelBoxes.get(Integer.parseInt(((String) event.getListenerActor().getUserObject()).split("&")[1]) - 1).press();
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            mLevelBoxes.get(Integer.parseInt(((String) event.getListenerActor().getUserObject()).split("&")[1]) - 1).release();
            System.out.println("touch up");
            super.touchUp(event, x, y, pointer, button);
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            mLevelBoxes.get(Integer.parseInt(((String) event.getListenerActor().getUserObject()).split("&")[1]) - 1).release();
            if (actionListener != null) {
                actionListener.onLevelClick((LevelStoreData) event.getListenerActor().getUserObject());
            }
        }
    };

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onAction(Action action);

        void onLevelClick(LevelStoreData levelStoreData);
    }

    private Color getLevelTypeColor(LevelType levelType) {
        System.out.println(levelType);
        switch (levelType) {
            case DESERT:
                return Color.GOLDENROD;
            case CELT:
                return Color.OLIVE;
        }
        return Color.GRAY;
    }


    public enum Action {
        ESCAPE
    }
}
