package com.gaolonglong.app.newser.model;

import android.app.Activity;

import com.gaolonglong.app.newser.utils.OkHttpManager;

/**
 * Created by gaohailong on 2016/11/24.
 */

public class NewsModelImpl implements NewsModel {

    @Override
    public void loadNews(Activity activity, String url, final OnLoadNewsListListener listener) {
        OkHttpManager.getInstance().getLoadData(activity,url,listener);
    }

    public interface OnLoadNewsListListener{
        void onSuccess(String result);
        void onFailure(String msg);
    }
}
