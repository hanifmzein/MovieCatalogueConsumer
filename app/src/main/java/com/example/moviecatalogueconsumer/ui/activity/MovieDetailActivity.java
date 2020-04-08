package com.example.moviecatalogueconsumer.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.moviecatalogueconsumer.R;
import com.example.moviecatalogueconsumer.model.MovieItems;
import com.example.moviecatalogueconsumer.viewmodel.MovieViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {
    ConstraintLayout csLayout;
    ImageView imgBackdrop, imgPoster;
    TextView tvVote, tvOverview, tvTitle, tvRelease;
    RatingBar rbVote;
    Toolbar toolbar;
    ProgressBar progressBar;

    MovieViewModel movieViewModel;

    public static final String EXTRA_NAME = "id_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgBackdrop = findViewById(R.id.img_backdrop);
        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvVote = findViewById(R.id.tv_vote);
        tvOverview = findViewById(R.id.tv_overview);
        tvRelease = findViewById(R.id.tv_release);
        rbVote = findViewById(R.id.rb_score);


        toolbar = findViewById(R.id.toolbar_detail);

        progressBar = findViewById(R.id.progressBar);
        csLayout = findViewById(R.id.cs_isi_layout);
        showLoading(true);

        Bundle bundle = this.getIntent().getExtras();
        int id = bundle.getInt(EXTRA_NAME);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);

        String url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US";
        String name = "0";

        movieViewModel.setMovie(url,name, this);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
            layoutParams.width = convertDpToPx(223);
            tvTitle.setLayoutParams(layoutParams);

            layoutParams = tvOverview.getLayoutParams();
            layoutParams.width = convertDpToPx(371);
            tvOverview.setLayoutParams(layoutParams);

        }
        else{
            ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
            layoutParams.width = convertDpToPx(373);
            tvTitle.setLayoutParams(layoutParams);

            layoutParams = tvOverview.getLayoutParams();
            layoutParams.width = convertDpToPx(643);
            tvOverview.setLayoutParams(layoutParams);
        }
    }

    public int convertDpToPx(int dp) {
        return (int) (dp * this.getResources().getDisplayMetrics().density);
    }

    private void setDetail(ArrayList<MovieItems> movieItems){

        MovieItems movie = movieItems.get(0);



        String title = movie.getTitle()+" ("+movie.getRelease().substring(0,4)+")";
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        tvTitle.setText(title);
        tvVote.setText(movie.getVote());
        tvOverview.setText(movie.getOverview());

        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(movie.getRelease());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");
        String tgl = dateFormatter.format(date);

        tvRelease.setText(tgl);
        System.out.println("Rilis : "+tgl);

        float f = Float.valueOf(movie.getVote().trim())/2;
        rbVote.setRating(f);

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500"+movie.getBackdrop())
                .into(imgBackdrop);
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500"+movie.getPoster())
                .into(imgPoster);
    }


    private Observer<ArrayList<MovieItems>> getMovie = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                setDetail(movieItems);

                System.out.println("masuk on changed");

                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            csLayout.setVisibility(View.INVISIBLE);

        } else {
            csLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}
