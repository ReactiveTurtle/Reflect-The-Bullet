package ru.reactiveturtle.reflectthebullet.game.objects.reflectors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.Entity;

public class TriangleReflector extends Entity {
    public TriangleReflector(GameContext gameContext, World world, float width, float height, Texture texture) {
        super(gameContext, world, texture);
        setSize(width, height);
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

    private float[] getUsualVertices(float oneMeter) {
        float[] usualVertices = new float[6];
        usualVertices[1] = getHeight() / oneMeter;
        usualVertices[4] = getWidth() / oneMeter;
        return usualVertices;
    }

    private static Pixmap getPixmap() {
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        pixmap.setColor(Color.GRAY);
        pixmap.fillRectangle(0, 0, 100, 100);
        return pixmap;
    }
}
