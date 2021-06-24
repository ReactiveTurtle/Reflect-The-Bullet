package ru.reactiveturtle.reflectthebullet.level.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.Reflector;
import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.toolkit.PixmapExtensions;

public class Level extends Stage {
    private final List<Sprite> mBackList;
    private final LevelData mLevelData;
    private final List<Reflector> mReflectors;

    public Level(GameContext gameContext,
                 String relativeLevelDirectory,
                 Texture backTexture,
                 List<Reflector> reflectors) {
        super(gameContext);
        this.mLevelData = gameContext.getLevelRepository().getLevelData(relativeLevelDirectory);
        this.mReflectors = reflectors;
        mBackList = new ArrayList<>();
        Sprite sprite = new Sprite(new Texture(
                PixmapExtensions.getLevelBack(gameContext.getDisplayMetrics(), backTexture)));
        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();
        sprite.setSize(displayMetrics.widthPixels(), displayMetrics.heightPixels());
        mBackList.add(sprite);
    }

    @Override
    public void draw() {
        super.draw();

        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        float oneMeter = displayMetrics.getOneMeterPixels();
        getSpriteBatch().begin();
        for (int i = 0; i < mBackList.size(); i++) {
            Sprite sprite = mBackList.get(i);
            sprite.draw(getSpriteBatch());
        }
        for (int i = 0; i < mReflectors.size(); i++) {
            Reflector reflector = mReflectors.get(i);
            reflector.syncSprite(oneMeter);
            reflector.draw(getSpriteBatch());
        }
        getSpriteBatch().end();
    }

    @Override
    public void dispose() {
        for (int i = 0; i < mReflectors.size(); i++) {
            mReflectors.get(i).dispose();
        }
        mReflectors.clear();
        super.dispose();
    }
}
