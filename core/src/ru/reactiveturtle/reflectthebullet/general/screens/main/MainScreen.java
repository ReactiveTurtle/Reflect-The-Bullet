package ru.reactiveturtle.reflectthebullet.general.screens.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import ru.reactiveturtle.reflectthebullet.AppInterface;
import ru.reactiveturtle.reflectthebullet.general.helpers.PixmapHelper;
import ru.reactiveturtle.reflectthebullet.general.screens.Screen;
import ru.reactiveturtle.reflectthebullet.general.screens.main.level.LevelsMenu;
import ru.reactiveturtle.reflectthebullet.general.screens.main.level.LevelsTypeMenu;

import static ru.reactiveturtle.reflectthebullet.general.GameData.GAME_FONT;
import static ru.reactiveturtle.reflectthebullet.general.GameData.IS_MUSIC_PLAYING;
import static ru.reactiveturtle.reflectthebullet.general.GameData.IS_SOUND_FX_PLAYING;
import static ru.reactiveturtle.reflectthebullet.general.GameData.MUSIC_VOLUME;
import static ru.reactiveturtle.reflectthebullet.general.GameData.SOUND_FX_VOLUME;
import static ru.reactiveturtle.reflectthebullet.general.GameData.width;

public class MainScreen implements Screen {
    private SpriteBatch mSpriteBatch;
    private Sprite mMenuBack;
    private Texture mMenuBackTexture;
    private Label mMenuTable;

    private MainMenu mMenu;
    private LevelsTypeMenu mLevelsTypeMenu;
    private LevelsMenu mLevelsMenu;
    private SettingsMenu mSettingsMenu;

    private Music mMenuBackMusic;

    private boolean mIsShowing = false;

    private int selectedStage = 0;

    private ActionListener mActionListener;
    private AppInterface mAppInterface;

    public MainScreen(AppInterface appInterface, final ActionListener actionListener) {
        mAppInterface = appInterface;

        mSpriteBatch = new SpriteBatch();
        mMenuBackTexture = getMenuBack();
        mMenuBack = new Sprite(mMenuBackTexture);
        mMenuBack.setSize(width(), Gdx.graphics.getHeight());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal(GAME_FONT));
        labelStyle.fontColor = Color.WHITE;
        labelStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("table.png"))));
        labelStyle.font.getData().setScale(width() / 1400f);
        mMenuTable = new Label("\n\nГлавное меню", labelStyle);
        mMenuTable.setAlignment(Align.center);
        mMenuTable.setSize(width() / 2.25f, width() / 3f);
        mMenuTable.setPosition(width() / 2f - width() / 4.5f, Gdx.graphics.getHeight() - mMenuTable.getHeight());

        mMenu = new MainMenu();
        mActionListener = actionListener;
        mMenu.setActionListener(new MainMenu.ActionListener() {
            @Override
            public void onAction(String id) {
                switch (id) {
                    case "continue":
                        mActionListener.onPlay();
                        break;
                    case "select_level":
                        selectedStage = 1;
                        mMenuTable.setText("\n\nУровни");
                        Gdx.input.setInputProcessor(mLevelsTypeMenu);
                        break;
                    case "settings":
                        selectedStage = 3;
                        mSettingsMenu.updateSettings(IS_MUSIC_PLAYING, MUSIC_VOLUME, IS_SOUND_FX_PLAYING, SOUND_FX_VOLUME);
                        mMenuTable.setText("\n\nНастройки");
                        Gdx.input.setInputProcessor(mSettingsMenu);
                        break;
                    case "exit_from_game":
                        mActionListener.onExit();
                        break;
                }
            }
        });
        initBackMusic();
        mLevelsTypeMenu = new LevelsTypeMenu();
        mLevelsTypeMenu.setActionListener(new LevelsTypeMenu.ActionListener() {
            @Override
            public void onAction(String id) {
                System.out.println(id);
                switch (id) {
                    case "escape":
                        selectedStage = 0;
                        mMenuTable.setText("\n\nГлавное меню");
                        Gdx.input.setInputProcessor(mMenu);
                        break;
                    default:
                        selectedStage = 2;
                        switch (id) {
                            case "desert_open":
                                mMenuTable.setText("\n\nПустыня");
                                mMenuBack.setTexture(new Texture(PixmapHelper.castShadow(
                                        PixmapHelper.getLevelBack("desert_back.png"), 0.5f)));
                                break;
                            case "country_open":
                                mMenuTable.setText("\n\nРавнина");
                                mMenuBack.setTexture(new Texture(PixmapHelper.castShadow(
                                        PixmapHelper.getLevelBack("country_back.png"), 0.5f)));
                                break;
                        }
                        mLevelsMenu.showLevels(id, mAppInterface.getLevelsInfo(id));
                        Gdx.input.setInputProcessor(mLevelsMenu);
                        break;
                }
            }
        });
        mLevelsMenu = new LevelsMenu();
        mLevelsMenu.setActionListener(new LevelsMenu.ActionListener() {
            @Override
            public void onAction(String id) {
                System.out.println(id);
                switch (id) {
                    case "escape":
                        selectedStage = 1;
                        mMenuTable.setText("\n\nУровни");
                        mMenuBack.setTexture(mMenuBackTexture);
                        Gdx.input.setInputProcessor(mLevelsTypeMenu);
                        break;
                    default:
                        String[] levelParams = id.split("&");
                        selectedStage = 0;
                        mMenuTable.setText("\n\nГлавное меню");
                        mMenuBack.setTexture(mMenuBackTexture);
                        Gdx.input.setInputProcessor(mMenu);
                        if (actionListener != null) {
                            actionListener.onLoadLevel(levelParams[0], Integer.parseInt(levelParams[1]));
                        }
                }
            }
        });

        mSettingsMenu = new SettingsMenu(IS_MUSIC_PLAYING, MUSIC_VOLUME, IS_SOUND_FX_PLAYING, SOUND_FX_VOLUME);
        mSettingsMenu.setActionListener(new SettingsMenu.ActionListener() {
            @Override
            public void onStateChanged(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
                IS_MUSIC_PLAYING = isMusicPlaying;
                MUSIC_VOLUME = musicVolume;
                mMenuBackMusic.setVolume(MUSIC_VOLUME);
                if (!isMusicPlaying && mMenuBackMusic.isPlaying()) {
                    mMenuBackMusic.stop();
                } else if (isMusicPlaying && !mMenuBackMusic.isPlaying()) {
                    mMenuBackMusic.play();
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
                selectedStage = 0;
                mMenuTable.setText("\n\nГлавное меню");
                mMenuBack.setTexture(mMenuBackTexture);
                Gdx.input.setInputProcessor(mMenu);
            }
        });
    }

    private void initBackMusic() {
        mMenuBackMusic = Gdx.audio.newMusic(Gdx.files.internal("music/game_theme.mp3"));
        mMenuBackMusic.setLooping(true);
        mMenuBackMusic.setVolume(MUSIC_VOLUME);
    }

    public void show() {
        mIsShowing = true;
        Gdx.input.setInputProcessor(mMenu);
        start();
    }

    public void hide() {
        mIsShowing = false;
        mMenuBackMusic.stop();
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public void start() {
        if (IS_MUSIC_PLAYING) {
            mMenuBackMusic.play();
        }
    }

    public void pause() {
        if (IS_MUSIC_PLAYING) {
            mMenuBackMusic.pause();
        }
    }

    @Override
    public void draw() {
        if (mIsShowing) {
            mSpriteBatch.begin();
            mMenuBack.draw(mSpriteBatch);
            mSpriteBatch.end();
            switch (selectedStage) {
                case 0:
                    mMenu.draw();
                    break;
                case 1:
                    mLevelsTypeMenu.act(Gdx.graphics.getDeltaTime());
                    mLevelsTypeMenu.draw();
                    break;
                case 2:
                    mLevelsMenu.act(Gdx.graphics.getDeltaTime());
                    mLevelsMenu.draw();
                    break;
                case 3:
                    mSettingsMenu.act(Gdx.graphics.getDeltaTime());
                    mSettingsMenu.draw();
                    break;
            }
            mSpriteBatch.begin();
            mMenuTable.draw(mSpriteBatch, 1f);
            mSpriteBatch.end();
        }
    }

    @Override
    public void dispose() {
        mMenuBackMusic.stop();
        mMenuBackMusic.dispose();
        mMenu.dispose();
        mMenuBackTexture.dispose();
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onPlay();

        void onLoadLevel(String levelType, int levelIndex);

        void onExit();
    }

    private Texture getMenuBack() {
        Texture texture = new Texture(Gdx.files.internal("sky.png"));
        texture.getTextureData().prepare();
        Pixmap src = texture.getTextureData().consumePixmap();
        Pixmap pixmap = new Pixmap(width(), Gdx.graphics.getHeight(), Pixmap.Format.RGB888);
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(), 0, 0, pixmap.getWidth(), pixmap.getHeight());
        texture.dispose();
        src.dispose();

        texture = new Texture(Gdx.files.internal("sun.png"));
        texture.getTextureData().prepare();
        src = texture.getTextureData().consumePixmap();
        pixmap.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(),
                -width() / 2, -width() / 2, width(), width() * src.getHeight() / src.getWidth());
        texture.dispose();
        src.dispose();
        return new Texture(pixmap);
    }
}
