package ru.reactiveturtle.reflectthebullet.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.Stage;
import ru.reactiveturtle.reflectthebullet.level.LevelStoreData;
import ru.reactiveturtle.reflectthebullet.level.LevelsMenu;
import ru.reactiveturtle.reflectthebullet.level.LevelsTypeMenu;
import ru.reactiveturtle.reflectthebullet.level.LevelType;
import ru.reactiveturtle.reflectthebullet.level.LevelTypeMapper;
import ru.reactiveturtle.reflectthebullet.main.settings.Settings;
import ru.reactiveturtle.reflectthebullet.main.settings.SettingsMenu;

public class MainMenuController extends Stage {
    private SpriteBatch mSpriteBatch;

    private MenuBack mMenuBack;
    private MenuTable mMenuTable;

    private Stage selectedStage;
    private MainMenu mMainMenu;
    private LevelsTypeMenu mLevelsTypeMenu;
    private LevelsMenu mLevelsMenu;
    private SettingsMenu mSettingsMenu;

    private Music mMenuBackMusic;

    private boolean mIsShowing = false;


    private ActionListener mActionListener;

    public MainMenuController(final GameContext gameContext) {
        super(gameContext);
        mMenuBack = new MenuBack(gameContext.getDisplayMetrics());
        mMenuTable = new MenuTable(gameContext.getDisplayMetrics());

        mSpriteBatch = new SpriteBatch();
        mMainMenu = new MainMenu(gameContext);
        mMainMenu.setActionListener(new MainMenu.ActionListener() {
            @Override
            public void onAction(MainMenu.Action action) {
                switch (action) {
                    case CONTINUE:
                        mActionListener.onPlay();
                        break;
                    case SELECT_LEVEL:
                        selectedStage = mLevelsTypeMenu;
                        mMenuTable.setText(MenuTable.Text.LEVELS);
                        Gdx.input.setInputProcessor(mLevelsTypeMenu);
                        break;
                    case SETTINGS:
                        selectedStage = mSettingsMenu;
                        mSettingsMenu.updateSettings(gameContext.getSettings());
                        mMenuTable.setText(MenuTable.Text.SETTINGS);
                        Gdx.input.setInputProcessor(mSettingsMenu);
                        break;
                    case EXIT:
                        mActionListener.onExit();
                        break;
                }
            }
        });
        initBackMusic();
        mLevelsTypeMenu = new LevelsTypeMenu(gameContext);
        mLevelsTypeMenu.setActionListener(new LevelsTypeMenu.ActionListener() {
            @Override
            public void onAction(LevelsTypeMenu.Action action) {
                switch (action) {
                    case ESCAPE:
                        selectedStage = mMainMenu;
                        mMenuTable.setText(MenuTable.Text.MAIN_MENU);
                        Gdx.input.setInputProcessor(mMainMenu);
                        break;
                    default:
                        selectedStage = mLevelsMenu;
                        mMenuTable.setText(MenuTable.Text.valueOf(action.toString()));
                        LevelType levelType = LevelTypeMapper.map(action);
                        mMenuBack.setBackground(levelType);
                        mLevelsMenu.showLevels(levelType, getGameContext().getLevelRepository().getLevels(levelType));
                        Gdx.input.setInputProcessor(mLevelsMenu);
                        break;
                }
            }
        });
        mLevelsMenu = new LevelsMenu(gameContext);
        mLevelsMenu.setActionListener(new LevelsMenu.ActionListener() {
            @Override
            public void onAction(LevelsMenu.Action action) {
                switch (action) {
                    case ESCAPE:
                        selectedStage = mLevelsTypeMenu;
                        mMenuTable.setText(MenuTable.Text.LEVELS);
                        Gdx.input.setInputProcessor(mLevelsTypeMenu);
                        break;
                }
            }

            @Override
            public void onLevelClick(LevelStoreData levelStoreData) {
                selectedStage = mMainMenu;
                mMenuTable.setText(MenuTable.Text.MAIN_MENU);
                mMenuBack.setMenuBackground();
                Gdx.input.setInputProcessor(mMainMenu);
                if (mActionListener != null) {
                    mActionListener.onLoadLevel(levelStoreData);
                }
            }
        });

        mSettingsMenu = new SettingsMenu(gameContext);
        mSettingsMenu.setActionListener(new SettingsMenu.ActionListener() {
            @Override
            public void onStateChanged(Settings settings) {
                mMenuBackMusic.setVolume(settings.getMusicVolume());
                if (!settings.isMusicPlaying() && mMenuBackMusic.isPlaying()) {
                    mMenuBackMusic.stop();
                } else if (settings.isMusicPlaying() && !mMenuBackMusic.isPlaying()) {
                    mMenuBackMusic.play();
                }
            }

            @Override
            public void applySettings(Settings settings) {
                gameContext.updateSettings(settings);
            }

            @Override
            public void onBackPressed() {
                selectedStage = mMainMenu;
                mMenuTable.setText(MenuTable.Text.MAIN_MENU);
                mMenuBack.setMenuBackground();
                Gdx.input.setInputProcessor(mMainMenu);
            }
        });

        selectedStage = mMainMenu;
    }

    private void initBackMusic() {
        mMenuBackMusic = Gdx.audio.newMusic(Gdx.files.internal("music/game_theme.mp3"));
        mMenuBackMusic.setLooping(true);
        System.out.println(mMenuBackMusic);
        mMenuBackMusic.setVolume(getGameContext().getSettings().getMusicVolume());
    }

    public void show() {
        mIsShowing = true;
        Gdx.input.setInputProcessor(mMainMenu);
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
        if (getGameContext().getSettings().isMusicPlaying()) {
            mMenuBackMusic.play();
        }
    }

    public void pause() {
        if (getGameContext().getSettings().isMusicPlaying()) {
            mMenuBackMusic.pause();
        }
    }

    @Override
    public void draw() {
        if (mIsShowing) {
            mSpriteBatch.begin();
            mMenuBack.draw(mSpriteBatch);
            mSpriteBatch.end();

            selectedStage.act(Gdx.graphics.getDeltaTime());
            selectedStage.draw();

            mSpriteBatch.begin();
            mMenuTable.draw(mSpriteBatch);
            mSpriteBatch.end();
        }
    }

    @Override
    public void dispose() {
        mMenuBackMusic.stop();
        mMenuBackMusic.dispose();
        mMainMenu.dispose();
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onPlay();

        void onLoadLevel(LevelStoreData levelStoreData);

        void onExit();
    }
}
