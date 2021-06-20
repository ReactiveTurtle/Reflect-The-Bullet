package ru.reactiveturtle.reflectthebullet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.Button;
import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.level.StarExtensions;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;

public class EndLevelMenu extends Stage {
    private Image[] stars = new Image[3];
    private Label bestScoreLabel;
    private Button nextLevelButton;

    private ActionListener actionListener;

    public EndLevelMenu(GameContext gameContext) {
        super(gameContext);

        DisplayMetrics displayMetrics = gameContext.getDisplayMetrics();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.fontColor = Color.GOLD;
        labelStyle.font.getData().setScale(displayMetrics.widthPixels() / 1024f);
        bestScoreLabel = new Label("", labelStyle);
        bestScoreLabel.setAlignment(Align.center);
        bestScoreLabel.setSize(displayMetrics.widthPixels() / 1.5f, displayMetrics.widthPixels() / 6f);
        bestScoreLabel.setPosition(displayMetrics.widthPixels() / 2f - displayMetrics.widthPixels() / 3f, displayMetrics.heightPixels() * 2 / 3f);
        addActor(bestScoreLabel);

        Button exitButton = new Button(displayMetrics, "М");
        exitButton.setColor(Color.FOREST);
        exitButton.setSize(displayMetrics.widthPixels() / 6f, displayMetrics.widthPixels() / 6f);
        exitButton.setX(displayMetrics.widthPixels() / 2f - exitButton.getWidth() * 3 / 2f - exitButton.getWidth() / 4f);
        exitButton.setY(displayMetrics.widthPixels() / 12f);
        exitButton.setUserObject(Action.MENU);
        exitButton.addListener(mClickListener);
        addActor(exitButton);

        Button repeatLevelButton = new Button(displayMetrics, "П");
        repeatLevelButton.setColor(Color.FOREST);
        repeatLevelButton.setSize(displayMetrics.widthPixels() / 6f, displayMetrics.widthPixels() / 6f);
        repeatLevelButton.setX(displayMetrics.widthPixels() / 2f - repeatLevelButton.getWidth() / 2f);
        repeatLevelButton.setY(displayMetrics.widthPixels() / 12f);
        repeatLevelButton.setUserObject(Action.REPEAT);
        repeatLevelButton.addListener(mClickListener);
        addActor(repeatLevelButton);

        nextLevelButton = new Button(displayMetrics, "Н");
        nextLevelButton.setColor(Color.FOREST);
        nextLevelButton.setSize(displayMetrics.widthPixels() / 6f, displayMetrics.widthPixels() / 6f);
        nextLevelButton.setX(displayMetrics.widthPixels() / 2f + nextLevelButton.getWidth() / 2f + nextLevelButton.getWidth() / 4f);
        nextLevelButton.setY(displayMetrics.widthPixels() / 12f);
        nextLevelButton.setUserObject(Action.NEXT);
        nextLevelButton.addListener(mClickListener);
        addActor(nextLevelButton);

        for (int i = 0; i < 3; i++) {
            stars[i] = new Image(new Texture(Gdx.files.internal("star.png")));
            stars[i].setBounds(
                    displayMetrics.widthPixels() * (4 + 8 * i) / 30f,
                    displayMetrics.heightPixels() - displayMetrics.widthPixels() * 6 / 30f - displayMetrics.widthPixels() / 10f,
                    displayMetrics.widthPixels() * 6 / 30f,
                    displayMetrics.widthPixels() * 6 / 30f);
            addActor(stars[i]);
        }
        stars[1].setY(stars[1].getY() + displayMetrics.widthPixels() / 20f);
    }

    public void showScore(int bestScore, int score, StarExtensions.Star star) {
        bestScoreLabel.setText("Ваш лучший результат: " + bestScore + "\nВаш результат: " + score);
        for (int i = 0; i < star.getInt(); i++) {
            stars[i].setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("star.png"))));
        }
        for (int i = star.getInt(); i < 3; i++) {
            stars[i].setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("star_t.png"))));
        }
    }

    private final ClickListener mClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if (actionListener != null) {
                actionListener.onAction((Action) event.getListenerActor().getUserObject());
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
        void onAction(Action action);
    }

    public enum Action {
        MENU,
        REPEAT,
        NEXT
    }
}
