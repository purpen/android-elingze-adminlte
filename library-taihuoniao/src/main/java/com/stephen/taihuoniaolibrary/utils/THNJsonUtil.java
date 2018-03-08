package com.stephen.taihuoniaolibrary.utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.stephen.taihuoniaolibrary.common.HttpResponseBean;

import java.io.Reader;
import java.util.List;

public class THNJsonUtil {
    private static Gson getGson(){
        return new Gson();
    }
    public static JsonArray getJsonArray(Reader reader) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(reader);
        return element.getAsJsonArray();
    }

    public static String list2Json(List list) throws JsonSyntaxException{
        return new Gson().toJson(list);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    public static <T> T fromJson(JsonElement element, Class<T> clazz) {
        return getGson().fromJson(element, clazz);
    }

    public static <T> T fromJson(String json, TypeToken<HttpResponseBean<T>> token) throws JsonSyntaxException {
        return ((HttpResponseBean<T>) getGson().fromJson(json, token.getType())).getData();
    }

    public static <T> HttpResponseBean<T> json2Bean(String json, TypeToken<HttpResponseBean<T>> token) throws JsonSyntaxException {
        return (HttpResponseBean<T>) getGson().fromJson(json, token.getType());
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }
}

