package com.gaolonglong.app.newser.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaolonglong.app.newser.R;
import com.gaolonglong.app.newser.bean.ZhiHuNews;
import com.gaolonglong.app.newser.ui.activity.NewsDetailActivity;

import java.util.List;

/**
 * Created by Admin on 2017/4/23.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Activity context;
    private List<ZhiHuNews.StoriesBean> storiesBeanList;
    private LayoutInflater inflater;

    public FavoriteAdapter(Context context, List<ZhiHuNews.StoriesBean> storiesBeanList) {
        this.context = (Activity) context;
        this.storiesBeanList = storiesBeanList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zhihu_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(storiesBeanList.get(position).getImages().get(0))
                .placeholder(R.drawable.zhihu_default_image)
                .error(R.drawable.zhihu_default_image)
                .into((holder).image);
        (holder).title.setText(storiesBeanList.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("id", storiesBeanList.get(position).getId());
                context.startActivityForResult(intent,200);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size();
    }
}
