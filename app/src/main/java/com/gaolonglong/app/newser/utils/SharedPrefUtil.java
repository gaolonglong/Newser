package com.gaolonglong.app.newser.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
}
