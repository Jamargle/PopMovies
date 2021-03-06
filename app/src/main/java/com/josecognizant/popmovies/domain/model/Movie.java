package com.josecognizant.popmovies.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing the Movie entity
 * Created by Jose on 24/05/2016.
 */
public class Movie {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_MEDIUM_SIZE = "/w185";

    private long movieDbId;

    @SerializedName("id")
    private int movieApiId;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String thumbnailPosterPath;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("release_date")
    private String releaseDate;
    private String orderType;
    private int favorite;

    Movie(Builder builder) {
        this.movieDbId = builder.movieDbId;
        this.movieApiId = builder.movieApiId;
        this.originalTitle = builder.originalTitle;
        this.overview = builder.overview;
        this.thumbnailPosterPath = builder.thumbnailPosterPath;
        this.voteAverage = builder.voteAverage;
        this.releaseDate = builder.releaseDate;
        this.orderType = builder.orderType;
        this.favorite = builder.favorite;
    }

    public long getMovieDbId() {
        return movieDbId;
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

    public void setFavorite(int favoriteValue) {
        favorite = favoriteValue;
    }

    public static class Builder {
        private long movieDbId;
        private int movieApiId;
        private String originalTitle;
        private String overview;
        private String thumbnailPosterPath;
        private float voteAverage;
        private String releaseDate;
        private String orderType;
        private int favorite;

        public Builder movieDbId(long movieDbId) {
            this.movieDbId = movieDbId;
            return this;
        }

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
            if (!path.contains(BASE_IMAGE_URL)) {
                this.thumbnailPosterPath = BASE_IMAGE_URL + IMAGE_MEDIUM_SIZE + path;
            } else {
                this.thumbnailPosterPath = path;
            }
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
