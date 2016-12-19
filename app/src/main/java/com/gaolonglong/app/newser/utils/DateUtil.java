package com.gaolonglong.app.newser.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gaohailong on 2016/11/30.
 */

public class DateUtil {
    /**
     * 12小时制: "yyyy-MM-dd hh:mm:ss"
     * 24小时制: "yyyy-MM-dd HH:mm:ss"
     */
    public static String getDate(boolean isZhiHu, int day){
        SimpleDateFormat format;

        if (isZhiHu){
            format = new SimpleDateFormat("yyyyMMdd");
        }else {
            format = new SimpleDateFormat("yyyy-MM-dd");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,Integer.parseInt("-" + day));
        Date date = calendar.getTime();

        return format.format(date);
    }

}
