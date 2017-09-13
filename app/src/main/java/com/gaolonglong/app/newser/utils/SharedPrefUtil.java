package com.gaolonglong.app.newser.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/4/24.
 */

public class SharedPrefUtil {

    public static boolean getLike(Activity activity, String key) {
        SharedPreferences likePref = activity.getSharedPreferences("LikePref", Context.MODE_PRIVATE);
        return likePref.getBoolean(key, false);
    }

    public static void setLike(Activity activity, String key, boolean isLike) {
        SharedPreferences likePref = activity.getSharedPreferences("LikePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = likePref.edit();
        edit.putBoolean(key, isLike);
        edit.commit();
    }

    public static void setThemeColor(Activity activity,int colorPrimaryDark, int colorPrimary){
        SharedPreferences colorPref = activity.getSharedPreferences("ColorPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = colorPref.edit();
        edit.putInt("colorPrimaryDark", colorPrimaryDark);
        edit.putInt("colorPrimary", colorPrimary);
        edit.commit();
    }

    public static List<Integer> getThemeColor(Activity activity){
        SharedPreferences colorPref = activity.getSharedPreferences("ColorPref", Context.MODE_PRIVATE);
        List<Integer> colorList = new ArrayList<>();
        colorList.add(colorPref.getInt("colorPrimaryDark",0));
        colorList.add(colorPref.getInt("colorPrimary",0));
        return colorList;
    }

    public static void setNightTag(Activity activity,boolean isNight){
        SharedPreferences nightPref = activity.getSharedPreferences("nightPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = nightPref.edit();
        edit.putBoolean("isNight", isNight);
        edit.commit();
    }

    public static boolean getNightTag(Activity activity){
        SharedPreferences nightPref = activity.getSharedPreferences("nightPref", Context.MODE_PRIVATE);
        return nightPref.getBoolean("isNight",false);
    }

    public static void setCacheTag(Activity activity,boolean isCache){
        SharedPreferences cachePref = activity.getSharedPreferences("cachePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = cachePref.edit();
        edit.putBoolean("isCache", isCache);
        edit.commit();
    }

    public static boolean getCacheTag(Activity activity){
        SharedPreferences cachePref = activity.getSharedPreferences("cachePref", Context.MODE_PRIVATE);
        return cachePref.getBoolean("isCache",false);
    }
}
