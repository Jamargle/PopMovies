package com.josecognizant.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class representing the Movie entity
 * Created by Jose on 24/05/2016.
 */
public class Movie implements Parcelable {
    private String originalTitle;
    private String overview;
    private String thumbnailPosterPath;
    private String voteAverage;
    private String releaseDate;

    Movie(Builder builder) {
        this.originalTitle = builder.originalTitle;
        this.overview = builder.overview;
        this.thumbnailPosterPath = builder.thumbnailPosterPath;
        this.voteAverage = builder.voteAverage;
        this.releaseDate = builder.releaseDate;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    protected Movie(Parcel in) {
        originalTitle = in.readString();
        overview = in.readString();
        thumbnailPosterPath = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getThumbnailPosterPath() {
        return thumbnailPosterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(thumbnailPosterPath);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
    }

    public static class Builder {
        private String originalTitle;
        private String overview;
        private String thumbnailPosterPath;
        private String voteAverage;
        private String releaseDate;

        public Builder originalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder overview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder thumbnailPosterPath(String path) {
            this.thumbnailPosterPath = path;
            return this;
        }

        public Builder voteAverage(String voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder releaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

}
