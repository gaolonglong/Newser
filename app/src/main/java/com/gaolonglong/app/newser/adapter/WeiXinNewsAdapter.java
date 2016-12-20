package com.gaolonglong.app.newser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.bean.WeiXinNews;
import com.gaolonglong.app.newser.ui.activity.NewsDetailActivity;

import java.util.List;

/**
 * Created by gaohailong on 2016/12/16.
 */

public class WeiXinNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WeiXinNews.NewslistBean> weixinNewsList;
    private LayoutInflater inflater;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public WeiXinNewsAdapter(Context context, List<WeiXinNews.NewslistBean> weixinNewsList) {
        this.context = context;
        this.weixinNewsList = weixinNewsList;
        inflater = LayoutInflater.from(context);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView author;
        TextView date;
        ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder{
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
        if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_FOOTER == viewType){
            return new WeiXinNewsAdapter.FooterViewHolder(inflater.inflate(R.layout.footer_layout,parent,false));
        }else if (TYPE_NORMAL == viewType){
            return new WeiXinNewsAdapter.ItemViewHolder(inflater.inflate(R.layout.weixin_news_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WeiXinNewsAdapter.ItemViewHolder){
            Glide.with(context)
                    .load(weixinNewsList.get(position).getPicUrl())
                    .placeholder(R.drawable.douban_default)
                    .error(R.drawable.douban_default)
                    .into(((WeiXinNewsAdapter.ItemViewHolder) holder).image);
            ((ItemViewHolder) holder).title.setText(weixinNewsList.get(position).getTitle());
            ((ItemViewHolder) holder).author.setText(weixinNewsList.get(position).getDescription());
            ((ItemViewHolder) holder).date.setText(weixinNewsList.get(position).getCtime());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("title",weixinNewsList.get(position).getTitle());
                    intent.putExtra("picUrl",weixinNewsList.get(position).getPicUrl());
                    intent.putExtra("url",weixinNewsList.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return weixinNewsList.size() + 1;
    }
}
