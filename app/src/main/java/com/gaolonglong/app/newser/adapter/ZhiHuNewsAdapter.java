package com.gaolonglong.app.newser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.bean.ZhiHuNews;
import com.gaolonglong.app.newser.ui.activity.NewsDetailActivity;
import com.gaolonglong.app.newser.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohailong on 2016/11/30.
 */

public class ZhiHuNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnBannerListener {

    private Context context;
    private List<ZhiHuNews.StoriesBean> storiesBeanList;
    private List<ZhiHuNews.TopStoriesBean> topStoriesBeanList;
    private LayoutInflater inflater;
    private static final int TYPE_NORMAL = 0X001;
    private static final int TYPE_HEADER = 0X002;
    private static final int TYPE_FOOTER = 0X003;
    private List<String> imgList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public ZhiHuNewsAdapter(Context context, List<ZhiHuNews.StoriesBean> storiesBeanList, List<ZhiHuNews.TopStoriesBean> topStoriesBeanList) {
        this.context = context;
        this.storiesBeanList = storiesBeanList;
        this.topStoriesBeanList = topStoriesBeanList;
        inflater = LayoutInflater.from(context);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        Banner banner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView text;

        FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_FOOTER == viewType) {
            return new FooterViewHolder(inflater.inflate(R.layout.footer_layout, parent, false));
        } else if (TYPE_NORMAL == viewType) {
            return new ItemViewHolder(inflater.inflate(R.layout.zhihu_news_item, parent, false));
        } else if (TYPE_HEADER == viewType) {
            return new HeaderViewHolder(inflater.inflate(R.layout.header_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("233", position + "-all");
        if (holder instanceof ItemViewHolder) {
            Glide.with(context)
                    .load(storiesBeanList.get(position - 1).getImages().get(0))
                    .placeholder(R.drawable.zhihu_default_image)
                    .error(R.drawable.zhihu_default_image)
                    .into(((ItemViewHolder) holder).image);
            ((ItemViewHolder) holder).title.setText(storiesBeanList.get(position - 1).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("id", storiesBeanList.get(position - 1).getId());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            Log.e("233", position + "-head");
            //图片轮播
            for (int i = 0; i < topStoriesBeanList.size(); i++) {
                imgList.add(topStoriesBeanList.get(i).getImage());
                titleList.add(topStoriesBeanList.get(i).getTitle());
            }
            ((HeaderViewHolder) holder).banner.setOnBannerListener(this);
            ((HeaderViewHolder) holder).banner.setImages(imgList)
                    .setBannerTitles(titleList)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImageLoader(new GlideImageLoader())
                    .start();
        }
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("id", topStoriesBeanList.get(position).getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size() + 2;
    }
}
