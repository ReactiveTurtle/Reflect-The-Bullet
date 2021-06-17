package ru.reactiveturtle.reflectthebullet.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.Drawable;
import ru.reactiveturtle.reflectthebullet.toolkit.PixmapExtensions;
import ru.reactiveturtle.reflectthebullet.level.LevelType;

public class MenuBack implements Drawable, Disposable {
    private Sprite mMenuBack;
    private Texture mMenuBackTexture;

    public MenuBack(DisplayMetrics displayMetrics) {
        Texture texture = new Texture(Gdx.files.internal("sky.png"));
        texture.getTextureData().prepare();
        Pixmap src = texture.getTextureData().consumePixmap();
        Pixmap pixmap = new Pixmap(displayMetrics.widthPixels(), displayMetrics.heightPixels(), Pixmap.Format.RGB888);
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        texture.dispose();
        src.dispose();

        texture = new Texture(Gdx.files.internal("sun.png"));
        texture.getTextureData().prepare();
        src = texture.getTextureData().consumePixmap();
        pixmap.drawPixmap(src,
                0,
                0,
                src.getWidth(),
                src.getHeight(),
                -displayMetrics.widthPixels() / 2,
                -displayMetrics.widthPixels() / 2,
                displayMetrics.widthPixels(),
                displayMetrics.widthPixels() * src.getHeight() / src.getWidth());
        texture.dispose();
        src.dispose();

        mMenuBackTexture = new Texture(pixmap);

        mMenuBack = new Sprite(mMenuBackTexture);
        mMenuBack.setSize(displayMetrics.widthPixels(), displayMetrics.heightPixels());
    }

    public void setBackground(LevelType levelType) {
        String textureName;
        switch (levelType) {
            case DESERT:
                textureName = "desert_back.png";
                break;
            case CELT:
                textureName = "country_back.png";
                break;
            default:
                throw new EnumConstantNotPresentException(LevelType.class, levelType.name());
        }
        mMenuBack.setTexture(new Texture(PixmapExtensions.castShadow(
                PixmapExtensions.getLevelBack(textureName), 0.5f)));
    }

    public void setMenuBackground() {
        mMenuBack.setTexture(mMenuBackTexture);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        mMenuBack.draw(spriteBatch);
    }

    @Override
    public void dispose() {
        mMenuBackTexture.dispose();
    }
}
