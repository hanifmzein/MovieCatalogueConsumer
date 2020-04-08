package com.example.moviecatalogueconsumer.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalogueconsumer.R;
import com.example.moviecatalogueconsumer.model.MovieItems;
import com.example.moviecatalogueconsumer.ui.activity.MovieDetailActivity;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private ArrayList<MovieItems> listMovie;

    public MovieListAdapter(ArrayList<MovieItems> list) {
        this.listMovie = list;
    }

    public void setData(ArrayList<MovieItems> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MovieItems movieItems = listMovie.get(position);

        //Binding title dan vote

        holder.tvTitle.setText(movieItems.getTitle()+" ("+ movieItems.getRelease().substring(0,4)+")");
        holder.tvVote.setText(movieItems.getVote());
        final int id = movieItems.getId();

        //Load Image Posternya
        Glide.with(holder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w154"+ movieItems.getPoster())
                .apply(new RequestOptions().override(480, 720))
                .into(holder.imgMovie);

        //Bikin Listener CLick di Image posternya
        holder.imgMovie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt(MovieDetailActivity.EXTRA_NAME, id);

                Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView tvTitle, tvVote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVote = itemView.findViewById(R.id.tv_vote);
        }
    }
}
