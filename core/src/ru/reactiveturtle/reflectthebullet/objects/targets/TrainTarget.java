package ru.reactiveturtle.reflectthebullet.objects.targets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.objects.Physical;
import ru.reactiveturtle.reflectthebullet.objects.StaticObject;

public class TrainTarget extends Sprite implements StaticObject, Physical {
    private Body mBody;

    public TrainTarget(float width, float height, Texture texture) {
        super(texture);
        setSize(width, height);
        setOrigin(0, 0);
    }

    @Override
    public void createBody(World world) {
        float oneMeter = ONE_METER;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.angle = (float) Math.toRadians(getRotation());
        def.position.set(getX() / oneMeter, getY() / oneMeter);
        mBody = world.createBody(def);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(getUsualVertices(oneMeter));
        Fixture fixture = mBody.createFixture(polygonShape, 0f);
        fixture.setRestitution(0f);
        mBody.setUserData("target");
        polygonShape.dispose();
    }

    @Override
    public void syncSprite(float oneMeter) {
        Helper.syncSpriteWithBody(this, mBody, oneMeter);
    }

    @Override
    public Body getBody() {
        return mBody;
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
        usualVertices[1] = getHeight() / 2f / oneMeter;
        usualVertices[2] = getWidth() / 2f / oneMeter;
        usualVertices[4] = getWidth() / oneMeter;
        usualVertices[5] = getHeight() / 2f / oneMeter;
        usualVertices[6] = getWidth() / 2f / oneMeter;
        usualVertices[7] = getHeight() / oneMeter;
        return usualVertices;
    }

    private static Pixmap getPixmap() {
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        pixmap.setColor(Color.PURPLE);
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
