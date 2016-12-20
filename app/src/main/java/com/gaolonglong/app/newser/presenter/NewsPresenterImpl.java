package com.gaolonglong.app.newser.presenter;

import android.app.Activity;

import com.gaolonglong.app.newser.api.NewsAPI;
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
    public void loadNews(Activity activity, String type, int indexPage) {
        switch (type){
            case "zhihu":
                if (indexPage == 0){
                    newsView.showLoading();
                    newsModel.loadNews(activity, NewsAPI.ZHIHU_LATEST_NEWS,this);
                }else if (indexPage > 1000000){
                    newsView.showLoading();
                    newsModel.loadNews(activity, NewsAPI.ZHIHU_NEWS_DETAIL + indexPage,this);
                }else {
                    String date = DateUtil.getDate(true, indexPage - 1);
                    newsModel.loadNews(activity, NewsAPI.ZHIHU_HISTORY_NEWS + date,this);
                }
                break;
            case "weixin":
                if (indexPage == 1){
                    newsView.showLoading();
                }
                newsModel.loadNews(activity, NewsAPI.WEIXIN_NEWS + indexPage, this);
                break;
            case "douban":
                if (indexPage > 100000){
                    newsView.showLoading();
                    newsModel.loadNews(activity, NewsAPI.DOUBAN_NEWS_DETAIL + indexPage, this);
                    return;
                }
                if (indexPage == 0){
                    newsView.showLoading();
                }
                newsModel.loadNews(activity, NewsAPI.DOUBAN_NEWS + DateUtil.getDate(false, indexPage), this);
                break;
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
