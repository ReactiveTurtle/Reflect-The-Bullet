package ru.reactiveturtle.reflectthebullet.level;

import org.json.JSONObject;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class LevelData implements JSONSerializable {
    private final LevelType levelType;
    private final boolean isFinished;
    private final int bestScore;

    public LevelData(LevelType levelType,
                     int bestScore,
                     boolean isFinished) {
        this.levelType = levelType;
        this.isFinished = isFinished;
        this.bestScore = bestScore;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getBestScore() {
        return bestScore;
    }

    @Override
    public String serialize() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("levelType", levelType.toString());
        jsonObject.put("bestScore", bestScore);
        jsonObject.put("isFinished", isFinished);
        return jsonObject.toString();
    }

    public static LevelData deserialize(String serialized) {
        JSONObject jsonObject = new JSONObject(serialized);
        return new LevelData(
                LevelType.valueOf(jsonObject.getString("levelType")),
                jsonObject.getInt("bestScore"),
                jsonObject.getBoolean("isFinished")
        );
    }
}
