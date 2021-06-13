package ru.reactiveturtle.reflectthebullet.general;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import ru.reactiveturtle.reflectthebullet.AppInterface;
import ru.reactiveturtle.reflectthebullet.general.screens.game.GameScreen;
import ru.reactiveturtle.reflectthebullet.general.screens.main.MainScreen;

import static ru.reactiveturtle.reflectthebullet.general.GameData.IS_MUSIC_PLAYING;
import static ru.reactiveturtle.reflectthebullet.general.GameData.IS_SOUND_FX_PLAYING;
import static ru.reactiveturtle.reflectthebullet.general.GameData.MUSIC_VOLUME;
import static ru.reactiveturtle.reflectthebullet.general.GameData.ONE_METER;
import static ru.reactiveturtle.reflectthebullet.general.GameData.SOUND_FX_VOLUME;

public class MainGame extends ApplicationAdapter {
    private MainScreen mMainScreen;
    private GameScreen mGameScreen;

    private OrthographicCamera mCamera;

    private AppInterface mAppInterface;

    public MainGame(AppInterface appInterface) {
        this.mAppInterface = appInterface;
    }

    @Override
    public void create() {
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        loadGameData();
        mMainScreen = new MainScreen(mAppInterface, new MainScreen.ActionListener() {
            @Override
            public void onPlay() {
                mMainScreen.hide();

                mGameScreen.loadCurrentLevel();
                mGameScreen.show();

                System.out.println("I'm playing");
            }

            @Override
            public void onLoadLevel(String levelType, int levelIndex) {
                GameData.CURRENT_LOCATION = levelType;
                GameData.CURRENT_LEVEL = levelIndex;
                mAppInterface.setCurrentLevelParams(levelType, levelIndex);
                mMainScreen.hide();

                mGameScreen.loadCurrentLevel();
                mGameScreen.show();
            }

            @Override
            public void onExit() {
                System.exit(0);
            }
        });
        mMainScreen.show();
        mGameScreen = new GameScreen(mAppInterface);
        mGameScreen.setActionListener(new GameScreen.ActionListener() {
            @Override
            public void onContinue() {
                mGameScreen.start();
            }

            @Override
            public void onExit() {
                mGameScreen.hide();
                mMainScreen.show();
            }
        });
        mCamera = new OrthographicCamera(width() / ONE_METER, height() / ONE_METER);
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
        mMainScreen.draw();
    }

    @Override
    public void dispose() {
        mMainScreen.dispose();
        mGameScreen.dispose();
    }

    private void loadGameData() {
        String[] currentLevelParams = mAppInterface.getCurrentLevelParams();
        GameData.CURRENT_LOCATION = currentLevelParams[0];
        GameData.CURRENT_LEVEL = Integer.parseInt(currentLevelParams[1]);
        String[] settings = mAppInterface.getSettings();
        IS_MUSIC_PLAYING = settings[0].equals("1");
        MUSIC_VOLUME = Float.parseFloat(settings[1]);
        IS_SOUND_FX_PLAYING = settings[2].equals("1");
        SOUND_FX_VOLUME = Float.parseFloat(settings[3]);
    }

    private int width() {
        return Gdx.graphics.getWidth();
    }

    private int height() {
        return Gdx.graphics.getHeight();
    }

    public void onResume() {
        if (mMainScreen != null) {
            if (mMainScreen.isShowing()) {
                mMainScreen.start();
            }
        }
    }

    public void onPause() {
        if (mMainScreen.isShowing()) {
            mMainScreen.pause();
        } else if (mGameScreen.isShowing()) {
            mGameScreen.pause();
        }
    }
}
