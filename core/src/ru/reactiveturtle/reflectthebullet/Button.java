package ru.reactiveturtle.reflectthebullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import ru.reactiveturtle.reflectthebullet.toolkit.PixmapExtensions;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class Button extends TextButton {
    private TextureRegionDrawable mTextureRegionDrawable;

    public Button() {
        super("", getTextButtonStyle());
        init();
    }

    public Button(String text) {
        super(text, getTextButtonStyle());
        init();
    }

    public void setColor(Color color, TouchState state) {
        Pixmap pixmap = new Pixmap((int) getWidth(), (int) getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle((int) (getHeight() / 2), (int) (getHeight() / 2), (int) (getHeight() / 2));
        pixmap.fillCircle((int) (getWidth() - getHeight() / 2), (int) (getHeight() / 2), (int) (getHeight() / 2));
        pixmap.fillRectangle((int) (getHeight() / 2f), 0, (int) (getWidth() - getHeight()), (int) getHeight());
        setTexture(new Texture(PixmapExtensions.getRoundRectPixmap(color, (int) getWidth(), (int) getHeight(),
                (int) (getHeight() / 2))), state);
    }

    public void setTexture(String name, TouchState state) {
        setTextureRegion(new TextureRegion(new Texture(Gdx.files.internal(name))), state);
    }

    public void setTexture(Texture texture, TouchState state) {
        setTextureRegion(new TextureRegion(texture), state);
    }

    public void setTextureRegion(TextureRegion textureRegion, TouchState state) {
        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        setDrawable(textureRegionDrawable, state);
    }

    public Drawable getDrawable(TouchState state) {
        switch (state) {
            case UP:
                return getStyle().up;
            case DOWN:
                return getStyle().down;
            case OVER:
                return getStyle().over;
            case CHECKED:
                return getStyle().checked;
            case CHECKED_OVER:
                return getStyle().checkedOver;
            case DISABLED:
                return getStyle().disabled;
        }
        return null;
    }

    private void init() {
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        pixmap.setColor(Color.GRAY);
        pixmap.fillRectangle(0, 0, 100, 100);
        mTextureRegionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        getStyle().up = mTextureRegionDrawable;
    }

    private static TextButtonStyle getTextButtonStyle() {
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        textButtonStyle.font.setColor(Color.WHITE);
        textButtonStyle.font.getData().setScale(width() / 1536f);
        return textButtonStyle;
    }

    public void setDrawable(Drawable drawable, TouchState state) {
        switch (state) {
            case UP:
                getStyle().up = drawable;
                break;
            case DOWN:
                getStyle().down = drawable;
                break;
            case OVER:
                getStyle().over = drawable;
                break;
            case CHECKED:
                getStyle().checked = drawable;
                break;
            case CHECKED_OVER:
                getStyle().checkedOver = drawable;
                break;
            case DISABLED:
                getStyle().disabled = drawable;
                break;
        }
    }

    public enum TouchState {
        UP, DOWN, OVER, CHECKED, CHECKED_OVER, DISABLED
    }
}
