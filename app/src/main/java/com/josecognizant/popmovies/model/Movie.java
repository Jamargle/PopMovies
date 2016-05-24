package com.josecognizant.popmovies.model;

/**
 * Class representing the Movie entity
 * Created by 552702 on 24/05/2016.
 */
public class Movie {

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
