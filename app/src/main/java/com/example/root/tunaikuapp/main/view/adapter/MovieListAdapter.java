package com.example.root.tunaikuapp.main.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.tunaikuapp.R;
import com.example.root.tunaikuapp.objects.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ArrayList<Movie> items;
    private Context ctx;

    public MovieListAdapter(Context ctx, ArrayList<Movie> items) {
        this.items = items;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_row, null);
        return new MovieViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int i) {
        holder.tv_movie_title.setText(this.items.get(i).getOriginal_title());
        String url = "http://image.tmdb.org/t/p/w500/" + this.items.get(i).getPoster_path();
        Picasso.get().load(url).transform(new CircleTransform(16, 0)).into(holder.iv_movie_cover);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_movie_title;
        ImageView iv_movie_cover;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_movie_title = itemView.findViewById(R.id.tv_movie_title);
            iv_movie_cover = itemView.findViewById(R.id.iv_movie_cover);
        }

        @Override
        public void onClick(View view) {
        }
    }

    private class CircleTransform implements Transformation {
        private final int radius;
        private final int margin;

        CircleTransform(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP));
            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin,
                    source.getHeight() - margin), radius, radius, paint);
            if (source != output) {
                source.recycle();
            }
            return output;
        }

        @Override
        public String key() {
            return "rounded(r=" + radius + ", m=" + margin + ")";
        }
    }
}
