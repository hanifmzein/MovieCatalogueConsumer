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
import com.example.moviecatalogueconsumer.adapter.TvShowListAdapter;
import com.example.moviecatalogueconsumer.model.TvShowItems;
import com.example.moviecatalogueconsumer.viewmodel.TvShowViewModel;

import java.util.ArrayList;

public class TvShowListFragment extends Fragment {
    RecyclerView rvTvShow;
    ArrayList<TvShowItems> list = new ArrayList<>();
    TvShowViewModel tvshowViewModel;
    ProgressBar progressBar;
    TvShowListAdapter tvshowListAdapter;

    public TvShowListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv_show_list, container, false);


        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        //menghubugnkan antara mainview model (view model) dengnan activity
        tvshowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvshowViewModel.getTvShows().observe(this, getTvShow);

        rvTvShow = view.findViewById(R.id.rv_tv_show);
        rvTvShow.setHasFixedSize(true);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvTvShow.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else{
            rvTvShow.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        tvshowListAdapter = new TvShowListAdapter(list);
        tvshowListAdapter.notifyDataSetChanged();
        rvTvShow.setAdapter(tvshowListAdapter);

        String url = "https://api.themoviedb.org/3/tv/popular?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US&page=1";
        String name = "results";
        tvshowViewModel.setTvShow(url,name,getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Observer<ArrayList<TvShowItems>> getTvShow = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowItems> tvshowItems) {
            if (tvshowItems != null) {
                tvshowListAdapter.setData(tvshowItems);
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
