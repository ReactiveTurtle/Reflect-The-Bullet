package ru.reactiveturtle.reflectthebullet.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.base.GameContext;

public abstract class Entity {
    private GameContext gameContext;
    private Sprite mSprite;
    private Body mBody;

    public Entity(GameContext gameContext, World world, Texture texture) {
        super();
        this.gameContext = gameContext;
        mSprite = new Sprite(texture);
        mSprite.setOriginCenter();
        mBody = createBody(world);
    }

    public void draw(SpriteBatch spriteBatch) {
        mSprite.draw(spriteBatch);
    }

    public void syncSprite(float oneMeter) {
        Helper.syncSpriteWithBody(getSprite(), getBody(), oneMeter);
    }

    public Sprite getSprite() {
        return mSprite;
    }

    public Body getBody() {
        return mBody;
    }

    protected abstract Body createBody(World world);

    protected GameContext getGameContext() {
        return gameContext;
    }

    public void dispose() {
        for (int i = 0; i < mBody.getFixtureList().size; i++) {
            mBody.destroyFixture(mBody.getFixtureList().get(i));
            i--;
        }
        getSprite().setTexture(null);
    }

    public float getX() {
        return mSprite.getX();
    }

    public float getY() {
        return mSprite.getY();
    }

    public void setOrigin(float x, float y) {
        mSprite.setOrigin(x, y);
    }

    public void setOriginCenter() {
        mSprite.setOriginCenter();
    }

    public float getOriginX() {
        return mSprite.getOriginX();
    }

    public float getOriginY() {
        return mSprite.getOriginY();
    }

    public float getWidth() {
        return mSprite.getWidth();
    }

    public float getHeight() {
        return mSprite.getHeight();
    }

    public float[] getVertices() {
        return mSprite.getVertices();
    }

    public void setPosition(float x, float y) {
        mSprite.setPosition(x, y);
    }

    public void setRotation(float degrees) {
        mSprite.setRotation(degrees);
    }

    public void rotate(float degrees) {
        mSprite.rotate(degrees);
    }

    public float getRotation() {
        return mSprite.getRotation();
    }

    public void setSize(float width, float height) {
        mSprite.setSize(width, height);
    }
}
