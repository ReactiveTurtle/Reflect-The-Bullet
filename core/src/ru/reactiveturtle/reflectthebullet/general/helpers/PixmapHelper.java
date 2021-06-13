package ru.reactiveturtle.reflectthebullet.general.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class PixmapHelper {
    public static Pixmap rotatePixmapTo90(Pixmap srcPix) {
        final int width = srcPix.getWidth();
        final int height = srcPix.getHeight();
        Pixmap rotatedPix = new Pixmap(height, width, srcPix.getFormat());

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                rotatedPix.drawPixel(x, y, srcPix.getPixel(width - y - 1, x));
            }
        }

        srcPix.dispose();
        return rotatedPix;
    }

    public static Pixmap getRectPixmap(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

    public static Pixmap getRoundRectPixmap(Color color, int width, int height, int radius) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(pixmap.getWidth() - radius, radius, radius);
        pixmap.fillCircle(pixmap.getWidth() - radius, pixmap.getHeight() - radius, radius);
        pixmap.fillCircle(radius, pixmap.getHeight() - radius, radius);
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight() - radius * 2);
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - radius * 2, pixmap.getHeight());
        return pixmap;
    }

    public static Pixmap getCirclePixmap(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, (pixmap.getHeight() + pixmap.getWidth()) / 4);
        return pixmap;
    }

    public static Texture getCircleTexture(Color color, String image) {
        Texture texture = new Texture(Gdx.files.internal(image));
        texture.getTextureData().prepare();
        Pixmap src = texture.getTextureData().consumePixmap();
        Pixmap pixmap = new Pixmap(texture.getWidth(), texture.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.drawCircle(texture.getWidth() / 2, texture.getHeight() / 2, (texture.getWidth() + texture.getHeight()) / 4);
        pixmap.drawPixmap(src, 0, 0);
        src.dispose();
        texture.dispose();
        return new Texture(pixmap);
    }

    public static Pixmap getLevelBack(String textureName) {
        Texture texture = new Texture(Gdx.files.internal("sky.png"));
        texture.getTextureData().prepare();
        Pixmap src = texture.getTextureData().consumePixmap();
        Pixmap pixmap = new Pixmap(width(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        src.dispose();
        texture.dispose();
        texture = new Texture(Gdx.files.internal(textureName));
        texture.getTextureData().prepare();
        src = texture.getTextureData().consumePixmap();
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(),
                0, pixmap.getHeight() - pixmap.getWidth() * src.getHeight() / src.getWidth(),
                pixmap.getWidth(), pixmap.getWidth() * src.getHeight() / src.getWidth());
        return pixmap;
    }

    public static Pixmap castShadow(Pixmap pixmap, float shadow) {
        Color color = Color.BLACK;
        color.a = shadow;
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

    public static Texture getLevelTypeMenuItem(String textureName, int imageWidth, int imageHeight) {
        Texture texture = new Texture(Gdx.files.internal("sky.png"));
        texture.getTextureData().prepare();
        Pixmap src = texture.getTextureData().consumePixmap();
        Pixmap pixmap = new Pixmap(width(), imageHeight, Pixmap.Format.RGBA8888);
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(), (width() - imageWidth) / 2, 0, imageWidth, imageHeight);
        texture.dispose();
        src.dispose();
        texture = new Texture(Gdx.files.internal(textureName));
        texture.getTextureData().prepare();
        src = texture.getTextureData().consumePixmap();
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(),
                (width() - imageWidth) / 2, imageHeight - imageWidth * src.getHeight() / src.getWidth(), imageWidth,
                imageWidth * src.getHeight() / src.getWidth());
        texture.dispose();
        src.dispose();
        return new Texture(pixmap);
    }
}
