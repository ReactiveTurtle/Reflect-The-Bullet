package ru.reactiveturtle.reflectthebullet.general.screens.game;

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

import ru.reactiveturtle.reflectthebullet.AppInterface;
import ru.reactiveturtle.reflectthebullet.Helper;
import ru.reactiveturtle.reflectthebullet.general.GameData;
import ru.reactiveturtle.reflectthebullet.general.screens.Screen;
import ru.reactiveturtle.reflectthebullet.general.screens.main.level.LevelInfo;
import ru.reactiveturtle.reflectthebullet.general.screens.main.SettingsMenu;
import ru.reactiveturtle.reflectthebullet.general.screens.world.GameWorld;
import ru.reactiveturtle.reflectthebullet.general.screens.world.MainWorld;

import static ru.reactiveturtle.reflectthebullet.general.GameData.CURRENT_LEVEL;
import static ru.reactiveturtle.reflectthebullet.general.GameData.CURRENT_LOCATION;
import static ru.reactiveturtle.reflectthebullet.general.GameData.IS_MUSIC_PLAYING;
import static ru.reactiveturtle.reflectthebullet.general.GameData.IS_SOUND_FX_PLAYING;
import static ru.reactiveturtle.reflectthebullet.general.GameData.MUSIC_VOLUME;
import static ru.reactiveturtle.reflectthebullet.general.GameData.SOUND_FX_VOLUME;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class GameScreen implements Screen {
    private AppInterface mAppInterface;

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

    public GameScreen(AppInterface appInterface) {
        mAppInterface = appInterface;
        mPauseShadow = new Image();
        mPauseShadow.setSize(width(), Gdx.graphics.getHeight());
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA4444);
        pixmap.setColor(0, 0, 0, 0.8f);
        pixmap.fillRectangle(0, 0, 100, 100);
        mPauseShadow.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));

        mPauseMenu = new PauseMenu();
        mPauseMenu.setActionListener(new PauseMenu.ActionListener() {
            @Override
            public void onAction(String id) {
                switch (id) {
                    case "pause_continue":
                    case "escape":
                        if (mActionListener != null) {
                            mActionListener.onContinue();
                        }
                        break;
                    case "pause_settings":
                        mPauseSelectedStage = 1;
                        mSettingsMenu.updateSettings(IS_MUSIC_PLAYING, MUSIC_VOLUME, IS_SOUND_FX_PLAYING, SOUND_FX_VOLUME);
                        Gdx.input.setInputProcessor(mSettingsMenu);
                        break;
                    case "pause_exit":
                        if (mActionListener != null) {
                            mActionListener.onExit();
                        }
                        break;
                }
            }
        });
        mSettingsMenu = new SettingsMenu(IS_MUSIC_PLAYING, MUSIC_VOLUME, IS_SOUND_FX_PLAYING, SOUND_FX_VOLUME);
        mSettingsMenu.setActionListener(new SettingsMenu.ActionListener() {
            @Override
            public void onStateChanged(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
                IS_MUSIC_PLAYING = isMusicPlaying;
                MUSIC_VOLUME = musicVolume;
                if (isPause) {
                    mLevelMusic.setVolume(MUSIC_VOLUME * 0.3f);
                } else {
                    mLevelMusic.setVolume(MUSIC_VOLUME);
                }
                if (!IS_MUSIC_PLAYING && !isMusicPlaying && mLevelMusic.isPlaying()) {
                    mLevelMusic.stop();
                } else if (IS_MUSIC_PLAYING && isMusicPlaying && !mLevelMusic.isPlaying()) {
                    mLevelMusic.play();
                }
                IS_SOUND_FX_PLAYING = isSoundFxPlaying;
                SOUND_FX_VOLUME = soundFxVolume;
            }

            @Override
            public void applySettings() {
                mAppInterface.setSettings(IS_MUSIC_PLAYING, MUSIC_VOLUME, IS_SOUND_FX_PLAYING, SOUND_FX_VOLUME);
            }

            @Override
            public void onBackPressed() {
                mPauseSelectedStage = 0;
                Gdx.input.setInputProcessor(mPauseMenu);
            }
        });

        mGameStage = new GameStage();
        mScoreStage = new ScoreStage();
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
                    mLevelMusic.setVolume(MUSIC_VOLUME * 0.3f);
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
        mEndLevelMenu = new EndLevelMenu();
        mEndLevelMenu.setActionListener(new EndLevelMenu.ActionListener() {
            @Override
            public void onAction(String id) {
                switch (id) {
                    case "exit":
                        if (mPauseMenu.getActionListener() != null) {
                            mPauseMenu.getActionListener().onAction("pause_exit");
                        }
                        break;
                    case "repeat":
                        mMainWorld.dispose();
                        loadCurrentLevel();
                        mScoreStage.updateBestScore(0);

                        updateBulletsCount();
                        start();
                        break;
                    case "next":
                        LevelInfo levelInfo = mAppInterface.getNextLevel(CURRENT_LOCATION, CURRENT_LEVEL);
                        mAppInterface.setCurrentLevelParams(levelInfo.levelType, levelInfo.levelIndex);
                        CURRENT_LOCATION = levelInfo.levelType;
                        CURRENT_LEVEL = levelInfo.levelIndex;
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
        if (IS_MUSIC_PLAYING) {
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
        mLevelMusic.setVolume(MUSIC_VOLUME);
    }

    public void pause() {
        isPause = true;
        Gdx.input.setInputProcessor(mPauseMenu);
        mLevelMusic.setVolume(MUSIC_VOLUME * 0.3f);
    }

    public boolean isPause() {
        return isPause;
    }

    public void loadCurrentLevel() {
        mMainWorld.dispose();
        if (!mMainWorld.getLoadedLocation().equals(CURRENT_LOCATION)) {
            if (mLevelMusic != null) {
                mLevelMusic.dispose();
            }
            mLevelMusic = Helper.loadLevelMusic(GameData.CURRENT_LOCATION);
            startMusic();
        }
        mMainWorld.loadLevel(CURRENT_LOCATION, CURRENT_LEVEL);
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

            mScoreStage.draw();

            mSpriteBatch.begin();
            mMainWorld.drawObjects(mSpriteBatch);
            mSpriteBatch.end();
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
                        LevelInfo levelInfo = mAppInterface.getLevelInfo(CURRENT_LOCATION, CURRENT_LEVEL);
                        LevelInfo nextLevel = mAppInterface.getNextLevel(CURRENT_LOCATION, CURRENT_LEVEL);
                        if ((levelInfo.isFinished || mMainWorld.getBestScore() >= levelInfo.firstStarScore) && nextLevel != null) {
                            mEndLevelMenu.enableNextLevelButton();
                        } else {
                            mEndLevelMenu.disableNextLevelButton();
                        }
                        mAppInterface.setLevelInfo(CURRENT_LOCATION, CURRENT_LEVEL,
                                levelInfo.bestScore < mMainWorld.getBestScore() ? mMainWorld.getBestScore() : levelInfo.bestScore,
                                levelInfo.isFinished || mMainWorld.getBestScore() >= mAppInterface.getLevelInfo(CURRENT_LOCATION, CURRENT_LEVEL).firstStarScore);
                        mEndLevelMenu.showScore(mAppInterface.getLevelInfo(CURRENT_LOCATION, CURRENT_LEVEL).bestScore,
                                mMainWorld.getBestScore(), mMainWorld.getBestScore() < levelInfo.firstStarScore ? 0 :
                                        mMainWorld.getBestScore() < levelInfo.secondStarScore ? 1 :
                                                mMainWorld.getBestScore() < levelInfo.thirdStarScore ? 2 : 3);
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
