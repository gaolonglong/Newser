package com.gaolonglong.app.newser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.bean.DouBanNews;

import java.util.List;

/**
 * Created by Admin on 2016/12/19.
 */

public class DouBanNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DouBanNews.PostsBean> doubanNewsList;
    private LayoutInflater inflater;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public DouBanNewsAdapter(Context context, List<DouBanNews.PostsBean> doubanNewsList) {
        this.context = context;
        this.doubanNewsList = doubanNewsList;
        inflater = LayoutInflater.from(context);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView description;
        ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
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
            return new DouBanNewsAdapter.FooterViewHolder(inflater.inflate(R.layout.footer_layout,parent,false));
        }else if (TYPE_NORMAL == viewType){
            return new DouBanNewsAdapter.ItemViewHolder(inflater.inflate(R.layout.douban_news_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DouBanNewsAdapter.ItemViewHolder){
            if (doubanNewsList.get(position).getThumbs().size() == 0){
                Glide.with(context)
                        .load(R.drawable.douban_default)
                        .into(((DouBanNewsAdapter.ItemViewHolder) holder).image);
            }else {
                Glide.with(context)
                        .load(doubanNewsList.get(position).getThumbs().get(0).getSmall().getUrl())
                        .placeholder(R.drawable.douban_default)
                        .error(R.drawable.douban_default)
                        .into(((ItemViewHolder) holder).image);
            }
            ((ItemViewHolder) holder).title.setText(doubanNewsList.get(position).getTitle());
            ((ItemViewHolder) holder).description.setText(doubanNewsList.get(position).getAbstractX());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, NewsDetailActivity.class);
//                    intent.putExtra("title",doubanNewsList.get(position).getTitle());
                    //intent.putExtra("picUrl",doubanNewsList.get(position).getPicUrl());
                    //intent.putExtra("url",doubanNewsList.get(position).getUrl());
//                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return doubanNewsList.size() + 1;
    }
}
