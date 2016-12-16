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
import com.gaolonglong.app.newser.bean.ZhiHuNews;
import com.gaolonglong.app.newser.ui.activity.ZhiHuDetailActivity;

import java.util.List;

/**
 * Created by gaohailong on 2016/11/30.
 */

public class ZhiHuNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ZhiHuNews.StoriesBean> storiesBeanList;
    private LayoutInflater inflater;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public ZhiHuNewsAdapter(Context context, List<ZhiHuNews.StoriesBean> storiesBeanList) {
        this.context = context;
        this.storiesBeanList = storiesBeanList;
        inflater = LayoutInflater.from(context);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
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
            return new FooterViewHolder(inflater.inflate(R.layout.footer_layout,parent,false));
        }else if (TYPE_NORMAL == viewType){
            return new ItemViewHolder(inflater.inflate(R.layout.zhihu_news_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder){
            Glide.with(context)
                    .load(storiesBeanList.get(position).getImages().get(0))
                    .into(((ItemViewHolder) holder).image);
            ((ItemViewHolder) holder).title.setText(storiesBeanList.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ZhiHuDetailActivity.class);
                    intent.putExtra("id",storiesBeanList.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size() + 1;
    }
}
