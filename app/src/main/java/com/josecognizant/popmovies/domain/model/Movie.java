package com.josecognizant.popmovies.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class representing the Movie entity
 * Created by Jose on 24/05/2016.
 */
public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private int movieApiId;
    private String originalTitle;
    private String overview;
    private String thumbnailPosterPath;
    private float voteAverage;
    private String releaseDate;
    private String orderType;
    private int favorite;

    Movie(Builder builder) {
        this.movieApiId = builder.movieApiId;
        this.originalTitle = builder.originalTitle;
        this.overview = builder.overview;
        this.thumbnailPosterPath = builder.thumbnailPosterPath;
        this.voteAverage = builder.voteAverage;
        this.releaseDate = builder.releaseDate;
        this.orderType = builder.orderType;
        this.favorite = builder.favorite;
    }

    protected Movie(Parcel in) {
        movieApiId = in.readInt();
        originalTitle = in.readString();
        overview = in.readString();
        thumbnailPosterPath = in.readString();
        voteAverage = in.readFloat();
        releaseDate = in.readString();
        orderType = in.readString();
        favorite = in.readInt();
    }

    public int getMovieApiId() {
        return movieApiId;
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

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        if (!orderType.isEmpty()) {
            this.orderType = orderType;
        }
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        if (favorite == 0 || favorite == 1) {
            this.favorite = favorite;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieApiId);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(thumbnailPosterPath);
        dest.writeFloat(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(orderType);
        dest.writeInt(favorite);
    }

    public static class Builder {
        private int movieApiId;
        private String originalTitle;
        private String overview;
        private String thumbnailPosterPath;
        private float voteAverage;
        private String releaseDate;
        private String orderType;
        private int favorite;

        public Builder movieApiId(int movieApiId) {
            this.movieApiId = movieApiId;
            return this;
        }

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

        public Builder voteAverage(float voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder releaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder orderType(String orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder favorite(int favorite) {
            if (favorite == 0 || favorite == 1) {
                this.favorite = favorite;
            } else {
                this.favorite = 0;
            }
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
