package com.volnoor.randomuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eugene on 13.02.2018.
 */

public class RandomuserResponse {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public class Result {

        @SerializedName("name")
        @Expose
        private Name name;

        @SerializedName("picture")
        @Expose
        private Picture picture;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("registered")
        @Expose
        private String registered;

        public Name getName() {
            return name;
        }

        public Picture getPicture() {
            return picture;
        }

        public String getEmail() {
            return email;
        }

        public String getRegistered() {
            return registered;
        }
    }

    public class Name {

        @SerializedName("first")
        @Expose
        private String first;

        @SerializedName("last")
        @Expose
        private String last;

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }
    }

    public class Picture {

        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        @SerializedName("large")
        @Expose
        private String large;

        public String getThumbnail() {
            return thumbnail;
        }

        public String getLarge() {
            return large;
        }
    }
}
