package ru.reactiveturtle.reflectthebullet.level.world;

import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.game.objects.reflectors.Reflector;
import ru.reactiveturtle.reflectthebullet.level.LevelData;

public class Level extends Stage {
    private final LevelData mLevelData;
    private final List<Reflector> mReflectors;

    public Level(GameContext gameContext,
                 LevelData levelData,
                 List<Reflector> reflectors) {
        super(gameContext);
        this.mLevelData = levelData;
        this.mReflectors = reflectors;
    }

    @Override
    public void draw() {
        super.draw();

        DisplayMetrics displayMetrics = getGameContext().getDisplayMetrics();

        float oneMeter = displayMetrics.getOneMeterPixels();
        for (int i = 0; i < mReflectors.size(); i++) {
            Reflector reflector = mReflectors.get(i);
            reflector.syncSprite(oneMeter);
            reflector.draw(getSpriteBatch());
        }
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
