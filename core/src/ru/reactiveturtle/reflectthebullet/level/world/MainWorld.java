package ru.reactiveturtle.reflectthebullet.level.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.reactiveturtle.reflectthebullet.game.GameScreen;
import ru.reactiveturtle.reflectthebullet.base.Renderable;
import ru.reactiveturtle.reflectthebullet.game.objects.Bullet;
import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.Revolver;
import ru.reactiveturtle.reflectthebullet.game.objects.StaticObject;

public class MainWorld implements GameWorld, Keeper {
    private GameScreen mGameScreen;
    private World mWorld;
    private String mLoadedLocation = "unknown";

    private List<Sprite> mBackList = new ArrayList<Sprite>();
    private List<Renderable> mRenderables = new ArrayList<>();

    private Revolver mRevolver;
    private Sprite mAim;
    private Bullet mBullet;
    private ParticleEffect mSparks;
    private int mScore = 0;
    private int mBestScore = 0;
    private List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

    public MainWorld(GameScreen gameScreen) {
        mGameScreen = gameScreen;
        mWorld = new World(new Vector2(0, -0.5f), false);

        mRevolver = new Revolver(new Texture(Gdx.files.internal("revolver.png")));
        mRevolver.setOnShotListener(new Revolver.OnShotListener() {
            @Override
            public void onShot(Bullet bullet) {
                playSound("shot");
                mScore = 0;
                mBullet = bullet;
                bullet.setId(genBulletId());
            }

            @Override
            public void onEmptyClip() {
                mGameScreen.showScore("Нет патронов");
            }
        });
        initAim();
        mSparks = new ParticleEffect();
        mSparks.load(Gdx.files.internal("rikoshet_sparks.p"), Gdx.files.internal(""));
        ParticleEmitter emitter = mSparks.getEmitters().get(0);
        emitter.scaleSize(width() / 480f);
    }

    private void initAim() {
        mAim = new Sprite(new Texture(Gdx.files.internal("aim.png")));
        mAim.setPosition(0, 0);
        mAim.setSize(width() / 8f, width() / 8f);
        mAim.setOriginCenter();
    }

    @Override
    public void loadLevel(String levelName, int levelIndex) {
        try {
            mLoadedLocation = levelName;
            Level level = (Level) Class.forName("ru.reactiveturtle.reflectthebullet.level.world.Levels$Level_" + levelName + levelIndex).newInstance();
            level.loadTo(this, mWorld);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLoadedLocation() {
        return mLoadedLocation;
    }

    @Override
    public void drawBackground(SpriteBatch spriteBatch) {
        for (int i = 0; i < mBackList.size(); i++) {
            mBackList.get(i).draw(spriteBatch);
        }
        for (int i = 0; i < mRenderables.size(); i++) {
            mRenderables.get(i).draw(spriteBatch, Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void drawObjects(SpriteBatch spriteBatch) {
        if (mBullet != null) {
            mBullet.syncSprite(ONE_METER);
            if (Helper.isVisibleInScreen(mBullet, width(), height()) &&
                    mBullet.getBody().isAwake() &&
                    mBullet.getBody().getUserData() != null &&
                    (Math.abs(mBullet.getBody().getLinearVelocity().x) > 0.01f)) {
                mBullet.draw(spriteBatch);
            } else {
                mRevolver.resetBullet();
                mBullet = null;
            }
        }

        mRevolver.draw(spriteBatch);

        for (int i = 0; i < mStaticObjects.size(); i++) {
            StaticObject staticObject = mStaticObjects.get(i);
            staticObject.getPhysical().syncSprite(ONE_METER);
            staticObject.getSprite().draw(spriteBatch);
        }
        mSparks.draw(spriteBatch);
        mSparks.update(Gdx.graphics.getDeltaTime());
        mAim.draw(spriteBatch);
    }

    @Override
    public void syncPhysics() {
        mWorld.step(Gdx.graphics.getDeltaTime(), 8, 8);
    }

    @Override
    public World getWorld() {
        return mWorld;
    }

    @Override
    public Sprite getAim() {
        return mAim;
    }

    @Override
    public Revolver getRevolver() {
        return mRevolver;
    }

    @Override
    public Bullet getBullet() {
        return mBullet;
    }

    @Override
    public int getBestScore() {
        return mBestScore;
    }

    @Override
    public void dispose() {
        mScore = 0;
        mBestScore = 0;
        mWorld.setContactListener(null);
        for (int i = 0; i < mBackList.size(); i++) {
            mBackList.get(i).getTexture().dispose();
        }
        mBackList.clear();
        for (int i = 0; i < mRenderables.size(); i++) {
            mRenderables.get(i).dispose();
        }
        mRenderables.clear();
        for (int i = 0; i < mStaticObjects.size(); i++) {
            mStaticObjects.get(i).getPhysical().disposeObject();
        }
        mStaticObjects.clear();
        if (mBullet != null) {
            mBullet = null;
        }
        mRevolver.reset(mWorld);
        mAim.setPosition(width() / 2f, mRevolver.getVertices()[11] - mAim.getHeight() / 2f - mRevolver.getHeight() * 24f / 283f);
    }

    @Override
    public void putBackground(List<Sprite> backList) {
        mBackList.addAll(backList);
    }

    @Override
    public void addVisualManager(Renderable renderable) {
        mRenderables.add(renderable);
    }

    @Override
    public void setScoreTableParams(Color textColor, int y) {
        mGameScreen.setScoreTableParams(textColor, y);
    }

    @Override
    public void putStaticObjects(List<StaticObject> staticObjects) {
        mStaticObjects.addAll(staticObjects);
    }

    @Override
    public void putContactListener(ContactListener contactListener) {
        mWorld.setContactListener(contactListener);
    }

    @Override
    public void resetBullet(String id) {
        String s = mScore + ". ";
        playSound("bullet_to_target");
        if (mBestScore < mScore) {
            mBestScore = mScore;
            mGameScreen.updateBestScore(mBestScore);
        }
        if (mScore < 50) {
            mGameScreen.showScore(s + "Loser");
        } else if (mScore < 70) {
            mGameScreen.showScore(s + "Not bad");
        } else if (mScore < 90) {
            mGameScreen.showScore(s + "You did it!");
        } else if (mScore < 110) {
            mGameScreen.showScore(s + "It was cool!");
        } else {
            mGameScreen.showScore(s + "How did you do that?!");
        }
        if (mBullet != null) {
            mBullet.getBody().setUserData(null);
        }
    }

    @Override
    public void putScore(int score) {
        this.mScore += score;
    }

    @Override
    public void playSound(String name) {
        if (!IS_SOUND_FX_PLAYING) {
            return;
        }
        switch (name) {
            case "shot":
                shotSound.play(SOUND_FX_VOLUME);
                break;
            case "bullet_to_target":
                bulletToTargetSound.play(SOUND_FX_VOLUME);
                break;
            case "rikoshet1":
                rikoshetSound1.play(SOUND_FX_VOLUME);
                break;
            case "rikoshet2":
                rikoshetSound2.play(SOUND_FX_VOLUME);
                break;
            case "rikoshet3":
                rikoshetSound3.play(SOUND_FX_VOLUME);
                break;
            case "rikoshet4":
                rikoshetSound4.play(SOUND_FX_VOLUME);
                break;
            case "hit":
                hitSound.play(SOUND_FX_VOLUME);
                break;
        }
    }

    @Override
    public void showRikoshetSparks(Vector2 collisionPoint) {
        mSparks.getEmitters().get(0).setPosition(collisionPoint.x * ONE_METER, collisionPoint.y * ONE_METER);
        mSparks.start();
    }

    public int genBulletId() {
        int randomId = new Random().nextInt(2147483647);
        /*boolean isUnique = false;
        while (!isUnique && mBullets.size() > 0) {
            for (int i = 0; i < mBullets.size(); i++) {
                Bullet bullet = mBullets.get(i);
                if (bullet.getBody().getUserData() != null && bullet.getBody().getUserData().equals("bullet" + randomId)) {
                    isUnique = false;
                    randomId = new Random().nextInt(2147483647);
                    break;
                } else if (!isUnique) {
                    isUnique = true;
                }
            }
        }*/
        return randomId;
    }
}
