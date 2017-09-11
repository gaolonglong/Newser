package com.gaolonglong.app.newser.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.adapter.ZhiHuNewsAdapter;
import com.gaolonglong.app.newser.bean.ZhiHuNews;
import com.gaolonglong.app.newser.presenter.NewsPresenter;
import com.gaolonglong.app.newser.presenter.NewsPresenterImpl;
import com.gaolonglong.app.newser.view.NewsView;
import com.google.gson.Gson;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohailong on 2016/12/14.
 */

public class ZhiHuFragment extends Fragment implements NewsView, SwipeRefreshLayout.OnRefreshListener, OnBannerListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ZhiHuNewsAdapter zhiHuNewsAdapter;
    private List<ZhiHuNews.StoriesBean> storiesBeanList;
    private List<ZhiHuNews.TopStoriesBean> topStoriesBeanList;
    private NewsPresenter newsPresenter;
    private Gson gson;
    private int lastVisibleItemPos;
    private int page;
    private String type;
    private OnFabShowListener onFabShowListener;

    @Override
    public void onAttach(Context context) {
        onFabShowListener = (OnFabShowListener) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = "zhihu";
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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPos + 1 == zhiHuNewsAdapter.getItemCount()) {
                    Log.e("233",zhiHuNewsAdapter.getItemCount()+"---"+lastVisibleItemPos);
                    page++;
                    topStoriesBeanList.clear();
                    onLoadMoreData();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            zhiHuNewsAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 隐藏或者显示fab
                if (dy > 0) {
                    //fab.hide();
                    onFabShowListener.isShow(false);
                } else {
                    //fab.show();
                    onFabShowListener.isShow(true);
                }
                lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();
            }
        });

        onLoadRefreshData();
    }

    private void onLoadRefreshData() {
        page = 0;
        newsPresenter.loadNews(getActivity(), type, page);
    }

    private void onLoadMoreData() {
        newsPresenter.loadNews(getActivity(), type, page);
    }

    @Override
    public void onRefresh() {
        onLoadRefreshData();
    }

    @Override
    public void addNews(String result) {
        if (storiesBeanList == null) {
            storiesBeanList = new ArrayList<>();
            topStoriesBeanList = new ArrayList<>();
        }
        ZhiHuNews zhiHuNews = gson.fromJson(result, ZhiHuNews.class);
        if (page == 0) {
            storiesBeanList.clear();
            topStoriesBeanList.clear();
        }
        storiesBeanList.addAll(zhiHuNews.getStories());
        if (zhiHuNews.getTop_stories() != null){
            topStoriesBeanList.clear();
            topStoriesBeanList.addAll(zhiHuNews.getTop_stories());
        }
        //日报列表
        if (page == 0) {
            zhiHuNewsAdapter = new ZhiHuNewsAdapter(getContext(), storiesBeanList, topStoriesBeanList);
            recyclerView.setAdapter(zhiHuNewsAdapter);
        }
    }

    @Override
    public void OnBannerClick(int position) {

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
        Snackbar.make(recyclerView, msg, Snackbar.LENGTH_LONG).show();
    }

    public interface OnFabShowListener {
        void isShow(boolean show);
    }

}
