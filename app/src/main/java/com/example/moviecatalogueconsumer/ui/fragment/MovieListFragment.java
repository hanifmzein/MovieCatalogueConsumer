package com.example.moviecatalogueconsumer.ui.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogueconsumer.R;
import com.example.moviecatalogueconsumer.adapter.MovieListAdapter;
import com.example.moviecatalogueconsumer.model.MovieItems;
import com.example.moviecatalogueconsumer.viewmodel.MovieViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {
    RecyclerView rvMovie;
    ArrayList<MovieItems> list = new ArrayList<>();
    MovieViewModel movieViewModel;
    ProgressBar progressBar;
    MovieListAdapter movieAdapter;

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);


        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        //menghubugnkan antara mainview model (view model) dengnan activity
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);

        rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvMovie.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else{
            rvMovie.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        movieAdapter = new MovieListAdapter(list);
        movieAdapter.notifyDataSetChanged();
        rvMovie.setAdapter(movieAdapter);

        String url = "https://api.themoviedb.org/3/movie/popular?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US&page=1";
        String name = "results";
        movieViewModel.setMovie(url,name, getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Observer<ArrayList<MovieItems>> getMovie = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                movieAdapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
