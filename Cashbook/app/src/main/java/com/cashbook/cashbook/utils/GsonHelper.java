package com.cashbook.cashbook.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Liyd
 * Date: 14-7-17.
 */
public class GsonHelper {

    private static final Gson gson = new Gson();

    /**
     * Serialize object to a json string
     *
     * @param object the object being serialized.
     * @return serialized string
     */
    public static String toString(@Nullable Object object) {
        if (object == null) {
            return "";
        }
        return gson.toJson(object);
    }

    /**
     * Deserialize json string to a json object.
     *
     * @param jsonString string get from toJsonString()
     * @param typeOfT    type of the expected object
     * @param <T>        type of the expected object
     * @return object of T
     */
    public static <T> T toObject(String jsonString, Type typeOfT) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        return gson.fromJson(jsonString, typeOfT);
    }

    public static List<Integer> toIntegerList(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return new ArrayList<Integer>();
        }

        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public static List<String> toStringList(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return new ArrayList<String>();
        }

        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public static <T> T to(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }
}
