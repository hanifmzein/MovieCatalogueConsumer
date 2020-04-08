package com.example.moviecatalogueconsumer.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogueconsumer.model.MovieItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.moviecatalogueconsumer.db.DatabaseContract.CONTENT_URI_FAVORITE;
import static com.example.moviecatalogueconsumer.db.DatabaseContract.MovieColumns.ID;

public class MovieViewModel extends ViewModel {

    ArrayList<Integer> listIdMovie = new ArrayList<>();

    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    public void setMovie(String url, final String name, Context context) {

        AsyncHttpClient client = new AsyncHttpClient();

        final ArrayList<MovieItems> listItems = new ArrayList<>();

        //mengambil data dari URI
        Cursor cursor = context.getContentResolver().query(CONTENT_URI_FAVORITE,  null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();

            for (int i=0; i<cursor.getCount(); i++){
                listIdMovie.add(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                cursor.moveToNext();
            }
        }


        //secara async mengambil data dari API
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    JSONArray list;


                    if (name == "0") {
                        list = new JSONArray("["+result+"]");
                    } else {
                        list = responseObject.getJSONArray(name);
                    }


                    for (int i = 0; i < list.length(); i++) {

                        JSONObject movie = list.getJSONObject(i);


                        MovieItems movieItems = new MovieItems(movie);

                        if (listIdMovie.contains(movieItems.getId())) {
                            listItems.add(movieItems);
                        }
                    }

                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }


    public LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }

}
