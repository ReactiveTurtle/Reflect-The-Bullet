package ru.reactiveturtle.reflectthebullet.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.base.DisplayMetrics;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.level.LastLevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelRequirements;
import ru.reactiveturtle.reflectthebullet.level.StarExtensions;
import ru.reactiveturtle.reflectthebullet.main.settings.SettingsMenu;
import ru.reactiveturtle.reflectthebullet.level.world.GameWorld;
import ru.reactiveturtle.reflectthebullet.level.world.MainWorld;
import ru.reactiveturtle.reflectthebullet.main.settings.Settings;

public class GameScreen extends Stage {
    private Image mPauseShadow;
    private PauseMenu mPauseMenu;
    private SettingsMenu mSettingsMenu;
    private EndLevelMenu mEndLevelMenu;
    private int mPauseSelectedStage = 0;

    private GameStage mGameStage;
    private ScoreStage mScoreStage;

    private SpriteBatch mSpriteBatch;
    private GameWorld mMainWorld;

    private Music mLevelMusic;

    private boolean isScreenDown = false;
    private boolean isShootDown = false;

    private boolean isShowing = false;
    private boolean isPause = false;

    private Box2DDebugRenderer renderer;

    private ActionListener mActionListener;

    public GameScreen(final GameContext gameContext) {
        super(gameContext);

        DisplayMetrics displayMetrics = gameContext.getDisplayMetrics();

        mPauseShadow = new Image();
        mPauseShadow.setSize(displayMetrics.widthPixels(), Gdx.graphics.getHeight());
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        pixmap.setColor(0, 0, 0, 0.8f);
        pixmap.fillRectangle(0, 0, 100, 100);
        mPauseShadow.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));

        mPauseMenu = new PauseMenu(gameContext);
        mPauseMenu.setActionListener(new PauseMenu.ActionListener() {
            @Override
            public void onAction(PauseMenu.Action action) {
                switch (action) {
                    case CONTINUE:
                    case ESCAPE:
                        if (mActionListener != null) {
                            mActionListener.onContinue();
                        }
                        break;
                    case SETTINGS:
                        mPauseSelectedStage = 1;
                        mSettingsMenu.updateSettings(gameContext.getSettings());
                        Gdx.input.setInputProcessor(mSettingsMenu);
                        break;
                    case EXIT:
                        if (mActionListener != null) {
                            mActionListener.onExit();
                        }
                        break;
                }
            }
        });
        mSettingsMenu = new SettingsMenu(gameContext);
        mSettingsMenu.setActionListener(new SettingsMenu.ActionListener() {
            @Override
            public void onStateChanged(Settings settings) {
                mLevelMusic.setVolume(settings.getMusicVolume());
                if (!settings.isMusicPlaying() && mLevelMusic.isPlaying()) {
                    mLevelMusic.stop();
                } else if (settings.isMusicPlaying() && !mLevelMusic.isPlaying()) {
                    mLevelMusic.play();
                }
            }

            @Override
            public void applySettings(Settings settings) {
                getGameContext().updateSettings(settings);
            }

            @Override
            public void onBackPressed() {
                mPauseSelectedStage = 0;
                Gdx.input.setInputProcessor(mPauseMenu);
            }
        });

        mGameStage = new GameStage(gameContext);
        mScoreStage = new ScoreStage(gameContext);
        mGameStage.setActionListener(new GameStage.ActionListener() {
            @Override
            public void onClick(String id) {
                if (id.equals("shoot")) {
                    Sprite aim = mMainWorld.getAim();
                    mMainWorld.getRevolver().shot(new Vector2(aim.getX() + aim.getWidth() / 2f, aim.getY() + aim.getHeight() / 2f));
                    updateBulletsCount();
                } else if (id.equals("escape")) {
                    pause();
                    Gdx.input.setInputProcessor(mPauseMenu);
                    mLevelMusic.setVolume(gameContext.getSettings().getMusicVolume() * 0.3f);
                }
            }

            @Override
            public void onUp(String id) {
                if (id.equals("shoot")) {
                    isShootDown = false;
                }
            }

            @Override
            public void onDown(String id) {
                if (id.equals("shoot")) {
                    isShootDown = true;
                }
            }
        });
        mGameStage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isShootDown) {
                    isScreenDown = true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (isScreenDown) {
                    Sprite aim = mMainWorld.getAim();
                    aim.setPosition(x - aim.getWidth() / 2f, y + aim.getHeight() / 2f);
                    mMainWorld.getRevolver().setRotation(0);
                    Vector2 vector2 = new Vector2().set(event.getStageX() - mMainWorld.getRevolver().getX() - mMainWorld.getRevolver().getOriginX(),
                            event.getStageY() - mMainWorld.getRevolver().getY() - mMainWorld.getRevolver().getOriginY() + 24f / 283 * mMainWorld.getRevolver().getHeight());
                    float cos = vector2.x / vector2.len();
                    float degrees = (float) Math.toDegrees(Math.acos(cos));
                    if (vector2.y < 0) {
                        degrees = 360 - degrees;
                    }

                    mMainWorld.getRevolver().setRotation(degrees);
                    Vector2 aimCenterPosition = new Vector2(aim.getX() + aim.getOriginX(), aim.getY() + aim.getOriginY());
                    vector2.set(aimCenterPosition.x - (mMainWorld.getRevolver().getVertices()[10] + mMainWorld.getRevolver().getVertices()[5]) / 2f,
                            aimCenterPosition.y - (mMainWorld.getRevolver().getVertices()[11] + mMainWorld.getRevolver().getVertices()[6]) / 2);
                    cos = vector2.x / vector2.len();
                    degrees = (float) Math.toDegrees(Math.acos(cos));
                    if (vector2.y < 0) {
                        degrees = 360 - degrees;
                    }
                    mMainWorld.getRevolver().setRotation(degrees);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                isScreenDown = false;
            }
        });
        mEndLevelMenu = new EndLevelMenu(gameContext);
        mEndLevelMenu.setActionListener(new EndLevelMenu.ActionListener() {
            @Override
            public void onAction(EndLevelMenu.Action action) {
                switch (action) {
                    case MENU:
                        if (mPauseMenu.getActionListener() != null) {
                            mPauseMenu.getActionListener().onAction(PauseMenu.Action.ESCAPE);
                        }
                        break;
                    case REPEAT:
                        mMainWorld.dispose();
                        loadCurrentLevel();
                        mScoreStage.updateBestScore(0);

                        updateBulletsCount();
                        start();
                        break;
                    case NEXT:
                        LevelRepositoryImpl levelRepositoryImpl = gameContext.getLevelRepository();
                        LastLevelData lastLevelData = levelRepositoryImpl.getLastLevelData();
                        String nextLevelDirectory = levelRepositoryImpl.getNextLevelDirectory(lastLevelData.getLastLevelDirectory());
                        levelRepositoryImpl.setLastLevelData(new LastLevelData(nextLevelDirectory));
                        loadCurrentLevel();
                        start();
                        break;
                }
            }
        });
        mSpriteBatch = new SpriteBatch();
        mMainWorld = new MainWorld(this);
        renderer = new Box2DDebugRenderer(true, false, false, false, false, false);
    }

    public void show() {
        isShowing = true;
        start();
        startMusic();
    }

    public void startMusic() {
        if (getGameContext().getSettings().isMusicPlaying()) {
            mLevelMusic.play();
        }
    }

    public void hide() {
        isShowing = false;
        mLevelMusic.stop();
        mLevelMusic.dispose();
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void start() {
        isPause = false;
        Gdx.input.setInputProcessor(mGameStage);
        mLevelMusic.setVolume(getGameContext().getSettings().getMusicVolume());
    }

    public void pause() {
        isPause = true;
        Gdx.input.setInputProcessor(mPauseMenu);
        mLevelMusic.setVolume(getGameContext().getSettings().getMusicVolume() * 0.3f);
    }

    public boolean isPause() {
        return isPause;
    }

    public void loadCurrentLevel() {
        mMainWorld.dispose();
        LevelRepositoryImpl levelRepository = getGameContext().getLevelRepository();
        LastLevelData lastLevelData = getGameContext().getLevelRepository().getLastLevelData();
        LevelData levelData = levelRepository.getLevelData(lastLevelData.getLastLevelDirectory());
        if (!mMainWorld.getLoadedLocation().equals(lastLevelData.getLastLevelDirectory())) {
            if (mLevelMusic != null) {
                mLevelMusic.dispose();
            }
            mLevelMusic = Helper.loadLevelMusic(levelData.getLevelType());
            startMusic();
        }
        mMainWorld.loadLevel(lastLevelData.getLastLevelDirectory());
        mScoreStage.updateBestScore(0);

        updateBulletsCount();
    }

    public void updateBulletsCount() {
        mGameStage.showBulletsCount(mMainWorld.getRevolver().getBulletsCount());
    }

    @Override
    public void draw() {
        if (isShowing) {
            mSpriteBatch.begin();
            mMainWorld.drawBackground(mSpriteBatch);
            mSpriteBatch.end();
            mMainWorld.drawObjects(mSpriteBatch);

            mScoreStage.draw();

            //renderer.render(mMainWorld.getWorld(), CAMERA.combined);
            if (isPause) {
                mSpriteBatch.begin();
                mPauseShadow.draw(mSpriteBatch, 1f);
                mSpriteBatch.end();
                if (mMainWorld.getBullet() == null && mMainWorld.getRevolver().getBulletsCount() == 0) {
                    mEndLevelMenu.draw();
                } else {
                    switch (mPauseSelectedStage) {
                        case 0:
                            mPauseMenu.draw();
                            break;
                        case 1:
                            mSettingsMenu.draw();
                            break;
                    }
                }
            } else {
                if (mMainWorld.getBullet() == null && mMainWorld.getRevolver().getBulletsCount() == 0) {
                    mEndLevelMenu.draw();
                    if (Gdx.input.getInputProcessor().hashCode() != mEndLevelMenu.hashCode()) {
                        LevelRepositoryImpl levelRepository = getGameContext().getLevelRepository();
                        LastLevelData lastLevelData = levelRepository.getLastLevelData();
                        LevelData levelData = levelRepository.getLevelData(lastLevelData.getLastLevelDirectory());
                        LevelRequirements levelRequirements = levelRepository.getLevelRequirements(lastLevelData.getLastLevelDirectory());
                        String nextLevelDirectory = levelRepository.getNextLevelDirectory(
                                lastLevelData.getLastLevelDirectory());

                        if ((levelData.isFinished() || mMainWorld.getBestScore() >= levelRequirements.getFirstStarScore()) && nextLevelDirectory != null) {
                            mEndLevelMenu.enableNextLevelButton();
                        } else {
                            mEndLevelMenu.disableNextLevelButton();
                        }
                        levelRepository.setLevelData(lastLevelData.getLastLevelDirectory(),
                                Math.max(levelData.getBestScore(), mMainWorld.getBestScore()),
                                levelData.isFinished() || mMainWorld.getBestScore() >= levelRepository.getLevelRequirements(lastLevelData.getLastLevelDirectory()).getFirstStarScore());
                        mEndLevelMenu.showScore(levelRepository.getLevelData(lastLevelData.getLastLevelDirectory()).getBestScore(),
                                mMainWorld.getBestScore(),
                                StarExtensions.getStars(mMainWorld.getBestScore(), levelRequirements));
                        pause();
                        Gdx.input.setInputProcessor(mEndLevelMenu);
                    }
                } else {
                    mMainWorld.syncPhysics();
                    mGameStage.draw();
                }
            }
        }
    }

    @Override
    public void dispose() {
        mPauseMenu.dispose();
        mGameStage.dispose();
        mSpriteBatch.dispose();
        mMainWorld.dispose();
    }

    public void setScoreTableParams(Color textColor, int y) {
        mScoreStage.setScoreTableParams(textColor, y);
    }

    public void updateBestScore(int bestScore) {
        mScoreStage.updateBestScore(bestScore);
    }

    public void showScore(String text) {
        mScoreStage.showScore(text);
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onContinue();

        void onExit();
    }
}
