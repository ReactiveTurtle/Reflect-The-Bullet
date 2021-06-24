package ru.reactiveturtle.reflectthebullet.toolkit;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class FileUtils {
    private FileUtils() {
    }

    public static void writeJsonFile(File file, JSONSerializable jsonSerializable) {
        createFile(file);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonSerializable.serialize());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(file, "Write json file error");
        }
    }

    public static String readJsonFile(FileHandle fileHandle) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader fileReader = new BufferedReader(fileHandle.reader());
            String line;
            while ((line = fileReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new FileException(fileHandle.file(), "Read json file error");
        }
    }

    public static <T extends JSONSerializable> T readJson(File file, Class<T> jsonSerializableClass) {
        String jsonStr = readJsonFile(file);
        try {
            Method deserializeMethod = jsonSerializableClass.getMethod("deserialize", String.class);
            return jsonSerializableClass.cast(deserializeMethod.invoke(null, jsonStr));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Not found static method \"deserialize(String serialized)\" in class");
    }

    public static String readJsonFile(File file) {
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

    public static void copy(FileHandle src, File dst) {
        try {
            createFile(dst);
            Reader fileReader = src.reader();
            FileWriter fileWriter = new FileWriter(dst);
            copy(src.file(), fileReader, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(dst, "File copy error");
        }
    }

    public static void copy(File src, File dst) {
        try {
            createFile(dst);
            FileReader fileReader = new FileReader(src);
            FileWriter fileWriter = new FileWriter(dst);
            copy(src, fileReader, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(dst, "File copy error");
        }
    }

    public static void copy(File src, Reader reader, Writer writer) {
        try {
            int buffer;
            while ((buffer =  reader.read()) != -1) {
                writer.write(buffer);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(src, "File copy error");
        }
    }

    public static void createDirectory(File file) {
        if (!file.exists() && !file.mkdirs()) {
            throwFileInitializingError(file);
        }
    }

    public static void createFile(File file) {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throwFileInitializingError(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throwFileInitializingError(file);
            }
        }
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
