//package lgj.example.com.biyesheji.utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
///**
// * Created by yhdj on 2017/5/12.
// */
//
//public class SPUtil {
//
//
//
//
//    public static boolean getIsFirstRun(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_SP, Context.MODE_PRIVATE);
//        return sharedPreferences.getBoolean(FIRST_RUN, true);
//    }
//
//    public static void setIsFirstRun(Context context, boolean isRuned) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_SP, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(FIRST_RUN, isRuned);
//        editor.
//        editor.commit();
//    }
//
//
//    public static String getCityId(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(GET_CITY_SP, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(CITY_ID, "");
//    }
//
//    public static void setCity(Context context, String cityName) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(GET_CITY_SP, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(CITY_ID, cityName);
//        editor.commit();
//    }
//
//
//    public static float getLongitude(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(LONGITUDE_SP, Context.MODE_PRIVATE);
//        return sharedPreferences.getFloat(LONGITUDE, 0);
//    }
//
//    public static void setLongitude(Context context, float longitude) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(LONGITUDE_SP, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putFloat(LONGITUDE, longitude);
//        editor.commit();
//    }
//
//
//    public static float getLatitude(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(LATITUDE_SP, Context.MODE_PRIVATE);
//        return sharedPreferences.getFloat(LATITUDE, 0);
//    }
//
//    public static void setLatitude(Context context, float latitude) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(LATITUDE_SP, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putFloat(LATITUDE, latitude);
//        editor.commit();
//    }
//
//
//
//
//}
