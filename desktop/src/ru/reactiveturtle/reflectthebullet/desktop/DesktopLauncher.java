package ru.reactiveturtle.reflectthebullet.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.nio.file.FileSystemException;

import ru.reactiveturtle.reflectthebullet.base.GameContext;

public class DesktopLauncher {
    public static final String APP_NAME = "Reflect The Bullet";
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 540;
        config.height = 960;
        config.title = APP_NAME;
        AppDesktopProvider appDesktopProvider = new AppDesktopProvider();
        new LwjglApplication(new GameContext(appDesktopProvider), config);
    }
}
