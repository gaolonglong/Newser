package com.gaolonglong.app.newser.utils;

import android.text.TextUtils;

import com.gaolonglong.app.newser.bean.DouBanDetailNews;

import java.util.List;

/**
 * Created by gaohailong on 2016/12/7.
 */

public class BodyUtil {

    public static String convertBody(String body) {
        if (TextUtils.isEmpty(body)){
            return null;
        }
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

    public static String convertContent(DouBanDetailNews douBanDetailNews) {
        String content = douBanDetailNews.getContent();

        if (TextUtils.isEmpty(content)){
            return null;
        }
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_light.css\" type=\"text/css\">";

        List<DouBanDetailNews.PhotosBean> photoList = douBanDetailNews.getPhotos();
        for (int i = 0; i < photoList.size(); i++) {
            String old = "<img id=\"" + photoList.get(i).getTag_name() + "\" />";
            String newStr = "<img id=\"" + photoList.get(i).getTag_name() + "\" "
                    + "src=\"" + photoList.get(i).getMedium().getUrl() + "\"/>";
            content = content.replace(old, newStr);
        }
        return new StringBuilder()
                .append( "<!DOCTYPE html>\n")
                .append("<html lang=\"ZH-CN\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n<meta charset=\"utf-8\" />\n")
                .append(css)
                .append("\n</head>\n<body>\n")
                .append("<div class=\"container bs-docs-container\">\n")
                .append("<div class=\"post-container\">\n")
                .append(content)
                .append("</div>\n</div>\n</body>\n</html>")
                .toString();
    }

}
