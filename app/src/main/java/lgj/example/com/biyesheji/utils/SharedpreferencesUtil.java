package lgj.example.com.biyesheji.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yu on 2017/11/5.
 */

public class SharedpreferencesUtil {
    private static String address = "news";

    public static void saveAddress(Context context, String key, String stuAddress) {
        SharedPreferences sp = context.getSharedPreferences(address, Context.MODE_PRIVATE);
        sp.edit().putString(key, stuAddress).commit();
    }

    public static String getAddress(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(address, Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
