package ru.reactiveturtle.reflectthebullet.toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtils {
    private FileUtils() {
    }

    public static void writeJsonFile(File file, JSONSerializable jsonSerializable) {
        if (!createFile(file)) {
            throwFileInitializingError(file);
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonSerializable.serialize());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(file, "Write json file error");
        }
    }

    public static String readJsonFile(File file) {
        if (!file.exists()) {
            throwFileInitializingError(file);
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new FileException(file, "Read json file error");
        }
    }

    public static boolean createDirectory(File file) {
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    public static boolean createFile(File file) {
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throwFileInitializingError(file);
            }
        }
        return true;
    }

    public static File getFileObject(String path, String... childs) {
        return getFileObject(new File(path), childs);
    }

    public static File getFileObject(File file, String... childs) {
        return new File(file, StringExtensions.join("/", childs));
    }

    public static void throwFileInitializingError(File file) {
        throw new FileException(file, "Initializing error");
    }
}
