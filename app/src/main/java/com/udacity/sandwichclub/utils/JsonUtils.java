package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static Sandwich sandwichModel;

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject mainJSONObject = new JSONObject(json);
            JSONObject nameObject = mainJSONObject.getJSONObject("name");
            String name = nameObject.getString("mainName");
            JSONArray alsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsList = convertJSONArrayToArrayList(alsoKnownAs);
            String placeOfOrigin = mainJSONObject.getString("placeOfOrigin");
            String description = mainJSONObject.getString("description");
            String image = mainJSONObject.getString("image");
            JSONArray ingredients = mainJSONObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = convertJSONArrayToArrayList(ingredients);

            sandwichModel = new Sandwich(name,alsoKnownAsList, placeOfOrigin, description, image,
                    ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwichModel;
    }

    private static ArrayList<String> convertJSONArrayToArrayList(JSONArray jsonArray) {
        ArrayList<String> arrayList = new ArrayList<>();

        if (jsonArray != null) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    arrayList.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return arrayList;
    }
}
