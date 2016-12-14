package com.gaolonglong.app.newser.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.adapter.NewsAdapter;
import com.gaolonglong.app.newser.bean.NewsBean;
import com.gaolonglong.app.newser.presenter.NewsPresenter;
import com.gaolonglong.app.newser.presenter.NewsPresenterImpl;
import com.gaolonglong.app.newser.view.NewsView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsView, SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private NewsAdapter newsAdapter;
    private List<NewsBean.StoriesBean> storiesBeanList;
    private NewsPresenter newsPresenter;
    private Gson gson;
    private int lastVisibleItemPos;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        gson = new Gson();
        newsPresenter = new NewsPresenterImpl(this);
        initView();

        onLoadRefreshData();

    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.background_dark));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos + 1 == newsAdapter.getItemCount()){
                    page++;
                    onLoadMoreData();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newsAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 隐藏或者显示fab
                if(dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
                lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private void onLoadRefreshData() {
        page = 0;
        newsPresenter.loadNews(this, page);
    }

    private void onLoadMoreData() {
        newsPresenter.loadNews(this, page);
    }

    @Override
    public void onRefresh() {
        onLoadRefreshData();
    }

    @Override
    public void addNews(String result) {
        if (storiesBeanList == null){
            storiesBeanList = new ArrayList<>();
        }
        NewsBean newsBean = gson.fromJson(result, NewsBean.class);
        if (page == 0){
            storiesBeanList.clear();
        }
        storiesBeanList.addAll(newsBean.getStories());

        if (page == 0){
            newsAdapter = new NewsAdapter(this,storiesBeanList);
            recyclerView.setAdapter(newsAdapter);
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
        Snackbar.make(recyclerView,msg,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
