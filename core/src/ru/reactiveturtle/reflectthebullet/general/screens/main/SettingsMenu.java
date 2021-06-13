package ru.reactiveturtle.reflectthebullet.general.screens.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.general.helpers.PixmapHelper;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class SettingsMenu extends Stage {
    private CheckBox mMusicCheckBox, mSoundFxCheckBox;
    private Slider mMusicSlider, mSoundFxSlider;

    private ActionListener actionListener;

    public SettingsMenu(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
        EventListener checkBoxEventListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (actionListener != null) {
                    actionListener.onStateChanged(
                            mMusicCheckBox.isChecked(), mMusicSlider.getValue(),
                            mSoundFxCheckBox.isChecked(), mSoundFxSlider.getValue());
                    actionListener.applySettings();
                }
            }
        };
        ChangeListener sliderChangeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (actionListener != null) {
                    actionListener.onStateChanged(
                            mMusicCheckBox.isChecked(), mMusicSlider.getValue(),
                            mSoundFxCheckBox.isChecked(), mSoundFxSlider.getValue());
                }
            }
        };
        ClickListener sliderClickListener = new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                actionListener.applySettings();
            }
        };
        mMusicCheckBox = getCheckBox("Фоновая музыка: ", isMusicPlaying,
                Gdx.graphics.getHeight() / 2f + width() / 16f + width() / 16f, checkBoxEventListener);
        mSoundFxCheckBox = getCheckBox("Звуковые эффекты: ", isSoundFxPlaying,
                Gdx.graphics.getHeight() / 2f - width() / 16f - width() / 64f, checkBoxEventListener);
        mMusicSlider = getSlider(musicVolume,
                (mSoundFxCheckBox.getX() + mSoundFxCheckBox.getWidth() - width() / 2f) * 2f,
                Gdx.graphics.getHeight() / 2f + width() / 64f);
        mMusicSlider.addListener(sliderChangeListener);
        mMusicSlider.addListener(sliderClickListener);
        mSoundFxSlider = getSlider(soundFxVolume,
                (mSoundFxCheckBox.getX() + mSoundFxCheckBox.getWidth() - width() / 2f) * 2f,
                Gdx.graphics.getHeight() / 2f - width() / 8f - width() / 16f);
        mSoundFxSlider.addListener(sliderChangeListener);
        mSoundFxSlider.addListener(sliderClickListener);
    }

    public void updateSettings(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
        mMusicCheckBox.setChecked(isMusicPlaying);
        mMusicSlider.setValue(musicVolume);
        mSoundFxCheckBox.setChecked(isSoundFxPlaying);
        mSoundFxSlider.setValue(soundFxVolume);
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

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onStateChanged(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume);

        void applySettings();

        void onBackPressed();
    }

    private CheckBox getCheckBox(String text, boolean isChecked, float y, EventListener eventListener) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.font.setColor(Color.WHITE);
        labelStyle.font.getData().setScale(width() / 1536f);
        Label label = new Label(text, labelStyle);
        label.setHeight(width() / 16f);
        label.setAlignment(Align.center);
        label.setPosition(width() / 2f - label.getWidth() / 2f - width() / 32f, y);
        addActor(label);

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = labelStyle.font;
        checkBoxStyle.checkboxOff = new TextureRegionDrawable(scaleTexture(
                new Texture(Gdx.files.internal("unchecked.png")),
                width() / 16, width() / 16));
        checkBoxStyle.checkboxOn = new TextureRegionDrawable(scaleTexture(
                new Texture(Gdx.files.internal("checked.png")),
                width() / 16, width() / 16));
        final CheckBox checkBox = new CheckBox("", checkBoxStyle);
        checkBox.setSize(width() / 16f, width() / 16f);
        checkBox.setPosition(label.getX() + label.getWidth() + checkBox.getWidth() / 4f, label.getY());
        checkBox.setChecked(isChecked);
        checkBox.addListener(eventListener);
        addActor(checkBox);
        return checkBox;
    }

    private Slider getSlider(float progress, float width, float y) {
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = new TextureRegionDrawable(new Texture(
                PixmapHelper.getCirclePixmap(Color.valueOf("#ff6f00"), width() / 24, width() / 24)));
        sliderStyle.knobBefore = new TextureRegionDrawable(new Texture(
                PixmapHelper.getRectPixmap(Color.valueOf("#689f38"), width() / 4, width() / 128)));
        sliderStyle.background = new TextureRegionDrawable(new Texture(
                PixmapHelper.getRectPixmap(Color.GRAY, width() / 4, width() / 128)));
        Slider slider = new Slider(0, 1.0f, 0.005f, false, sliderStyle);
        slider.setDisabled(false);
        slider.setValue(progress);
        slider.setSize(width, width() / 16f);
        slider.setPosition((width() - slider.getWidth()) / 2f, y);
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
