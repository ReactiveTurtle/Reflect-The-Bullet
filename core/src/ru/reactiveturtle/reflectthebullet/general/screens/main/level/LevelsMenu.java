package ru.reactiveturtle.reflectthebullet.general.screens.main.level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class LevelsMenu extends Stage {
    private List<LevelBox> mLevelBoxes = new ArrayList<>();
    private ActionListener actionListener;

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {
            if (actionListener != null) {
                actionListener.onAction("escape");
            }
        }
        return super.keyDown(keyCode);
    }

    public void showLevels(String levelType, List<LevelInfo> levelInfoList) {
        clear();
        for (int i = 0; i < mLevelBoxes.size(); i++) {
            mLevelBoxes.get(i).dispose();
        }
        mLevelBoxes.clear();
        int levelBoxSize = (int) (width() / 4f);
        int space = (int) (levelBoxSize / 4f);
        System.out.println(levelInfoList.size());
        LevelInfo lastLevelInfo = new LevelInfo();
        lastLevelInfo.isFinished = true;
        Color color = getLevelTypeColor(levelType);
        for (int i = 0; i < levelInfoList.size() / 3f; i++) {
            for (int j = 0; j < (levelInfoList.size() / 3 == i ? levelInfoList.size() % 3 : 3); j++) {
                LevelInfo levelInfo = levelInfoList.get(i * 3 + j);
                mLevelBoxes.add(new LevelBox(this, levelType, color,
                        levelInfo, lastLevelInfo, i, j, levelBoxSize, space, mClickListener));
                lastLevelInfo = levelInfo;
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

    private Color getLevelTypeColor(String levelType) {
        System.out.println(levelType);
        switch (levelType) {
            case "desert_open":
                return Color.GOLDENROD;
            case "celt_open":
                return Color.OLIVE;
        }
        return Color.GRAY;
    }
}
