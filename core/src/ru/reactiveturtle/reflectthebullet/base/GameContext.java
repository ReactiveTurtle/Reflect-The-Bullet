package ru.reactiveturtle.reflectthebullet.base;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import ru.reactiveturtle.reflectthebullet.base.loader.LevelLoader;
import ru.reactiveturtle.reflectthebullet.base.loader.SoundLoader;
import ru.reactiveturtle.reflectthebullet.base.loader.TextureLoader;
import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.general.GameData;
import ru.reactiveturtle.reflectthebullet.game.GameScreen;
import ru.reactiveturtle.reflectthebullet.level.LastLevelData;
import ru.reactiveturtle.reflectthebullet.main.MainMenuController;
import ru.reactiveturtle.reflectthebullet.main.settings.Settings;
import ru.reactiveturtle.reflectthebullet.toolkit.Objects;

public class GameContext extends ApplicationAdapter {
    private TextureLoader mTextureLoader;
    private SoundLoader mSoundLoader;
    private LevelLoader mLevelLoader;

    private AppProviderImpl mAppProvider;
    private DisplayMetrics mDisplayMetrics;
    private Settings mSettings;

    private MainMenuController mMainMenuController;
    private GameScreen mGameScreen;

    private OrthographicCamera mCamera;

    public GameContext(AppProviderImpl appProvider) {
        this.mAppProvider = appProvider;
        mTextureLoader = new TextureLoader();
        mSoundLoader = new SoundLoader();
        mLevelLoader = new LevelLoader();
        mDisplayMetrics = new DisplayMetrics();
    }

    @Override
    public void create() {
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        loadGameData();
        mMainMenuController = new MainMenuController(this);
        mMainMenuController.setActionListener(new MainMenuController.ActionListener() {
            @Override
            public void onPlay() {
                mMainMenuController.hide();

                mGameScreen.loadCurrentLevel();
                mGameScreen.show();

                System.out.println("I'm playing");
            }

            @Override
            public void onLoadLevel(String relativeLevelDirectory) {
                getLevelRepository().setLastLevelData(new LastLevelData(relativeLevelDirectory));
                mMainMenuController.hide();

                mGameScreen.loadCurrentLevel();
                mGameScreen.show();
            }

            @Override
            public void onExit() {
                System.exit(0);
            }
        });
        mMainMenuController.show();
        mGameScreen = new GameScreen(this);
        mGameScreen.setActionListener(new GameScreen.ActionListener() {
            @Override
            public void onContinue() {
                mGameScreen.start();
            }

            @Override
            public void onExit() {
                mGameScreen.hide();
                mMainMenuController.show();
            }
        });

        DisplayMetrics displayMetrics = getDisplayMetrics();

        mCamera = new OrthographicCamera(
                displayMetrics.widthPixels() / displayMetrics.getOneMeterPixels(),
                displayMetrics.heightPixels() / displayMetrics.getOneMeterPixels());
        mCamera.translate(mCamera.viewportWidth / 2f, mCamera.viewportHeight / 2f);
        mCamera.update();
        GameData.CAMERA = mCamera;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mCamera.update();
        mGameScreen.draw();
        mMainMenuController.draw();
    }

    @Override
    public void dispose() {
        mMainMenuController.dispose();
        mGameScreen.dispose();
    }

    private void loadGameData() {
        mSettings = mAppProvider.getSettingsRepository().getSettings();
    }

    public void onResume() {
        if (mMainMenuController != null) {
            if (mMainMenuController.isShowing()) {
                mMainMenuController.start();
            }
        }
    }

    public void onPause() {
        if (mMainMenuController.isShowing()) {
            mMainMenuController.pause();
        } else if (mGameScreen.isShowing()) {
            mGameScreen.pause();
        }
    }

    public DisplayMetrics getDisplayMetrics() {
        return mDisplayMetrics;
    }

    public Settings getSettings() {
        return mSettings;
    }

    public LevelRepositoryImpl getLevelRepository() {
        return mAppProvider.getLevelRepository();
    }

    public void updateSettings(Settings settings) {
        Objects.requireNonNull(settings);
        mAppProvider.getSettingsRepository().setSettings(settings);
        mSettings = settings;
    }

    public TextureLoader getTextureLoader() {
        return mTextureLoader;
    }

    public SoundLoader getSoundLoader() {
        return mSoundLoader;
    }

    public LevelLoader getLevelLoader() {
        return mLevelLoader;
    }
}
