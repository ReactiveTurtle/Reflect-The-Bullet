package ru.reactiveturtle.reflectthebullet;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.base.App;
import ru.reactiveturtle.reflectthebullet.base.GameContext;
import ru.reactiveturtle.reflectthebullet.level.LevelData;

public class AndroidLauncher extends AndroidApplication implements App {
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
    public List<LevelData> getLevelsInfo(String levelTypeName) {
        List<LevelData> levelsInfoList = new ArrayList<>();
        String[] levelsInfo = mRepository.getLevelsInfo().split(";");
        for (String s : levelsInfo) {
            String[] levelsNameSplit = s.split("=");
            if (levelsNameSplit[0].equals(levelTypeName)) {
                String[] levelsDataSplit = levelsNameSplit[1].split("&");
                for (String value : levelsDataSplit) {
                    LevelData levelData = new LevelData();
                    levelData.levelType = levelsNameSplit[0];
                    String[] levelFinishSplit = value.split(":");
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
    public LevelData getLevelData(String levelTypeName, int levelIndex) {
        String[] levelsInfo = mRepository.getLevelsInfo().split(";");
        for (String s : levelsInfo) {
            String[] levelsNameSplit = s.split("=");
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
                return levelData;
            }
        }
        return null;
    }

    @Override
    public LevelData getNextLevel(String levelTypeName, int levelIndex) {
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
                return levelData;
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
        mGameContext.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameContext.onPause();
    }
}
