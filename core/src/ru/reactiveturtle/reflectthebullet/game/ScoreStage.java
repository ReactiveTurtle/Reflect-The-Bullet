package ru.reactiveturtle.reflectthebullet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.Timer;
import java.util.TimerTask;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;

public class ScoreStage extends Stage {
    private Label bestScoreLabel, scoreLabel;
    private Timer timer;

    public ScoreStage(GameContext gameContext) {
        super(gameContext);

        DisplayMetrics displayMetrics = gameContext.getDisplayMetrics();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.fontColor = Color.GOLD;
        labelStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("table.png"))));
        labelStyle.font.getData().setScale(displayMetrics.widthPixels() / 1280f);
        bestScoreLabel = new Label("", labelStyle);
        bestScoreLabel.setAlignment(Align.center);
        bestScoreLabel.setSize(displayMetrics.widthPixels() / 2.25f, displayMetrics.widthPixels() / 3f);
        bestScoreLabel.setPosition(displayMetrics.widthPixels() / 2f - displayMetrics.widthPixels() / 4.5f, displayMetrics.heightPixels() - bestScoreLabel.getHeight());
        addActor(bestScoreLabel);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.fontColor = Color.FIREBRICK;
        labelStyle.font.getData().setScale(displayMetrics.widthPixels() / 1024f);
        scoreLabel = new Label("", labelStyle);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setSize(displayMetrics.widthPixels(), displayMetrics.widthPixels() / 12);
        scoreLabel.setVisible(false);
        scoreLabel.setPosition(0, displayMetrics.heightPixels() / 2f - scoreLabel.getHeight() / 2f);
        addActor(scoreLabel);
        updateBestScore(0);
    }

    public void updateBestScore(int bestScore) {
        bestScoreLabel.setText("\n\nРезультат: " + bestScore);
    }

    public void showScore(String text) {
        scoreLabel.setText(text);
        System.out.println(text);
        final int[] i = new int[]{0};
        if (timer != null) {
            timer.cancel();
            timer = null;
            scoreLabel.getColor().a = 1;
            scoreLabel.setVisible(false);
        }
        scoreLabel.setVisible(true);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i[0] == 125) {
                    scoreLabel.getColor().a = 1;
                    scoreLabel.setVisible(false);
                    cancel();
                    timer = null;
                } else {
                    scoreLabel.getColor().a -= 0.008;
                    i[0] += 1;
                }
            }
        }, 0, 10);
    }

    public void setScoreTableParams(Color textColor, int y) {
        bestScoreLabel.getStyle().fontColor = textColor;
        bestScoreLabel.setY(y - bestScoreLabel.getHeight());
    }
}
