package ru.reactiveturtle.reflectthebullet.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.general.helpers.PixmapHelper;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;

public class LevelBox {
    private List<Widget> widgetList = new ArrayList<>();
    private boolean isPressed = false;
    private final int levelBoxSize;

    public LevelBox(Stage stage,
                    LevelType levelType,
                    Color levelTypeColor,
                    LevelData levelData,
                    LevelData lastLevelData,
                    int i,
                    int j,
                    int levelBoxSize,
                    int space,
                    ClickListener mClickListener) {

        DisplayMetrics displayMetrics = stage.getGameContext().getDisplayMetrics();

        this.levelBoxSize = levelBoxSize;
        Color color = Color.BLACK;
        color.a = 0.8f;
        Image shadow = new Image(new Texture(PixmapHelper.getRoundRectPixmap(
                color, 128, 128, 32)));
        shadow.setBounds(levelBoxSize / 4f + (levelBoxSize + space) * j + levelBoxSize / 32f,
                Gdx.graphics.getHeight() - levelBoxSize / 4f - levelBoxSize * (i + 1) - space * i - levelBoxSize / 32f - displayMetrics.widthPixels() / 3f,
                levelBoxSize, levelBoxSize);
        stage.addActor(shadow);

        Image background = new Image(new Texture(PixmapHelper.getRoundRectPixmap(
                levelTypeColor, 128, 128, 32)));
        background.setUserObject(levelType + "&" + (i * 3 + j + 1));
        background.setBounds(levelBoxSize / 4f + (levelBoxSize + space) * j,
                Gdx.graphics.getHeight() - levelBoxSize / 4f - levelBoxSize * (i + 1) - space * i - displayMetrics.widthPixels() / 3f, levelBoxSize, levelBoxSize);
        stage.addActor(background);
        widgetList.add(background);

        if (lastLevelData.isFinished()) {
            LevelRequirements levelRequirements = lastLevelData.getRequirements();

            background.addListener(mClickListener);
            Image star = new Image(new Texture(Gdx.files.internal(levelData.getBestScore() < levelRequirements.getFirstStarScore() ? "star_t.png" : "star.png")));
            star.setBounds(background.getX() + levelBoxSize * 2 / 24f,
                    background.getY() + levelBoxSize * 1.85f / 3f,
                    levelBoxSize * 6 / 24f, levelBoxSize * 6 / 24f);
            star.setUserObject(levelType + "&" + (i * 3 + j + 1) + "");
            star.addListener(mClickListener);
            stage.addActor(star);
            widgetList.add(star);

            star = new Image(new Texture(Gdx.files.internal(levelData.getBestScore() < levelRequirements.getSecondStarScore() ? "star_t.png" : "star.png")));
            star.setBounds(background.getX() + levelBoxSize * 9 / 24f, background.getY() + levelBoxSize * 2 / 3f,
                    levelBoxSize * 6 / 24f, levelBoxSize * 6 / 24f);
            star.setUserObject(levelType + "&" + (i * 3 + j + 1) + "");
            star.addListener(mClickListener);
            stage.addActor(star);
            widgetList.add(star);

            star = new Image(new Texture(Gdx.files.internal(levelData.getBestScore() < levelRequirements.getThirdStarScore() ? "star_t.png" : "star.png")));
            star.setBounds(background.getX() + levelBoxSize * 16 / 24f,
                    background.getY() + levelBoxSize * 1.85f / 3f,
                    levelBoxSize * 6 / 24f, levelBoxSize * 6 / 24f);
            star.setUserObject(levelType + "&" + (i * 3 + j + 1) + "");
            star.addListener(mClickListener);
            stage.addActor(star);
            widgetList.add(star);

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
            labelStyle.font.setColor(Color.WHITE);
            labelStyle.font.getData().setScale(displayMetrics.widthPixels() / 1536f);

            Label label = new Label((i * 3 + j + 1) + "", labelStyle);
            label.setAlignment(Align.center);
            label.setBounds(background.getX(), background.getY(), background.getWidth(), background.getHeight() / 3f);
            label.setUserObject(levelType + "&" + (i * 3 + j + 1) + "");
            label.addListener(mClickListener);
            stage.addActor(label);
            widgetList.add(label);

            Label scoreLabel = new Label(levelData.getBestScore() + "", labelStyle);
            scoreLabel.setAlignment(Align.center);
            scoreLabel.setBounds(background.getX(), background.getY() + background.getHeight() / 4f, background.getWidth(), background.getHeight() / 3f);
            scoreLabel.setUserObject(levelType);
            scoreLabel.addListener(mClickListener);
            stage.addActor(scoreLabel);
            widgetList.add(scoreLabel);
        } else {
            Image lockImage = new Image(new Texture(Gdx.files.internal("lock.png")));
            lockImage.setBounds(background.getX() + levelBoxSize / 4f, background.getY() + levelBoxSize / 8f,
                    levelBoxSize / 2f, levelBoxSize / 2f);
            stage.addActor(lockImage);
            widgetList.add(lockImage);
        }
    }

    public void press() {
        if (!isPressed) {
            isPressed = true;
            for (int i = 0; i < widgetList.size(); i++) {
                Widget widget = widgetList.get(i);
                widget.setPosition(widget.getX() + levelBoxSize / 32f, widget.getY() - levelBoxSize / 32f);
            }
        }
    }

    public void release() {
        if (isPressed) {
            isPressed = false;
            for (int i = 0; i < widgetList.size(); i++) {
                Widget widget = widgetList.get(i);
                widget.setPosition(widget.getX() - levelBoxSize / 32f, widget.getY() + levelBoxSize / 32f);
            }
        }
    }

    public void dispose() {
        for (int i = 0; i < widgetList.size(); i++) {
            widgetList.get(i).remove();
        }
        widgetList.clear();
    }
}
