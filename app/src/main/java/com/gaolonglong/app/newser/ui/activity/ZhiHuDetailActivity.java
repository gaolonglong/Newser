package com.gaolonglong.app.newser.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.gaolonglong.app.newser.R;
import com.bumptech.glide.Glide;
import com.gaolonglong.app.newser.bean.ZhiHuDetailNews;
import com.gaolonglong.app.newser.presenter.NewsPresenter;
import com.gaolonglong.app.newser.presenter.NewsPresenterImpl;
import com.gaolonglong.app.newser.utils.BodyUtil;
import com.gaolonglong.app.newser.view.NewsView;
import com.google.gson.Gson;

/**
 * Created by gaohailong on 2016/12/5.
 */

public class ZhiHuDetailActivity extends AppCompatActivity implements NewsView {

    private NewsPresenter newsPresenter;
    private Gson gson;
    private CollapsingToolbarLayout collapsingToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView webView;
    private ImageView headImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_detail_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gson = new Gson();
        newsPresenter = new NewsPresenterImpl(this);
        initView();

        onLoadNewsDetail();

    }

    private void initView() {
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        headImage = (ImageView) findViewById(R.id.head_image);

        webView = (WebView) findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
    }

    private void onLoadNewsDetail() {
        int id = getIntent().getIntExtra("id", 0);
        newsPresenter.loadNews(this, "zhihu", id);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void addNews(String result) {
        ZhiHuDetailNews zhiHuDetailNews = gson.fromJson(result, ZhiHuDetailNews.class);
        Glide.with(this)
                .load(zhiHuDetailNews.getImage())
                .into(headImage);
        collapsingToolbar.setTitle(zhiHuDetailNews.getTitle());

        String body = BodyUtil.convertBody(zhiHuDetailNews.getBody());
        webView.loadDataWithBaseURL("x-data://base", body, "text/html", "utf-8", null);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
