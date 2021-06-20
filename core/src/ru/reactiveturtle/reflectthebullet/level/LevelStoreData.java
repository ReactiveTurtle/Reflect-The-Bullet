package ru.reactiveturtle.reflectthebullet.level;

import org.json.JSONObject;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class LevelStoreData implements JSONSerializable {
    private LevelType levelType;
    private String levelFile;

    public LevelStoreData(LevelType levelType,
                          String levelFile) {
        this.levelType = levelType;
        this.levelFile = levelFile;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public String getLevelFile() {
        return levelFile;
    }

    @Override
    public String serialize() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("levelType", levelType.toString());
        jsonObject.put("levelFile", levelFile);
        return jsonObject.toString();
    }

    public static LevelStoreData deserialize(String serialized) {
        JSONObject jsonObject = new JSONObject(serialized);
        return new LevelStoreData(
                LevelType.valueOf(jsonObject.getString("levelType")),
                jsonObject.getString("levelFile")
        );
    }
}
