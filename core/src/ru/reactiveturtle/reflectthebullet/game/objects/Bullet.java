package ru.reactiveturtle.reflectthebullet.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.Revolver;
import ru.reactiveturtle.reflectthebullet.base.GameContext;

public class Bullet extends Entity {
    private float mSpeed;

    public Bullet(GameContext gameContext, World world) {
        super(gameContext, world, gameContext.getTextureLoader().getBulletTexture());
        mSpeed = Revolver.BULLET_SPEED;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setId(int id) {
        getBody().setUserData("bullet" + id);
    }

    @Override
    public Body createBody(World world) {
        Sprite sprite = getSprite();

        float oneMeter = getGameContext().getDisplayMetrics().getOneMeterPixels();

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.angle = (float) Math.toRadians(getRotation());
        def.position.set(sprite.getX() / oneMeter, sprite.getY() / oneMeter);
        Body body = world.createBody(def);
        body.setBullet(true);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(getWidth() / oneMeter / 2f);
        Fixture fixture = body.createFixture(circleShape, 1f);
        fixture.setRestitution(0.6f);
        circleShape.dispose();
        body.resetMassData();

        Vector2 vector2 = new Vector2(1f, 0f);
        vector2.rotate(getRotation());
        body.setLinearVelocity(new Vector2(mSpeed * vector2.x, mSpeed * vector2.y));
        return body;
    }

    public void syncSprite(float oneMeter) {
        Helper.syncSpriteWithBody(getSprite(), getBody(), oneMeter);
    }

    public void syncBody() {
        float oneMeter = getGameContext().getDisplayMetrics().getOneMeterPixels();
        getBody().setAngularVelocity(0);
        getBody().setTransform(getX() / oneMeter, getY() / oneMeter, (float) Math.toRadians(getRotation()));
    }

    private float[] getUsualVertices(float oneMeter) {
        float[] usualVertices = new float[8];
        for (int i = 0; i < 4; i++) {
            usualVertices[i * 2] = getVertices()[i * 5] / oneMeter;
            usualVertices[i * 2 + 1] = getVertices()[i * 5 + 1] / oneMeter;
        }
        return usualVertices;
    }

    public void disposeObject() {
    }
}
