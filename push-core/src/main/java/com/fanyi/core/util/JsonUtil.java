package com.fanyi.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 处理 json 使用的是 fastjson
 * @author Best Wu
 */
public class JsonUtil {

    /**
     * To JSON 字符串，还做了一些默认值处理 <br>
     * map集合中 是否输出 value 为 null 的字段 <br>
     * Boolean 类型的值空时 默认 false <br>
     * Number 类型的值空时 默认 0 <br>
     * List集合 空时 默认 empty  size = 0 <br>
     * String 类型值空时 默认为 空字符串 <br>
     * Date 日期类型  yyyy-MM-dd <br>
     * @param t 参数对象
     * @param <T> 对象类型
     * @return java.lang.String
     */
    public static <T> String toJsonString(T t) {
        return JSON.toJSONString(t,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 将传入类型数组 转换为 JSONArray
     * @param tArray 传入类型数组
     * @param <T> 传入类型
     * @return com.alibaba.fastjson.JSONArray
     */
    public static <T> JSONArray toJsonArray(T[] tArray) {
        return JSON.parseArray(toJsonString(tArray));
    }

    /**
     * 将 传入类型 的 list 集合 转换为 JSONArray
     * @param list 传入类型 list 集合
     * @param <T> 传入类型
     * @return com.alibaba.fastjson.JSONArray
     */
    public static <T> JSONArray toJsonArray(List<T> list) {
        return JSON.parseArray(toJsonString(list));
    }

    /**
     * 将 传入的 set 集合 转换为 JSONArray <br>
     * 泛型避免使用 Object  <br>
     * 比如在使用 TreeSet 的时候，具有排序功能 <br>
     * 使用 Object 做泛型 会出现不同数据类型的值存储异常 <br>
     * @param set 传入类型 set 集合
     * @param <T> 传入类型
     * @return com.alibaba.fastjson.JSONArray
     */
    public static <T> JSONArray toJsonArray(Set<T> set) {
        return JSON.parseArray(toJsonString(set));
    }

    /**
     * 将传入的类型对象转换为 JSONObject <br>
     * 不要将 数组，List，Set等集合对象作为参数传入，JSONArray 不能转换为 JSONObject <br>
     * 可以传入 Map，Bean 键值类型的对象 <br>
     * @param t 传入类型 对象
     * @param <T> 传入类型
     * @return com.alibaba.fastjson.JSONObject
     */
    public static <T> JSONObject toJsonObject(T t) {
        return JSON.parseObject(toJsonString(t));
    }

    /**
     * 将 JSONArray 转换成 List 集合
     * @param jsonArray JSONArray 对象
     * @param clazz List 中 泛型的类
     * @param <T> 传入的类型
     * @return java.util.List&lt;T&gt;
     */
    public static <T> List<T> parseJsonArray(JSONArray jsonArray, Class<T> clazz) {
        return JSON.parseArray(toJsonString(jsonArray), clazz);
    }

    /**
     * 将 JSONArray 解析成 Set 集合 <br>
     * 泛型不要使用 Object 避免 Set 的实际类型为 TreeSet 而引起不同数据类型的排序出问题 <br>
     * @param jsonArray JSONArray 对象
     * @param set 传入类型的 Set集合
     * @param <T> 传入的 Set中的泛型
     * @return java.util.Set&lt;T&gt;
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> parseJsonArray(JSONArray jsonArray, Set<T> set) {
        return JSON.parseObject(toJsonString(jsonArray), set.getClass());
    }

    /**
     * 支持将 JSONArray 解析成 数组对象 指定泛型 <br>
     * 该方法同时支持解析为 List 和 Set 但是建议如果转换为上述两种类型的对象 <br>
     * @see JsonUtil#parseJsonArray(JSONArray, Class) <br>
     * @see JsonUtil#parseJsonArray(JSONArray, Set) <br>
     * @param jsonArray JSONArray 对象
     * @param clazz 数组的类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T parseObject(JSONArray jsonArray, Class<T> clazz) {
        return JSON.parseObject(toJsonString(jsonArray), clazz);
    }

    /**
     * 将 json 字符串 转换为 Map 集合 value 为指定泛型的
     * @param jsonString json 字符串
     * @param map 传入类型的 Map 对象
     * @param <T> Map 中 value 的泛型
     * @return java.util.Map&lt;java.lang.String, T&gt;
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> parseMap(String jsonString, Map<String, T> map) {
        return JSON.parseObject(jsonString, map.getClass());
    }

    /**
     * 将 JSON 解析为 Object 泛型类型对象 <br>
     * 该方法不支持解析为 List，Set，但支持解析为数组 ，解析数组推荐使用：<br>
     * @see JsonUtil#parseObject(JSONArray, Class)
     * @param json JSON 对象
     * @param clazz 要解析的类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T parseObject(JSON json, Class<T> clazz) {
        return JSON.toJavaObject(json, clazz);
    }

    /**
     * json 字符串 转换为实体对象
     * @param jsonString json 字符串
     * @param clazz 类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

}
