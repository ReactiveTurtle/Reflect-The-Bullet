package ru.reactiveturtle.reflectthebullet.base.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

import ru.reactiveturtle.reflectthebullet.level.LevelData;
import ru.reactiveturtle.reflectthebullet.level.LevelType;
import ru.reactiveturtle.reflectthebullet.toolkit.FileUtils;

public final class LevelsInitializer {
    private LevelsInitializer() {
    }

    public static void tryInitLevelStructureFiles(LevelType levelType, File levelsDirectory) {
        File levelTypeDirectory = FileUtils.getFileObject(
                levelsDirectory,
                levelType.toString().toLowerCase());
        FileUtils.createDirectory(levelTypeDirectory);
        FileHandle fileHandle = Gdx.files.internal("levels/" + levelType.toString().toLowerCase());
        FileHandle[] levelFiles = fileHandle.list();
        for (int i = 0; i < levelFiles.length; i++) {
            FileHandle srcLevelFile = new FileHandle(levelFiles[i].path() + "/" + i + "/structure.json");
            File levelDirectory = FileUtils.getFileObject(levelTypeDirectory, String.valueOf(i));
            FileUtils.createDirectory(levelDirectory);
            File dstLevelFile = FileUtils.getFileObject(levelDirectory, "structure.json");
            if (!dstLevelFile.exists()) {
                FileUtils.copy(srcLevelFile.file(), dstLevelFile);
            }
        }
    }

    public static void tryInitLevelRequirementsFiles(LevelType levelType, File levelsDirectory) {
        File levelTypeDirectory = FileUtils.getFileObject(
                levelsDirectory,
                levelType.toString().toLowerCase());
        FileUtils.createDirectory(levelTypeDirectory);
        for (int i = 1; ; i++) {
            String relativePath = levelType.toString().toLowerCase() + "/" + i + "/requirements.json";
            FileHandle levelRequirementsFile = Gdx.files.internal("levels/" + relativePath);
            if (!levelRequirementsFile.exists()) {
                break;
            }

            File levelDirectory = FileUtils.getFileObject(levelTypeDirectory, String.valueOf(i));
            File dstLevelFile = FileUtils.getFileObject(levelDirectory, "requirements.json");
            if (!dstLevelFile.exists()) {
                FileUtils.copy(levelRequirementsFile, FileUtils.getFileObject(
                        levelsDirectory, relativePath));
            }
        }
    }

    public static void tryInitLevelDataFiles(LevelType levelType, File levelsDirectory) {
        File levelTypeDirectory = FileUtils.getFileObject(levelsDirectory,
                levelType.toString().toLowerCase());
        FileUtils.createDirectory(levelTypeDirectory);
        for (int i = 1; ; i++) {
            String requirementsRelativePath = "levels/" + levelType.toString().toLowerCase()
                    + "/" + i + "/requirements.json";
            FileHandle levelRequirementsFile = Gdx.files.internal(requirementsRelativePath);
            if (!levelRequirementsFile.exists()) {
                break;
            }

            File levelDirectory = FileUtils.getFileObject(levelTypeDirectory, String.valueOf(i));
            FileUtils.createDirectory(levelDirectory);
            File dstLevelFile = FileUtils.getFileObject(levelDirectory, "levelData.json");
            if (!dstLevelFile.exists()) {
                LevelData levelData = new LevelData(
                        levelType,
                        0,
                        false
                );
                FileUtils.writeJsonFile(dstLevelFile, levelData);
            }
        }
    }

    public static void tryInitLevelStructure(File levelsDirectory, String relativeLevelDirectory) {
        File levelDirectory = FileUtils.getFileObject(levelsDirectory, relativeLevelDirectory);
        FileUtils.createDirectory(levelDirectory);
        FileHandle srcLevelFile = new FileHandle( "levels/" + relativeLevelDirectory + "/structure.json");
        File dstLevelFile = FileUtils.getFileObject(levelDirectory, "structure.json");
        if (!dstLevelFile.exists()) {
            FileUtils.copy(srcLevelFile.file(), dstLevelFile);
        }
    }
}
