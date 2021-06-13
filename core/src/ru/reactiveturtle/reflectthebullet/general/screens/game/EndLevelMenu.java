package ru.reactiveturtle.reflectthebullet.general.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.Button;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;
import static ru.reactiveturtle.reflectthebullet.general.GameData.height;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class EndLevelMenu extends Stage {
    private Image[] stars = new Image[3];
    private Label bestScoreLabel;
    private Button exitButton;
    private Button repeatLevelButton;
    private Button nextLevelButton;

    private ActionListener actionListener;

    public EndLevelMenu() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.fontColor = Color.GOLD;
        labelStyle.font.getData().setScale(width() / 1024f);
        bestScoreLabel = new Label("", labelStyle);
        bestScoreLabel.setAlignment(Align.center);
        bestScoreLabel.setSize(width() / 1.5f, width() / 6f);
        bestScoreLabel.setPosition(width() / 2f - width() / 3f, height() * 2 / 3f);
        addActor(bestScoreLabel);

        exitButton = new Button("М");
        exitButton.setColor(Color.FOREST);
        exitButton.setSize(width() / 6f, width() / 6f);
        exitButton.setX(width() / 2f - exitButton.getWidth() * 3 / 2f - exitButton.getWidth() / 4f);
        exitButton.setY(width() / 12f);
        exitButton.setUserObject("exit");
        exitButton.addListener(mClickListener);
        addActor(exitButton);

        repeatLevelButton = new Button("П");
        repeatLevelButton.setColor(Color.FOREST);
        repeatLevelButton.setSize(width() / 6f, width() / 6f);
        repeatLevelButton.setX(width() / 2f - repeatLevelButton.getWidth() / 2f);
        repeatLevelButton.setY(width() / 12f);
        repeatLevelButton.setUserObject("repeat");
        repeatLevelButton.addListener(mClickListener);
        addActor(repeatLevelButton);

        nextLevelButton = new Button("Н");
        nextLevelButton.setColor(Color.FOREST);
        nextLevelButton.setSize(width() / 6f, width() / 6f);
        nextLevelButton.setX(width() / 2f + nextLevelButton.getWidth() / 2f + nextLevelButton.getWidth() / 4f);
        nextLevelButton.setY(width() / 12f);
        nextLevelButton.setUserObject("next");
        nextLevelButton.addListener(mClickListener);
        addActor(nextLevelButton);

        for (int i = 0; i < 3; i++) {
            stars[i] = new Image(new Texture(Gdx.files.internal("star.png")));
            stars[i].setBounds(width() * (4 + 8 * i) / 30f, height() - width() * 6 / 30f - width()  / 10f,
                    width() * 6 / 30f, width() * 6 / 30f);
            addActor(stars[i]);
        }
        stars[1].setY(stars[1].getY() + width() / 20f);
    }

    public void showScore(int bestScore, int score, int starsCount) {
        bestScoreLabel.setText("Ваш лучший результат: " + bestScore + "\nВаш результат: " + score);
        for (int i = 0; i < starsCount; i++) {
            stars[i].setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("star.png"))));
        }
        for (int i = starsCount; i < 3; i++) {
            stars[i].setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("star_t.png"))));
        }
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

    public void enableNextLevelButton() {
        nextLevelButton.setVisible(true);
    }

    public void disableNextLevelButton() {
        nextLevelButton.setVisible(false);
    }

    public interface ActionListener {
        void onAction(String id);
    }
}
