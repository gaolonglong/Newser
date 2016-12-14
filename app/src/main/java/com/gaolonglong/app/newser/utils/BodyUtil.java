package com.gaolonglong.app.newser.utils;

/**
 * Created by gaohailong on 2016/12/7.
 */

public class BodyUtil {

    public static String convertBody(String body) {

        body = body.replace("<div class=\"img-place-holder\">", "");
        body = body.replace("<div class=\"headline\">", "");
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";

        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(body)
                .append("</body></html>").toString();
    }

}
