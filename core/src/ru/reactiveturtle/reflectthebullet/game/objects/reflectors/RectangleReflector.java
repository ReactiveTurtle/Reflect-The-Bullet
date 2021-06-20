package ru.reactiveturtle.reflectthebullet.game.objects.reflectors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.Physical;
import ru.reactiveturtle.reflectthebullet.game.objects.StaticObject;

public class RectangleReflector extends Reflector implements StaticObject, Physical {
    public RectangleReflector(GameContext gameContext, World world, Texture texture) {
        super(gameContext, world, texture);
        setOrigin(0, 0);
    }

    @Override
    public Body createBody(World world) {
        float oneMeter = getGameContext().getDisplayMetrics().getOneMeterPixels();
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.angle = (float) Math.toRadians(getRotation());
        def.position.set(getX() / oneMeter, getY() / oneMeter);
        Body body = world.createBody(def);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(getUsualVertices(oneMeter));
        Fixture fixture = body.createFixture(polygonShape, 0f);
        fixture.setRestitution(0.9f);
        fixture.setFriction(0.1f);
        polygonShape.dispose();
        body.setUserData("reflector");
        return body;
    }

    public void syncSprite(float oneMeter) {
        Helper.syncSpriteWithBody(getSprite(), getBody(), oneMeter);
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
}
