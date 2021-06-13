package ru.reactiveturtle.reflectthebullet;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.general.MainGame;
import ru.reactiveturtle.reflectthebullet.general.screens.main.level.LevelInfo;

public class AndroidLauncher extends AndroidApplication implements AppInterface {
    private MainGame mMainGame;
    private Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = new Repository(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        mMainGame = new MainGame(this);
        initialize(mMainGame, config);
    }

    @Override
    public void showMessage(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setCurrentLevelParams(String levelType, int levelIndex) {
        mRepository.setCurrentLevelParams(levelType, levelIndex);
    }

    @Override
    public String[] getCurrentLevelParams() {
        String buffer = mRepository.getCurrentLevelParams();
        return buffer.split("&");
    }

    @Override
    public List<LevelInfo> getLevelsInfo(String levelTypeName) {
        List<LevelInfo> levelsInfoList = new ArrayList<>();
        String[] levelsInfo = mRepository.getLevelsInfo().split(";");
        for (String s : levelsInfo) {
            String[] levelsNameSplit = s.split("=");
            if (levelsNameSplit[0].equals(levelTypeName)) {
                String[] levelsDataSplit = levelsNameSplit[1].split("&");
                for (String value : levelsDataSplit) {
                    LevelInfo levelInfo = new LevelInfo();
                    levelInfo.levelType = levelsNameSplit[0];
                    String[] levelFinishSplit = value.split(":");
                    levelInfo.isFinished = levelFinishSplit[0].equals("1");

                    String[] levelBestScoreSplit = levelFinishSplit[1].split("-");
                    levelInfo.bestScore = Integer.parseInt(levelBestScoreSplit[0]);

                    String[] levelStarsSplit = levelBestScoreSplit[1].split("#");
                    System.out.println(Arrays.deepToString(levelStarsSplit));
                    levelInfo.firstStarScore = Integer.parseInt(levelStarsSplit[0]);
                    levelInfo.secondStarScore = Integer.parseInt(levelStarsSplit[1]);
                    levelInfo.thirdStarScore = Integer.parseInt(levelStarsSplit[2]);
                    levelsInfoList.add(levelInfo);
                }
                break;
            }
        }
        return levelsInfoList;
    }

    @Override
    public void setLevelInfo(String levelTypeName, int levelIndex, int score, boolean isFinished) {
        String[] levelsInfo = mRepository.getLevelsInfo().split(";");
        StringBuilder builder = new StringBuilder();
        for (String s : levelsInfo) {
            String[] levelsNameSplit = s.split("=");
            builder.append(levelsNameSplit[0]).append("=");
            if (levelsNameSplit[0].equals(levelTypeName)) {
                String[] levelsDataSplit = levelsNameSplit[1].split("&");
                for (int i = 0; i < levelsDataSplit.length; i++) {
                    if (i == levelIndex - 1) {
                        String[] levelBestScoreSplit = levelsDataSplit[i].split("-");
                        System.out.println(score + ", " + levelIndex);
                        builder.append(isFinished ? 1 : 0).append(":").append(score).append("-").append(levelBestScoreSplit[1]);
                    } else {
                        builder.append(levelsDataSplit[i]);
                    }
                    builder.append(i == levelsDataSplit.length - 1 ? "" : "&");
                }
            } else {
                builder.append(levelsNameSplit[1]);
            }
            builder.append(";");
        }
        mRepository.setLevelsInfo(builder.toString());
    }

    @Override
    public LevelInfo getLevelInfo(String levelTypeName, int levelIndex) {
        String[] levelsInfo = mRepository.getLevelsInfo().split(";");
        for (String s : levelsInfo) {
            String[] levelsNameSplit = s.split("=");
            if (levelsNameSplit[0].equals(levelTypeName)) {
                String[] levelsDataSplit = levelsNameSplit[1].split("&");
                LevelInfo levelInfo = new LevelInfo();
                levelInfo.levelType = levelsNameSplit[0];
                String[] levelFinishSplit = levelsDataSplit[levelIndex - 1].split(":");
                levelInfo.isFinished = levelFinishSplit[0].equals("1");

                String[] levelBestScoreSplit = levelFinishSplit[1].split("-");
                levelInfo.bestScore = Integer.parseInt(levelBestScoreSplit[0]);

                String[] levelStarsSplit = levelBestScoreSplit[1].split("#");
                levelInfo.firstStarScore = Integer.parseInt(levelStarsSplit[0]);
                levelInfo.secondStarScore = Integer.parseInt(levelStarsSplit[1]);
                levelInfo.thirdStarScore = Integer.parseInt(levelStarsSplit[2]);
                return levelInfo;
            }
        }
        return null;
    }

    @Override
    public LevelInfo getNextLevel(String levelTypeName, int levelIndex) {
        String[] locations = mRepository.getLevelsInfo().split(";");
        System.out.println(Arrays.deepToString(locations));
        int index = levelIndex;
        String name = levelTypeName;
        for (int i = 0; i < locations.length; i++) {
            String[] levelsNameSplit = locations[i].trim().split("=");
            System.out.println(Arrays.deepToString(levelsNameSplit));
            String[] levelsDataSplit = levelsNameSplit[1].split("&");
            if (levelsDataSplit.length == index) {
                index = 0;
            } else if (name.equals(levelsNameSplit[0])) {
                name = "yes";
            }
            System.out.println(index);
            if (name.equals("yes")) {
                LevelInfo levelInfo = new LevelInfo();
                levelInfo.levelType = levelsNameSplit[0];
                levelInfo.levelIndex = index + 1;
                String[] levelFinishSplit = levelsDataSplit[index].split(":");
                levelInfo.isFinished = levelFinishSplit[0].equals("1");

                String[] levelBestScoreSplit = levelFinishSplit[1].split("-");
                levelInfo.bestScore = Integer.parseInt(levelBestScoreSplit[0]);

                String[] levelStarsSplit = levelBestScoreSplit[1].split("#");
                levelInfo.firstStarScore = Integer.parseInt(levelStarsSplit[0]);
                levelInfo.secondStarScore = Integer.parseInt(levelStarsSplit[1]);
                levelInfo.thirdStarScore = Integer.parseInt(levelStarsSplit[2]);
                return levelInfo;
            }
            if (levelsDataSplit.length == index) {
                name = "yes";
            }
        }
        return null;
    }

    @Override
    public void setSettings(boolean isMusicPlaying, float musicVolume, boolean isSoundFxPlaying, float soundFxVolume) {
        mRepository.setSettings(isMusicPlaying, musicVolume, isSoundFxPlaying, soundFxVolume);
    }

    @Override
    public String[] getSettings() {
        return mRepository.getSettings().split(":");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainGame.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainGame.onPause();
    }
}
