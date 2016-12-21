package com.gaolonglong.app.newser.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.webkit.WebView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Admin on 2016/12/20.
 */

public class ShareUtil {

    private static ShareUtil instance;
    Bitmap bitmap;

    public static ShareUtil getInstance(){
        if (instance == null){
            synchronized (ShareUtil.class){
                if (instance == null){
                    instance = new ShareUtil();
                }
            }
        }
        return instance;
    }

    public static void shareText(Context context, String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(Intent.createChooser(intent,"分享文本"));
    }

    public void shareImage(Context context, String url){
        //根据图片url，返回bitmap
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap = resource;
            }
        });
        //缓存网络图片到本地，返回本地图片的Uri
        Uri uri = saveImage(context, url, bitmap);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");

        //File image = new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Screenshots/lock_wallpaper.jpg");
        //Uri uri = Uri.fromFile(image);//图片路径

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent,"分享图片"));
    }

    private static Uri saveImage(Context context, String url, Bitmap bitmap){
        //缓存图片保存路径
        String imgDir = Environment.getExternalStorageDirectory().getPath() + "/newser" + "/douban";
        //图片名称处理
        String[] fileNameArr = url.substring(url.lastIndexOf("/") + 1).split("\\.");
        String fileName = fileNameArr[0] + ".png";
        //创建文件路径
        File fileDir = new File(imgDir);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        //创建文件
        File imageFile = new File(fileDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if (compress){
                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }
            //刷新输入图片
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(imageFile);
        //发送广播，通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return uri;
    }

    public static void copyToClipboard(Context context, String url, WebView webView){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("url",url));
        Snackbar.make(webView,"链接已经拷贝成功啦..",Snackbar.LENGTH_SHORT).show();
    }

}