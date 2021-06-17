package ru.reactiveturtle.reflectthebullet.game.objects.reflectors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.Physical;
import ru.reactiveturtle.reflectthebullet.game.objects.StaticObject;


public class BoxReflector extends Sprite implements StaticObject, Physical {
    private Body mBody;
    private float mCellWidth, mCellHeight;
    private final float oneMeter;

    public BoxReflector(GameContext gameContext, Pixmap pixmap, float cellWidth, float cellHeight) {
        super(new Texture(pixmap));
        oneMeter = gameContext.getDisplayMetrics().getOneMeterPixels();
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
        setOrigin(0, 0);
    }

    @Override
    public void createBody(World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(getX() / oneMeter, getY() / oneMeter);
        mBody = world.createBody(def);
        mBody.setUserData("reflector");

        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[]{mCellWidth / oneMeter, mCellHeight * (getHeight() / mCellHeight - 1) / oneMeter,
                mCellWidth / oneMeter, mCellHeight / oneMeter,
                mCellWidth * (getWidth() / mCellWidth - 1) / oneMeter, mCellHeight / oneMeter,
                mCellWidth * (getWidth() / mCellWidth - 1) / oneMeter, mCellHeight * (getHeight() / mCellHeight - 1) / oneMeter}, 0, 8);
        Fixture fixture = mBody.createFixture(chainShape, 0f);
        fixture.setRestitution(0.9f);
        fixture.setFriction(0.1f);
        chainShape.dispose();
    }

    @Override
    public Body getBody() {
        return mBody;
    }

    @Override
    public void syncSprite(float oneMeter) {
        Helper.syncSpriteWithBody(this, mBody, oneMeter);
    }

    @Override
    public void disposeObject() {
        for (int i = 0; i < mBody.getFixtureList().size; i++) {
            mBody.destroyFixture(mBody.getFixtureList().get(i));
            i--;
        }
        getTexture().dispose();
    }

    private float[] getUsualVertices(float oneMeter) {
        float[] usualVertices = new float[8];
        usualVertices[3] = getHeight() / oneMeter;
        usualVertices[4] = getWidth() / oneMeter;
        usualVertices[5] = getHeight() / oneMeter;
        usualVertices[6] = getWidth() / oneMeter;
        return usualVertices;
    }

    private static Pixmap getPixmap() {
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        pixmap.setColor(Color.BROWN);
        pixmap.fillRectangle(0, 0, 100, 100);
        return pixmap;
    }

    @Override
    public Physical getPhysical() {
        return this;
    }

    @Override
    public Sprite getSprite() {
        return this;
    }
}
