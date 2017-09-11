package com.gaolonglong.app.newser.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.adapter.FavoriteAdapter;
import com.gaolonglong.app.newser.bean.ZhiHuNews;
import com.gaolonglong.app.newser.db.SQLiteDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/4/23.
 */

public class FavoriteActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TextView empty_text;
    private FavoriteAdapter newsAdapter;
    private List<ZhiHuNews.StoriesBean> storiesBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("我的收藏");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        storiesBeanList = new ArrayList<>();

        empty_text = (TextView) findViewById(R.id.empty_text);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        storiesBeanList.clear();
        Cursor cursor = SQLiteDb.getInstance().query("News", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                int news_id = cursor.getInt(cursor.getColumnIndex("news_id"));
                String img_url = cursor.getString(cursor.getColumnIndex("img_url"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                List<String> imgList = new ArrayList<>();
                imgList.add(img_url);
                storiesBeanList.add(new ZhiHuNews.StoriesBean(0,news_id,"",title,imgList));
                Log.e("233",storiesBeanList.toString());
            }while (cursor.moveToNext());
        }
        cursor.close();

        if (storiesBeanList.size() == 0){
            empty_text.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }else {
            empty_text.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }

        newsAdapter = new FavoriteAdapter(this,storiesBeanList);
        recyclerView.setAdapter(newsAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200){
            onRefresh();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
