package com.gaolonglong.app.newser.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.gaolonglong.app.newser.R;

/**
 * Created by Admin on 2016/12/26.
 */

public class ThemeUtil {

    public static final int DAY_THEME = 0;
    public static final int NIGHT_THEME = 1;
    private static int mThemeType;
    public static boolean isNightTheme = false;

    public static void changeAppTheme(Activity activity, int themeType){
        mThemeType = themeType;
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.finish();
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void setAppTheme(Activity activity){
        switch (mThemeType){
            case DAY_THEME:
                activity.setTheme(R.style.AppTheme);
                SharedPrefUtil.setNightTag(activity,false);
                break;
            case NIGHT_THEME:
                activity.setTheme(R.style.AppThemeNight);
                SharedPrefUtil.setNightTag(activity,true);
                break;
            default:
                activity.setTheme(R.style.AppTheme);
                break;
        }
    }
}
