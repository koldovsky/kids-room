package ua.softserveinc.tc.util;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Util class for Json convert. Got generic method's.
 *
 * Created by TARAS on 18.06.2016.
 */
public class JsonUtil {

    /**
     * Generic type method, to convert JSON into list of needed object type.
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return List<T>
     */
    public static <T> List<T> fromJsonList(final String json, final Class<T[]> clazz) {
        return Arrays.asList(new Gson().fromJson(json, clazz));
    }


    /**
     * Generic Type method, to convert list of any type, into JSON format.
     *
     * @param list
     * @return String (of JSON)
     */
    public static String toJson(final List<?> list) {
        return new Gson().toJson(list);
    }


    /**
     * Generic Type method, to convert object of any type, into JSON format.
     *
     * @param object
     * @return String (of JSON)
     */
    public static <T> String toJson(final T object) {
        return new Gson().toJson(object);
    }
}
