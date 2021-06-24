package ru.reactiveturtle.reflectthebullet.level;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.reactiveturtle.reflectthebullet.toolkit.JSONSerializable;

public class LevelRequirements implements JSONSerializable {
    private final String[] requiredLevelFiles;
    private final int firstStarScore;
    private final int secondStarScore;
    private final int thirdStarScore;

    public LevelRequirements(String[] requiredLevelFiles,
                             int firstStarScore,
                             int secondStarScore,
                             int thirdStarScore) {
        this.requiredLevelFiles = requiredLevelFiles;
        this.firstStarScore = firstStarScore;
        this.secondStarScore = secondStarScore;
        this.thirdStarScore = thirdStarScore;
    }

    public String[] getRequiredLevelFiles() {
        return Arrays.copyOf(requiredLevelFiles, requiredLevelFiles.length);
    }

    public int getFirstStarScore() {
        return firstStarScore;
    }

    public int getSecondStarScore() {
        return secondStarScore;
    }

    public int getThirdStarScore() {
        return thirdStarScore;
    }

    @Override
    public String serialize() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.putAll(requiredLevelFiles);
        jsonObject.put("requiredLevelFiles", jsonArray.toString());
        jsonObject.put("firstStarScore", firstStarScore);
        jsonObject.put("secondStarScore", secondStarScore);
        jsonObject.put("thirdStarScore", thirdStarScore);
        return jsonObject.toString();
    }

    public static LevelRequirements deserialize(String serialized) {
        JSONObject jsonObject = new JSONObject(serialized);
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("requiredLevelFiles"));
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            stringList.add(jsonArray.getString(i));
        }
        String[] requiredLevelFiles = new String[jsonArray.length()];
        return new LevelRequirements(
                stringList.toArray(requiredLevelFiles),
                jsonObject.getInt("firstStarScore"),
                jsonObject.getInt("secondStarScore"),
                jsonObject.getInt("thirdStarScore")
        );
    }
}
