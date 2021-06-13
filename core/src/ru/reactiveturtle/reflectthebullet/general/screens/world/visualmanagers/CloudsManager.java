package ru.reactiveturtle.reflectthebullet.general.screens.world.visualmanagers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class CloudsManager implements VisualManager{
    private int minCount, maxCount;
    private float minX, maxX;
    private float minY, maxY;
    float minScale, maxScale;
    float minSpeed, maxSpeed;
    private float cloudHeight;
    private Texture[] clouds;
    private List<Sprite> sprites = new ArrayList<>();
    private List<Float> speeds = new ArrayList<>();

    public CloudsManager(float screenHeightPart, float cloudHeight,
                         int minCount, int maxCount,
                         float minX, float maxX,
                         float minY, float maxY,
                         float minScale, float maxScale,
                         float minSpeed, float maxSpeed, String... assets) {

        this.minX = minX;
        this.maxX = maxX;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.minY = minY;
        this.maxY = maxY;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.cloudHeight = cloudHeight;
        clouds = new Texture[assets.length];
        for (int i = 0; i < assets.length; i++) {
            clouds[i] = new Texture(Gdx.files.internal(assets[i]));
            clouds[i] = scaleTexture(clouds[i], (int) (clouds[i].getWidth() * screenHeightPart / clouds[i].getHeight()), (int) screenHeightPart);
            System.out.println(clouds[i].getWidth() + ", " + clouds[i].getHeight());
        }
        generate();
    }


    private void generate() {
        int i = (int) (Math.random() * (maxCount - minCount) + minCount);
        for (int j = 0; j < i; j++) {
            Texture texture = clouds[(int) (Math.random() * (clouds.length - 1))];
            Sprite sprite = new Sprite(texture);
            sprite.setOrigin(0, 0);
            sprite.setSize(texture.getWidth() * cloudHeight / texture.getHeight(), cloudHeight);
            sprite.setX((float) (Math.random() * (maxX - minX) + minX));
            sprite.setY((float) (Math.random() * (maxY - minY) + minY));
            sprite.setScale((float) (Math.random() * (maxScale - minScale) + minScale));
            sprites.add(sprite);
            speeds.add((float) (Math.random() * (maxSpeed - minSpeed) + minSpeed));
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float deltaTime) {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            sprites.get(i).draw(spriteBatch);
            sprite.setX(sprite.getX() + speeds.get(i) * deltaTime);
            double r = Math.random();
            if (sprite.getX() >= maxX) {
                if (r >= 0.7f) {
                    sprite.setScale((float) (Math.random() * (maxScale - minScale) + minScale));
                    sprite.setX(-sprite.getWidth() * sprite.getScaleX());
                    sprite.setY((float) (Math.random() * (maxY - minY) + minY));
                    sprites.set(i, sprite);
                    speeds.set(i, (float) (Math.random() * (maxSpeed - minSpeed) + minSpeed));
                } else if (r >= 0.5 && sprites.size() > minCount){
                    sprites.remove(i);
                    speeds.remove(i);
                    i--;
                } else if (r >= 0.3f && r < 0.5 && sprites.size() < maxCount) {
                    Texture texture = clouds[(int) (Math.random() * (clouds.length - 1))];
                    Sprite sprite1 = new Sprite(texture);
                    sprite1.setOrigin(0, 0);
                    sprite1.setSize(texture.getWidth() * cloudHeight / texture.getHeight(), cloudHeight);
                    sprite1.setScale((float) (Math.random() * (maxScale - minScale) + minScale));
                    sprite1.setX(-sprite1.getWidth() * sprite1.getScaleX());
                    sprite1.setY((float) (Math.random() * (maxY - minY) + minY));
                    sprites.add(sprite1);
                    speeds.add((float) (Math.random() * (maxSpeed - minSpeed) + minSpeed));
                    sprites.get(i).draw(spriteBatch);
                }
            } else if (r <= 0.0001f && sprites.size() < maxCount){
                Texture texture = clouds[(int) (Math.random() * (clouds.length - 1))];
                Sprite sprite1 = new Sprite(texture);
                sprite1.setOrigin(0, 0);
                sprite1.setSize(texture.getWidth() * cloudHeight / texture.getHeight(), cloudHeight);
                sprite1.setScale((float) (Math.random() * (maxScale - minScale) + minScale));
                sprite1.setX(-sprite1.getWidth() * sprite1.getScaleX());
                sprite1.setY((float) (Math.random() * (maxY - minY) + minY));
                sprites.add(sprite1);
                speeds.add((float) (Math.random() * (maxSpeed - minSpeed) + minSpeed));
                sprites.get(i).draw(spriteBatch);
            }
        }
    }

    @Override
    public void dispose() {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).getTexture().dispose();
        }
        sprites.clear();

        for (int i = 0; i < clouds.length; i++) {
            clouds[i].dispose();
            clouds[i] = null;
        }
        clouds = null;
    }

    private static Texture scaleTexture(Texture texture, int dstWidth, int dstHeight) {
        texture.getTextureData().prepare();
        Pixmap pixmap = new Pixmap(dstWidth, dstHeight, Pixmap.Format.RGBA4444);
        pixmap.drawPixmap(texture.getTextureData().consumePixmap(), 0, 0, texture.getWidth(), texture.getHeight(),
                0, 0, dstWidth, dstHeight);
        texture.dispose();
        return new Texture(pixmap);
    }
}
