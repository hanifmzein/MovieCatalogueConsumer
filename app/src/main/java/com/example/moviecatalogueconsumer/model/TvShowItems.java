package com.example.moviecatalogueconsumer.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShowItems implements Parcelable {

    Integer id;
    String vote;
    String title;
    String poster;
    String backdrop;


    String release;
    String overview;


    public TvShowItems(JSONObject object) {

        try {
            int id = object.getInt("id");
            String name = object.getString("name");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");
            String vote = object.getString("vote_average");
            String release = object.getString("first_air_date");
            String overview = object.getString("overview");
            this.id = id;
            this.title = name;
            this.vote = vote;
            this.poster = poster;
            this.backdrop = backdrop;
            this.release = release;
            this.overview = overview;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(release);
        parcel.writeString(overview);
        parcel.writeString(poster);
        parcel.writeString(backdrop);
        parcel.writeString(vote);
    }

    protected TvShowItems(Parcel in) {
        id = in.readInt();
        title = in.readString();
        release = in.readString();
        overview = in.readString();
        poster = in.readString();
        backdrop = in.readString();
        vote = in.readString();
    }

    public static final Creator<TvShowItems> CREATOR = new Creator<TvShowItems>() {
        @Override
        public TvShowItems createFromParcel(Parcel in) {
            return new TvShowItems(in);
        }

        @Override
        public TvShowItems[] newArray(int size) {
            return new TvShowItems[size];
        }
    };
}
