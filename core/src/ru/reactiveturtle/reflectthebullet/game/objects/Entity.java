package ru.reactiveturtle.reflectthebullet.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
    private Sprite mSprite;
    private Body body;

    public Entity(World world, String textureName) {
        super();
        mSprite = new Sprite(new Texture(Gdx.files.internal(textureName)));
        mSprite.setOriginCenter();
        body = createBody(world);
    }

    public Sprite getSprite() {
        return mSprite;
    }

    public Body getBody() {
        return body;
    }

    protected abstract Body createBody(World world);
}
