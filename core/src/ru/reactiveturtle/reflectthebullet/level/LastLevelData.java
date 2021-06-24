package ru.reactiveturtle.reflectthebullet.level;

import org.json.JSONObject;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class LastLevelData implements JSONSerializable {
    private final String lastLevelDirectory;

    public LastLevelData(String lastLevelDirectory) {
        this.lastLevelDirectory = lastLevelDirectory;
    }

    public String getLastLevelDirectory() {
        return lastLevelDirectory;
    }

    @Override
    public String serialize() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastLevelDirectory", lastLevelDirectory);
        return jsonObject.toString();
    }

    public static LastLevelData deserialize(String serialized) {
        JSONObject jsonObject = new JSONObject(serialized);
        return new LastLevelData(
                jsonObject.getString("lastLevelDirectory")
        );
    }
}
