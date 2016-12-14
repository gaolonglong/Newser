package com.gaolonglong.app.newser.presenter;

import android.app.Activity;

import com.gaolonglong.app.newser.api.API;
import com.gaolonglong.app.newser.model.NewsModel;
import com.gaolonglong.app.newser.model.NewsModelImpl;
import com.gaolonglong.app.newser.utils.DateUtil;
import com.gaolonglong.app.newser.view.NewsView;

/**
 * Created by gaohailong on 2016/11/24.
 */

public class NewsPresenterImpl implements NewsPresenter,NewsModelImpl.OnLoadNewsListListener {

    private NewsView newsView;
    private NewsModel newsModel;

    public NewsPresenterImpl(NewsView newsView) {
        this.newsView = newsView;
        this.newsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(Activity activity, int indexPage) {
        if (indexPage == 0){
            newsView.showLoading();
            newsModel.loadNews(activity, API.ZHIHU_LATEST_API,this);
        }else if (indexPage > 1000000){
            newsView.showLoading();
            newsModel.loadNews(activity,API.ZHIHU_NEWS_DETAIL + indexPage,this);
        }else {
            String date = DateUtil.getDate(indexPage - 1);
            newsModel.loadNews(activity,API.ZHIHU_BEFORE_API + date,this);
        }
    }

    @Override
    public void onSuccess(String result) {
        newsView.hideLoading();
        newsView.addNews(result);
    }

    @Override
    public void onFailure(String msg) {
        newsView.hideLoading();
        newsView.showErrorMsg(msg);
    }
}
