package com.ixuea.courses.mymusic.util

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * JSON工具类
 */
object JSONUtil {
    //Gson 是 Google 开发的一个 Java 库，用于将 Java 对象转换为 JSON 字符串，以及将 JSON 字符串转换为 Java 对象。
    fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()

        //驼峰转下划线
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    /**
     * 转为json字符串
     */
    fun toJSON(data: Any): String {
        return createGson().toJson(data)
    }

    /**
     * 将Json转为对象
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
    </T> */
    fun <T> fromJSON(data: String, clazz: Class<T>): T {
        return createGson()
            .fromJson(data, clazz)
    }
}