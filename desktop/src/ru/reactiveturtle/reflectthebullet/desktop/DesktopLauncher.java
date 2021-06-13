package ru.reactiveturtle.reflectthebullet.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.general.GameData;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.level.LevelData;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 540;
        config.height = 960;
        new LwjglApplication(new GameContext(new App()), config);
    }

    public static class App implements ru.reactiveturtle.reflectthebullet.App {
        private static final String LEVELS_DATA_DIR = "C:/Documents/Reflect The Bullet";

        App() {
            init();
        }

        private void init() {
            File gameDataDir = new File(LEVELS_DATA_DIR);
            if (gameDataDir.exists() || gameDataDir.mkdirs()) {
                File cache = new File(LEVELS_DATA_DIR + "/levelsDataCache");
                File levelsData = new File(LEVELS_DATA_DIR + "/levelsData");
                try {
                    if (!cache.exists() && cache.createNewFile()) {
                        System.out.println("Cache file created");
                    }
                    if (!levelsData.exists() && levelsData.createNewFile()) {
                        FileWriter writer = new FileWriter(levelsData);
                        writer.write(GameData.DEFAULT_CURRENT_LEVEL_PARAMS + "\n");
                        writer.write(GameData.DEFAULT_LEVELS_INFO);
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void showMessage(String text) {

        }

        @Override
        public void setCurrentLevelParams(String levelType, int levelIndex) {
            try {
                File cache = new File(LEVELS_DATA_DIR + "/levelsDataCache");
                FileWriter writer = new FileWriter(cache);
                BufferedReader reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsData")));
                String buffer;
                while ((buffer = reader.readLine()) != null) {
                    writer.write(buffer + "\n");
                }
                writer.close();
                reader.close();

                writer = new FileWriter(new File(LEVELS_DATA_DIR + "/levelsData"));
                reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsDataCache")));
                writer.write(levelType + "&" + levelIndex + "\n");
                reader.readLine();
                while ((buffer = reader.readLine()) != null) {
                    writer.write(buffer + "\n");
                }
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String[] getCurrentLevelParams() {
            init();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsData")));
                String buffer = reader.readLine();
                reader.close();
                return buffer.split("&");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<LevelData> getLevelsInfo(String levelTypeName) {
            init();
            List<LevelData> levelsInfoList = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsData")));
                reader.readLine();
                String[] locations = reader.readLine().split(";");
                for (int i = 0; i < locations.length; i++) {
                    String[] levelsNameSplit = locations[i].split("=");
                    if (levelsNameSplit[0].equals(levelTypeName)) {
                        String[] levelsDataSplit = levelsNameSplit[1].split("&");
                        for (int j = 0; j < levelsDataSplit.length; j++) {
                            LevelData levelData = new LevelData();
                            levelData.levelType = levelsNameSplit[0];
                            String[] levelFinishSplit = levelsDataSplit[j].split(":");
                            levelData.isFinished = levelFinishSplit[0].equals("1");

                            String[] levelBestScoreSplit = levelFinishSplit[1].split("-");
                            levelData.bestScore = Integer.parseInt(levelBestScoreSplit[0]);

                            String[] levelStarsSplit = levelBestScoreSplit[1].split("#");
                            System.out.println(Arrays.deepToString(levelStarsSplit));
                            levelData.firstStarScore = Integer.parseInt(levelStarsSplit[0]);
                            levelData.secondStarScore = Integer.parseInt(levelStarsSplit[1]);
                            levelData.thirdStarScore = Integer.parseInt(levelStarsSplit[2]);
                            levelsInfoList.add(levelData);
                        }
                        reader.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return levelsInfoList;
        }

        @Override
        public void setLevelInfo(String levelTypeName, int levelIndex, int score, boolean isFinished) {
            init();
            try {
                File cache = new File(LEVELS_DATA_DIR + "/levelsDataCache");
                FileWriter writer = new FileWriter(cache);
                BufferedReader reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsData")));
                String buffer;
                while ((buffer = reader.readLine()) != null) {
                    writer.write(buffer + "\n");
                }
                writer.close();
                reader.close();

                writer = new FileWriter(new File(LEVELS_DATA_DIR + "/levelsData"));
                reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsDataCache")));
                writer.write(reader.readLine() + "\n");
                String[] locations = reader.readLine().split(";");
                for (int i = 0; i < locations.length; i++) {
                    String[] levelsNameSplit = locations[i].split("=");
                    writer.write(levelsNameSplit[0] + "=");
                    if (levelsNameSplit[0].equals(levelTypeName)) {
                        String[] levelsDataSplit = levelsNameSplit[1].split("&");
                        for (int j = 0; j < levelsDataSplit.length; j++) {
                            if (j == levelIndex - 1) {
                                String[] levelBestScoreSplit = levelsDataSplit[j].split("-");
                                System.out.println(score + ", " + levelIndex);
                                writer.write((isFinished ? 1 : 0) + ":" + score + "-" + levelBestScoreSplit[1]);
                            } else {
                                writer.write(levelsDataSplit[j]);
                            }
                            writer.write(j == levelsDataSplit.length - 1 ? "" : "&");
                        }
                    } else {
                        writer.write(levelsNameSplit[1]);
                    }
                    writer.write(";");
                }
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public LevelData getLevelData(String levelTypeName, int levelIndex) {
            init();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsData")));
                reader.readLine();
                String[] locations = reader.readLine().split(";");
                for (int i = 0; i < locations.length; i++) {
                    String[] levelsNameSplit = locations[i].split("=");
                    if (levelsNameSplit[0].equals(levelTypeName)) {
                        String[] levelsDataSplit = levelsNameSplit[1].split("&");
                        LevelData levelData = new LevelData();
                        levelData.levelType = levelsNameSplit[0];
                        String[] levelFinishSplit = levelsDataSplit[levelIndex - 1].split(":");
                        levelData.isFinished = levelFinishSplit[0].equals("1");

                        String[] levelBestScoreSplit = levelFinishSplit[1].split("-");
                        levelData.bestScore = Integer.parseInt(levelBestScoreSplit[0]);

                        String[] levelStarsSplit = levelBestScoreSplit[1].split("#");
                        levelData.firstStarScore = Integer.parseInt(levelStarsSplit[0]);
                        levelData.secondStarScore = Integer.parseInt(levelStarsSplit[1]);
                        levelData.thirdStarScore = Integer.parseInt(levelStarsSplit[2]);
                        reader.close();
                        return levelData;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public LevelData getNextLevel(String levelTypeName, int levelIndex) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(LEVELS_DATA_DIR + "/levelsData")));
                reader.readLine();
                String[] locations = reader.readLine().split(";");
                for (int i = 0; i < locations.length; i++) {
                    String[] levelsNameSplit = locations[i].split("=");
                    if (levelsNameSplit[0].equals(levelTypeName)) {
                        String[] levelsDataSplit = levelsNameSplit[1].split("&");
                        int index = levelIndex;
                        if (index == levelsDataSplit.length) {
                            index = 0;
                            if (i + 1 < locations.length) {
                                levelsNameSplit = locations[i + 1].split("=");
                                levelsDataSplit = levelsNameSplit[1].split("&");
                            }
                        }
                        LevelData levelData = new LevelData();
                        levelData.levelType = levelsNameSplit[0];
                        levelData.levelIndex = index + 1;
                        String[] levelFinishSplit = levelsDataSplit[index].split(":");
                        levelData.isFinished = levelFinishSplit[0].equals("1");

                        String[] levelBestScoreSplit = levelFinishSplit[1].split("-");
                        levelData.bestScore = Integer.parseInt(levelBestScoreSplit[0]);

                        String[] levelStarsSplit = levelBestScoreSplit[1].split("#");
                        levelData.firstStarScore = Integer.parseInt(levelStarsSplit[0]);
                        levelData.secondStarScore = Integer.parseInt(levelStarsSplit[1]);
                        levelData.thirdStarScore = Integer.parseInt(levelStarsSplit[2]);
                        reader.close();
                        return levelData;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setSettings(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
            File settingsFile = new File(LEVELS_DATA_DIR);
            if (settingsFile.exists() || settingsFile.mkdirs()) {
                settingsFile = new File(LEVELS_DATA_DIR + "/settings");
                try {
                    if (settingsFile.exists() || settingsFile.createNewFile()) {
                        FileWriter writer = new FileWriter(settingsFile);
                        writer.write((isMusicPlaying ? 1 : 0) + ":" +
                                musicVolume + ":" +
                                (isSoundFxPlaying ? 1 : 0) + ":" +
                                soundFxVolume);
                        writer.close();
                        System.out.println(isMusicPlaying + ", " + musicVolume + ", " + isSoundFxPlaying + ", " + soundFxVolume);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public String[] getSettings() {
            File settingsFile = new File(LEVELS_DATA_DIR);
            if (settingsFile.exists() || settingsFile.mkdirs()) {
                settingsFile = new File(LEVELS_DATA_DIR + "/settings");
                try {
                    if (!settingsFile.exists() && settingsFile.createNewFile()) {
                        FileWriter writer = new FileWriter(settingsFile);
                        writer.write(GameData.DEFAULT_SETTINGS);
                        writer.close();
                    }
                    if (settingsFile.exists()) {
                        BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
                        String[] settings = reader.readLine().split(":");
                        reader.close();
                        return settings;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
