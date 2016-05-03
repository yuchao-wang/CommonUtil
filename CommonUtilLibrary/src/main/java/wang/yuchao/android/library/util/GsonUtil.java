package wang.yuchao.android.library.util;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gson解析帮助类
 * <pre>
 *     @Expose//序列化
 *     @SerializedName("name")//序列化名字
 * </pre>
 * Created by wangyuchao on 16/1/14.
 */
public class GsonUtil {
    private static Gson gson;

    private static Gson get() {
        if (gson == null) {
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.serializeNulls();
//            gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES);
//            gsonBuilder.excludeFieldsWithoutExposeAnnotation();// 只转换有 @Expose 注解的字段
//            gson = gsonBuilder.create();
            gson = new Gson();
        }
        return gson;
    }

    public static <T> T fromJsonToModel(String response, Class<T> tClass) {
        return get().fromJson(response, tClass);
    }

    public static <T> ArrayList<T> fromJsonToModelList(String response, Class<T[]> tClass) {
        T t[] = get().fromJson(response, tClass);
        ArrayList<T> allTs = new ArrayList<T>(Arrays.asList(t));
        return allTs;
    }
}
