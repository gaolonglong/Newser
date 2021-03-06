package com.gaolonglong.app.newser.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.webkit.WebView;

/**
 * Created by Admin on 2016/12/20.
 */

public class ShareUtil {

    public static void shareText(Context context, String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(Intent.createChooser(intent,"分享文本"));
    }

    public static void shareImage(Context context, String url){

        Uri uri = OkHttpManager.getInstance((Activity) context).getImageUri(url);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        //File image = new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Screenshots/lock_wallpaper.jpg");
        //Uri uri = Uri.fromFile(image);//图片路径

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent,"分享图片"));
    }

    public static void copyToClipboard(Context context, String url, WebView webView){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("url",url));
        Snackbar.make(webView,"链接已经拷贝成功啦..",Snackbar.LENGTH_SHORT).show();
    }

}