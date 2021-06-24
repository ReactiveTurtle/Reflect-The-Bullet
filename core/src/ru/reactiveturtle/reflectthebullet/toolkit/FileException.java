package ru.reactiveturtle.reflectthebullet.toolkit;

import java.io.File;

public class FileException extends RuntimeException {
    public FileException(File file, String message) {
        super("Error with file \n" + file.getAbsolutePath() + ".\n" + message);
    }
}
