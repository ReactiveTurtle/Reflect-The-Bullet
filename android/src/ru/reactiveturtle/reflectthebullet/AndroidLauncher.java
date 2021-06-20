package ru.reactiveturtle.reflectthebullet;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ru.reactiveturtle.reflectthebullet.base.AppProviderImpl;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.base.repository.LevelRepositoryImpl;
import ru.reactiveturtle.reflectthebullet.base.repository.SettingsRepositoryImpl;

public class AndroidLauncher extends AndroidApplication implements AppProviderImpl {
    private GameContext mGameContext;
    private Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = new Repository(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        mGameContext = new GameContext(this);
        initialize(mGameContext, config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameContext.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameContext.onPause();
    }

    @Override
    public void showMessage(String text) {

    }

    @Override
    public SettingsRepositoryImpl getSettingsRepository() {
        return null;
    }

    @Override
    public LevelRepositoryImpl getLevelRepository() {
        return null;
    }
}
