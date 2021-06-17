package ru.reactiveturtle.reflectthebullet.main.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.toolkit.PixmapExtensions;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;

public class SettingsMenu extends Stage {
    private final CheckBox mMusicCheckBox;
    private final CheckBox mSoundFxCheckBox;
    private final Slider mMusicSlider;
    private final Slider mSoundFxSlider;

    private ActionListener actionListener;

    public SettingsMenu(GameContext gameContext) {
        super(gameContext);

        DisplayMetrics displayMetrics = gameContext.getDisplayMetrics();
        Settings settings = gameContext.getSettings();

        EventListener checkBoxEventListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (actionListener != null) {
                    actionListener.onStateChanged(getSettings());
                    actionListener.applySettings(getSettings());
                }
            }
        };
        ChangeListener sliderChangeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (actionListener != null) {
                    actionListener.onStateChanged(getSettings());
                }
            }
        };
        ClickListener sliderClickListener = new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                actionListener.applySettings(getSettings());
            }
        };
        mMusicCheckBox = getCheckBox("Фоновая музыка: ", settings.isMusicPlaying(),
                Gdx.graphics.getHeight() / 2f + displayMetrics.widthPixels() / 16f + displayMetrics.widthPixels() / 16f, checkBoxEventListener);
        mSoundFxCheckBox = getCheckBox("Звуковые эффекты: ", settings.isSoundFxPlaying(),
                Gdx.graphics.getHeight() / 2f - displayMetrics.widthPixels() / 16f - displayMetrics.widthPixels() / 64f, checkBoxEventListener);
        mMusicSlider = getSlider(settings.getMusicVolume(),
                (mSoundFxCheckBox.getX() + mSoundFxCheckBox.getWidth() - displayMetrics.widthPixels() / 2f) * 2f,
                Gdx.graphics.getHeight() / 2f + displayMetrics.widthPixels() / 64f);
        mMusicSlider.addListener(sliderChangeListener);
        mMusicSlider.addListener(sliderClickListener);

        mSoundFxSlider = getSlider(settings.getSoundFxVolume(),
                (mSoundFxCheckBox.getX() + mSoundFxCheckBox.getWidth() - displayMetrics.widthPixels() / 2f) * 2f,
                Gdx.graphics.getHeight() / 2f - displayMetrics.widthPixels() / 8f - displayMetrics.widthPixels() / 16f);
        mSoundFxSlider.addListener(sliderChangeListener);
        mSoundFxSlider.addListener(sliderClickListener);
    }

    public void updateSettings(Settings settings) {
        mMusicCheckBox.setChecked(settings.isMusicPlaying());
        mMusicSlider.setValue(settings.getMusicVolume());
        mSoundFxCheckBox.setChecked(settings.isSoundFxPlaying());
        mSoundFxSlider.setValue(settings.getSoundFxVolume());
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {
            if (actionListener != null) {
                actionListener.onBackPressed();
            }
        }
        return super.keyDown(keyCode);
    }

    public Settings getSettings() {
        return new Settings(
                mMusicCheckBox.isChecked(),
                mMusicSlider.getValue(),
                mSoundFxCheckBox.isChecked(),
                mSoundFxSlider.getValue());
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onStateChanged(Settings settings);

        void applySettings(Settings settings);

        void onBackPressed();
    }

    private CheckBox getCheckBox(String text, boolean isChecked, float y, EventListener eventListener) {
        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.font.setColor(Color.WHITE);
        labelStyle.font.getData().setScale(displayMetrics.widthPixels() / 1536f);
        Label label = new Label(text, labelStyle);
        label.setHeight(displayMetrics.widthPixels() / 16f);
        label.setAlignment(Align.center);
        label.setPosition(displayMetrics.widthPixels() / 2f - label.getWidth() / 2f - displayMetrics.widthPixels() / 32f, y);
        addActor(label);

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = labelStyle.font;
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(scaleTexture(
                new Texture(Gdx.files.internal("unchecked.png")),
                displayMetrics.widthPixels() / 16, displayMetrics.widthPixels() / 16));
        checkBoxStyle.checkboxOn = new TextureRegionDrawable(scaleTexture(
                new Texture(Gdx.files.internal("checked.png")),
                displayMetrics.widthPixels() / 16, displayMetrics.widthPixels() / 16));
        final CheckBox checkBox = new CheckBox("", checkBoxStyle);
        checkBox.setSize(displayMetrics.widthPixels() / 16f, displayMetrics.widthPixels() / 16f);
        checkBox.setPosition(label.getX() + label.getWidth() + checkBox.getWidth() / 4f, label.getY());
        checkBox.setChecked(isChecked);
        checkBox.addListener(eventListener);
        addActor(checkBox);
        return checkBox;
    }

    private Slider getSlider(float progress, float width, float y) {
        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = new TextureRegionDrawable(new Texture(
                PixmapExtensions.getCirclePixmap(Color.valueOf("#ff6f00"), displayMetrics.widthPixels() / 24, displayMetrics.widthPixels() / 24)));
        sliderStyle.knobBefore = new TextureRegionDrawable(new Texture(
                PixmapExtensions.getRectPixmap(Color.valueOf("#689f38"), displayMetrics.widthPixels() / 4, displayMetrics.widthPixels() / 128)));
        sliderStyle.background = new TextureRegionDrawable(new Texture(
                PixmapExtensions.getRectPixmap(Color.GRAY, displayMetrics.widthPixels() / 4, displayMetrics.widthPixels() / 128)));
        Slider slider = new Slider(0, 1.0f, 0.005f, false, sliderStyle);
        slider.setDisabled(false);
        slider.setValue(progress);
        slider.setSize(width, displayMetrics.widthPixels() / 16f);
        slider.setPosition((displayMetrics.widthPixels() - slider.getWidth()) / 2f, y);
        addActor(slider);
        return slider;
    }

    private static Texture scaleTexture(Texture texture, int dstWidth, int dstHeight) {
        texture.getTextureData().prepare();
        Pixmap pixmap = new Pixmap(dstWidth, dstHeight, Pixmap.Format.RGBA8888);
        pixmap.drawPixmap(texture.getTextureData().consumePixmap(), 0, 0, texture.getWidth(), texture.getHeight(),
                0, 0, dstWidth, dstHeight);
        texture.dispose();
        return new Texture(pixmap);
    }
}
