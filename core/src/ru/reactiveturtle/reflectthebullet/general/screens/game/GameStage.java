package ru.reactiveturtle.reflectthebullet.general.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.Timer;
import java.util.TimerTask;

import ru.reactiveturtle.reflectthebullet.Button;
import ru.reactiveturtle.reflectthebullet.general.helpers.PixmapHelper;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;
import static ru.reactiveturtle.reflectthebullet.general.GameData.height;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class GameStage extends Stage {
    private Button shootButton;
    private Image bulletsCountImage;
    private ActionListener actionListener;

    public GameStage() {
        shootButton = new Button("");
        shootButton.setUserObject("shoot");
        shootButton.getColor().a = 0.8f;
        shootButton.setSize(width() / 6f, width() / 6f);
        shootButton.setX(width() - shootButton.getWidth() - width() / 12f);
        shootButton.setY(width() / 12f);
        shootButton.setTexture("shoot_button_up.png", Button.TouchState.UP);
        shootButton.setTexture("shoot_button_down.png", Button.TouchState.DOWN);
        shootButton.setDrawable(shootButton.getDrawable(Button.TouchState.DOWN), Button.TouchState.OVER);
        shootButton.setDrawable(shootButton.getDrawable(Button.TouchState.DOWN), Button.TouchState.CHECKED_OVER);
        addActor(shootButton);
        shootButton.addListener(mClickListener);
        bulletsCountImage = new Image();
        bulletsCountImage.getColor().a = 0.8f;
        bulletsCountImage.setHeight(width() / 24f);
        addActor(bulletsCountImage);
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {
            if (actionListener != null) {
                actionListener.onClick("escape");
            }
        }
        return super.keyDown(keyCode);
    }

    private ClickListener mClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if (actionListener != null) {
                actionListener.onClick((String) event.getListenerActor().getUserObject());
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (actionListener != null) {
                actionListener.onUp((String) event.getListenerActor().getUserObject());
            }
            super.touchUp(event, x, y, pointer, button);
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (actionListener != null) {
                actionListener.onDown((String) event.getListenerActor().getUserObject());
            }
            return super.touchDown(event, x, y, pointer, button);
        }
    };

    void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void showBulletsCount(int bulletsCount) {
        Texture texture = new Texture(Gdx.files.internal("cartridge.png"));
        texture.getTextureData().prepare();
        Pixmap src = PixmapHelper.rotatePixmapTo90(texture.getTextureData().consumePixmap());
        Pixmap pixmap = new Pixmap((src.getWidth() + src.getWidth()) * bulletsCount,
                src.getHeight(), Pixmap.Format.RGBA4444);
        for (int i = 0; i < bulletsCount; i++) {
            pixmap.drawPixmap(src, (src.getWidth() + src.getWidth()) * i, 0);
        }
        bulletsCountImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        bulletsCountImage.setWidth(bulletsCountImage.getHeight() * pixmap.getWidth() / pixmap.getHeight());
        bulletsCountImage.setPosition(width() / 12f + width() / 48f, width() / 12f + width() / 48f);
    }

    public interface ActionListener {
        void onClick(String id);

        void onUp(String id);

        void onDown(String id);
    }
}
