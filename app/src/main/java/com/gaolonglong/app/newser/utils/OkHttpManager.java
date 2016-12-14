package com.gaolonglong.app.newser.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.gaolonglong.app.newser.model.NewsModelImpl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gaohailong on 2016/11/29.
 */

public class OkHttpManager {

    private OkHttpClient okHttpClient;
    private static OkHttpManager instance;

    private OkHttpManager() {

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .cache(getCacheConfig())
                .build();
    }

    public static OkHttpManager getInstance(){
        if (instance == null){
            synchronized (OkHttpManager.class){
                if (instance == null){
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    private Cache getCacheConfig(){
        int cacheSize = 10 * 1024 * 1024;
        String cachePath = Environment.getExternalStorageDirectory().getPath() + "/mvp" + "/cache";

        File cacheDir = new File(cachePath);
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        return new Cache(cacheDir,cacheSize);
    }

    public void getLoadData(final Activity activity, String url, final NewsModelImpl.OnLoadNewsListListener listener){
        /**
         * 如果想让某次请求禁用缓存，可以调用request.cacheControl(CacheControl.FORCE_NETWORK)方法，
         * 这样即便缓存目录有对应的缓存，也会忽略缓存，强制发送网络请求，这对于获取最新的响应结果很有用
         * 如果想强制某次请求使用缓存的结果，可以调用request.cacheControl(CacheControl.FORCE_CACHE)方法，
         * 这样不会发送实际的网络请求，而是读取缓存，即便缓存数据过期了，也会强制使用该缓存作为响应数据，如果缓存不存在，那么就返回504 Unsatisfiable Request错误。
         */
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        /**
         * okhttp做离线缓存的另一种方法：1.判断网络状况，根据是否有网络给出不同的Request（如下）；2.在OkHttpClient添加cache即可，不需要Interceptor。
         */
        /*if (NetworkUtil.isNetworkConnected(activity)){
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            Toast.makeText(activity,"有网络",Toast.LENGTH_SHORT).show();
        }else {
            request = new Request.Builder()
                    .get()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .url(url)
                    .build();
            Toast.makeText(activity,"无网络",Toast.LENGTH_SHORT).show();
        }*/

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(String.valueOf(e));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    //打印响应头
                    Headers headers = response.headers();
                    for (int i = 0;i < headers.size();i++){
                        Log.e("headers","name: "+headers.name(i) + " value: " + headers.value(i));
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(result);
                        }
                    });
                }
            }
        });
    }

    public void postLoadData(final Activity activity, String url, String json, final NewsModelImpl.OnLoadNewsListListener listener){

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(String.valueOf(e));
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()){
                    final String result = response.body().string();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(result);
                        }
                    });
                }
            }
        });
    }

}
