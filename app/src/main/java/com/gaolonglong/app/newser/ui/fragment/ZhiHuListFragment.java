package com.gaolonglong.app.newser.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.adapter.NewsAdapter;
import com.gaolonglong.app.newser.bean.NewsBean;
import com.gaolonglong.app.newser.presenter.NewsPresenter;
import com.gaolonglong.app.newser.presenter.NewsPresenterImpl;
import com.gaolonglong.app.newser.view.NewsView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohailong on 2016/12/14.
 */

public class ZhiHuListFragment extends Fragment implements NewsView, SwipeRefreshLayout.OnRefreshListener{

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gson = new Gson();
        newsPresenter = new NewsPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.background_dark));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
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
                    //fab.hide();
                } else {
                    //fab.show();
                }
                lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();
            }
        });

        onLoadRefreshData();
    }

    private void onLoadRefreshData() {
        page = 0;
        newsPresenter.loadNews(getActivity(), page);
    }

    private void onLoadMoreData() {
        newsPresenter.loadNews(getActivity(), page);
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
            newsAdapter = new NewsAdapter(getContext(),storiesBeanList);
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

}
