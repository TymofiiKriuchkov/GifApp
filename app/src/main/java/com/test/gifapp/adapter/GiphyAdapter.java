package com.test.gifapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.giphy.sdk.core.models.Media;
import com.test.gifapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiphyAdapter extends RecyclerView.Adapter<GiphyAdapter.GiphyAdapterViewHolder> {

    private List<Media> data;

    public GiphyAdapter(List<Media> data) {
        this.data = data;
    }

    class GiphyAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pbGif)
        ProgressBar pbGif;
        @BindView(R.id.ivGif)
        ImageView ivGif;

        GiphyAdapterViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override public GiphyAdapterViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {
        return new GiphyAdapterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final GiphyAdapterViewHolder holder, int position) {
        final Media model = data.get(position);

        Context context = holder.ivGif.getContext();

        Glide.with(context)
                .asGif()
                .load(model.getImages().getOriginal().getGifUrl())
                .listener(new RequestListener() {
                              @Override
                              public boolean onLoadFailed( GlideException e, Object model,
                                                          Target target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Object resource, Object model, Target target,
                                                             DataSource dataSource, boolean isFirstResource) {
                                  holder.ivGif.setVisibility(View.VISIBLE);
                                  holder.pbGif.setVisibility(View.GONE);
                                  return false;
                              }
                          }

                )
                .into(holder.ivGif);

    }

    @Override public int getItemCount() {
        return data.size();
    }

    @Override public void onViewRecycled(final GiphyAdapterViewHolder holder) {
        super.onViewRecycled(holder);
        holder.ivGif.setImageDrawable(null);
    }

    public void updateAdapter(List<Media> gifArray){
        data.clear();
        data.addAll(gifArray);
        notifyDataSetChanged();
    }
}
