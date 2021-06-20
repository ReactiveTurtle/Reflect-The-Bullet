package ru.reactiveturtle.reflectthebullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.game.objects.Bullet;
import ru.reactiveturtle.reflectthebullet.game.objects.Entity;

public class Revolver extends Entity {
    private Sound mEmptyClipSnapSound = Gdx.audio.newSound(Gdx.files.internal("empty_clip_snap.ogg"));
    private Bullet mBullet;
    public static float BULLET_SPEED = 10f;
    private int mBulletsCount = 6;

    private OnShotListener mOnShotListener;

    public Revolver(GameContext gameContext, World world) {
        super(gameContext, world, gameContext.getTextureLoader().getRevolverTexture());
    }

    @Override
    protected Body createBody(World world) {
        return null;
    }

    public int getBulletsCount() {
        return mBulletsCount;
    }

    public void shot(Vector2 shotPoint) {
        if (mBulletsCount > 0) {
            resetBullet();
            mBullet.getBody().setType(BodyDef.BodyType.DynamicBody);
            Vector2 vector2 = new Vector2(shotPoint.x - (mBullet.getX() + mBullet.getWidth() / 2f),
                    shotPoint.y - (mBullet.getY() + mBullet.getHeight() / 2f));
            float cos = vector2.x / vector2.len();
            float degrees = (float) Math.toDegrees(Math.acos(cos));
            if (vector2.y < 0) {
                degrees = 360 - degrees;
            }
            Vector2 rotationVector = new Vector2(1f, 0f);
            rotationVector.rotate(degrees);
            mBullet.getBody().setLinearVelocity(new Vector2(mBullet.getSpeed() * rotationVector.x, mBullet.getSpeed() * rotationVector.y));

            if (mOnShotListener != null) {
                mOnShotListener.onShot(mBullet);
            }
            mBulletsCount--;
        } else {
            mEmptyClipSnapSound.play();
            if (mOnShotListener != null) {
                mOnShotListener.onEmptyClip();
            }
        }
    }

    public void reset(World world) {
        if (mBullet != null) {
            mBullet.disposeObject();
        }

        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        mBulletsCount = 6;
        setRotation(0);
        setPosition(displayMetrics.widthPixels() / 8f, displayMetrics.widthPixels() / 5f);
        setSize(displayMetrics.widthPixels() / 3f, displayMetrics.widthPixels() / 3f * getHeight() / getWidth());
        setOriginCenter();
        mBullet = new Bullet(getGameContext(), world);
        mBullet.setRotation(getRotation());
        mBullet.setSize(40f / 283 * getHeight(), 40f / 283 * getHeight());
        mBullet.setOriginCenter();
        mBullet.createBody(world);
    }

    public void resetBullet() {
        Vector2 start = new Vector2(getVertices()[15] - getVertices()[10], getVertices()[16] - getVertices()[11]);
        float bias = 32f / 283;
        mBullet.setRotation(getRotation());
        mBullet.setPosition(getVertices()[10] + start.x * bias, getVertices()[11] + start.y * bias);
        mBullet.syncBody();
        mBullet.getBody().setType(BodyDef.BodyType.StaticBody);
    }

    private float[] getUsualVertices() {
        float[] usualVertices = new float[8];
        usualVertices[0] = getX();
        usualVertices[1] = getY();
        usualVertices[2] = getX();
        usualVertices[3] = getY() + getHeight();
        usualVertices[4] = getX() + getWidth();
        usualVertices[5] = getY() + getHeight();
        usualVertices[6] = getX() + getWidth();
        usualVertices[7] = getY();
        return usualVertices;
    }

    public void setOnShotListener(OnShotListener onShotListener) {
        mOnShotListener = onShotListener;
    }

    public interface OnShotListener {
        void onShot(Bullet bullet);

        void onEmptyClip();
    }
}
