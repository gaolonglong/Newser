package com.gaolonglong.app.newser.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.bean.DouBanDetailNews;
import com.gaolonglong.app.newser.bean.ZhiHuDetailNews;
import com.gaolonglong.app.newser.db.SQLiteDb;
import com.gaolonglong.app.newser.presenter.NewsPresenter;
import com.gaolonglong.app.newser.presenter.NewsPresenterImpl;
import com.gaolonglong.app.newser.utils.BodyUtil;
import com.gaolonglong.app.newser.utils.NetworkUtil;
import com.gaolonglong.app.newser.utils.ShareUtil;
import com.gaolonglong.app.newser.utils.SharedPrefUtil;
import com.gaolonglong.app.newser.view.NewsView;
import com.google.gson.Gson;

/**
 * Created by gaohailong on 2016/12/5.
 */

public class NewsDetailActivity extends AppCompatActivity implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    private NewsPresenter newsPresenter;
    private Gson gson;
    private CollapsingToolbarLayout collapsingToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView webView;
    private ImageView headImage;
    private FloatingActionButton fab;
    private ZhiHuDetailNews zhiHuDetailNews;
    private DouBanDetailNews douBanDetailNews;
    private int news_id;
    private String title;
    private String picUrl;
    private String url;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        Intent intent = getIntent();
        news_id = intent.getIntExtra("id", 0);

        if (news_id != 0) {
            gson = new Gson();
            newsPresenter = new NewsPresenterImpl(this);
            onLoadNewsDetail(news_id);
        } else {
            title = intent.getStringExtra("title");
            picUrl = intent.getStringExtra("picUrl");
            url = intent.getStringExtra("url");

            type = "weixin";
            addNews(title, picUrl, url);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断网络情况，在进行分享
                if (NetworkUtil.isNetworkConnected(NewsDetailActivity.this)) {
                    if (news_id == 0) {
                        ShareUtil.shareText(NewsDetailActivity.this, "微信精选：" + title + " " + url);
                    } else if (news_id > 1000000) {
                        ShareUtil.shareText(NewsDetailActivity.this, "知乎日报：" + zhiHuDetailNews.getTitle() + " " + zhiHuDetailNews.getShare_url());
                    } else {
                        ShareUtil.shareImage(NewsDetailActivity.this, douBanDetailNews.getShare_pic_url());
                    }
                } else {
                    Toast.makeText(NewsDetailActivity.this, "连接WIFI再进行分享吧！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        headImage = (ImageView) findViewById(R.id.head_image);

        webView = (WebView) findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);

        String webViewCachePath = getFilesDir().getAbsolutePath() + "/webview_cache";
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(webViewCachePath);

    }

    private void addNews(String title, String picUrl, String url) {
        showLoading();

        Glide.with(this)
                .load(picUrl)
                .into(headImage);
        collapsingToolbar.setTitle(title);
        webView.loadUrl(url);

        hideLoading();
    }

    private void onLoadNewsDetail(int id) {
        if (id > 1000000) {
            type = "zhihu";
        } else {
            type = "douban";
        }
        newsPresenter.loadNews(this, type, id);
    }

    @Override
    public void onRefresh() {
        if (news_id != 0) {
            onLoadNewsDetail(news_id);
        } else {
            addNews(title, picUrl, url);
        }
    }

    @Override
    public void addNews(String result) {
        if (news_id > 1000000) {
            zhiHuDetailNews = gson.fromJson(result, ZhiHuDetailNews.class);
            Glide.with(this)
                    .load(zhiHuDetailNews.getImage())
                    .into(headImage);
            collapsingToolbar.setTitle(zhiHuDetailNews.getTitle());

            String body = BodyUtil.convertBody(zhiHuDetailNews.getBody());
            webView.loadDataWithBaseURL("x-data://base", body, "text/html", "utf-8", null);
        } else {
            douBanDetailNews = gson.fromJson(result, DouBanDetailNews.class);
            if (douBanDetailNews.getThumbs().size() > 0) {
                Glide.with(this)
                        .load(douBanDetailNews.getThumbs().get(0).getLarge().getUrl())
                        .into(headImage);
            }
            collapsingToolbar.setTitle(douBanDetailNews.getTitle());
            String content = BodyUtil.convertContent(douBanDetailNews);
            webView.loadDataWithBaseURL("x-data://base", content, "text/html", "utf-8", null);
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMsg(String msg) {
        Snackbar.make(webView, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (type.equals("zhihu")){
            getMenuInflater().inflate(R.menu.menu_news_detail_zhihu, menu);
        }else if (type.equals("douban")){
            getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_copy_url) {
            if (type.equals("zhihu")){
                ShareUtil.copyToClipboard(this, zhiHuDetailNews.getShare_url(), webView);
            }else if (type.equals("douban")){
                ShareUtil.copyToClipboard(this, douBanDetailNews.getShort_url(), webView);
            }else {
                ShareUtil.copyToClipboard(this, url, webView);
            }
        } else if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_open_in_browser) {
            if (type.equals("zhihu")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(zhiHuDetailNews.getShare_url())));
            }else if (type.equals("douban")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(douBanDetailNews.getShort_url())));
            }else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

        } else if (id == R.id.action_favorite) {
            if (SharedPrefUtil.getLike(this, news_id + "")) {
                SharedPrefUtil.setLike(this, news_id + "", false);
                SQLiteDb.getInstance().delete("News", "news_id == ?", new String[]{news_id + ""});
                Snackbar.make(webView, "已取消收藏！", Snackbar.LENGTH_SHORT).show();
            } else {
                SharedPrefUtil.setLike(this, news_id + "", true);
                ContentValues values = new ContentValues();
                values.put("news_id", news_id);
                values.put("img_url", zhiHuDetailNews.getImage());
                values.put("title", zhiHuDetailNews.getTitle());
                SQLiteDb.getInstance().insert("News", null, values);
                Snackbar.make(webView, "文章收藏成功！", Snackbar.LENGTH_SHORT).show();

            }
        }

        return super.onOptionsItemSelected(item);
    }
}

