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

    public void showLevels(LevelType levelType) {
        clear();

        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        for (int i = 0; i < mLevelBoxes.size(); i++) {
            mLevelBoxes.get(i).dispose();
        }
        mLevelBoxes.clear();
        int levelBoxSize = (int) (displayMetrics.widthPixels() / 4f);
        int space = (int) (levelBoxSize / 4f);
        Color color = getLevelTypeColor(levelType);
        List<String> levelDirectoriesList = getGameContext().getLevelRepository()
                .getLevelDirectoriesList(levelType);
        List<LevelData> levelDataList = getGameContext().getLevelRepository().getLevelDataList(levelType);
        List<LevelRequirements> levelRequirementsList = getGameContext().getLevelRepository()
                .getLevelRequirementsList(levelType);
        for (int i = 0; i < levelDataList.size() / 3f; i++) {
            for (int j = 0; j < (levelDataList.size() / 3 == i ? levelDataList.size() % 3 : 3); j++) {
                int rectifiedIndex = i * 3 + j;
                String levelDirectory = levelDirectoriesList.get(rectifiedIndex);
                LevelData levelData = levelDataList.get(rectifiedIndex);
                LevelRequirements levelRequirements = levelRequirementsList.get(rectifiedIndex);
                mLevelBoxes.add(new LevelBox(
                        this,
                        levelType,
                        color,
                        levelDirectory,
                        levelRequirements,
                        levelData,
                        isLevelAvailable(levelRequirements, levelData),
                        i,
                        j,
                        levelBoxSize,
                        space,
                        mClickListener));
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
            LevelBox.LevelBoxData levelBoxData = (LevelBox.LevelBoxData) event.getListenerActor().getUserObject();
            mLevelBoxes.get(levelBoxData.getId()).press();
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            LevelBox.LevelBoxData levelBoxData = (LevelBox.LevelBoxData) event.getListenerActor().getUserObject();
            mLevelBoxes.get(levelBoxData.getId()).release();
            System.out.println("touch up");
            super.touchUp(event, x, y, pointer, button);
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            LevelBox.LevelBoxData levelBoxData = (LevelBox.LevelBoxData) event.getListenerActor().getUserObject();
            mLevelBoxes.get(levelBoxData.getId()).release();
            if (actionListener != null) {
                actionListener.onLevelClick(levelBoxData.getRelativeLevelDirectory());
            }
        }
    };

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onAction(Action action);

        void onLevelClick(String relativeLevelDirectory);
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

    private boolean isLevelAvailable(LevelRequirements levelRequirements, LevelData levelData) {
        if (levelData.isFinished()) {
            return true;
        }
        String[] requiredLevelDirectories = levelRequirements.getRequiredLevelFiles();
        for (String relativeDirectory : requiredLevelDirectories) {
            LevelData requiredLevelData = getGameContext().getLevelRepository()
                    .getLevelData(relativeDirectory);
            if (!requiredLevelData.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public enum Action {
        ESCAPE
    }
}
