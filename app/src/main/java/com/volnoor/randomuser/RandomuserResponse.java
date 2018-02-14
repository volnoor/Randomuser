package com.volnoor.randomuser;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static class Result implements Parcelable {

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

        protected Result(Parcel in) {
            name = in.readParcelable(Name.class.getClassLoader());
            picture = in.readParcelable(Picture.class.getClassLoader());
            email = in.readString();
            registered = in.readString();
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(name, flags);
            dest.writeParcelable(picture, flags);
            dest.writeString(email);
            dest.writeString(registered);
        }
    }

    public static class Name implements Parcelable {

        @SerializedName("first")
        @Expose
        private String first;

        @SerializedName("last")
        @Expose
        private String last;

        protected Name(Parcel in) {
            first = in.readString();
            last = in.readString();
        }

        public static final Creator<Name> CREATOR = new Creator<Name>() {
            @Override
            public Name createFromParcel(Parcel in) {
                return new Name(in);
            }

            @Override
            public Name[] newArray(int size) {
                return new Name[size];
            }
        };

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(first);
            dest.writeString(last);
        }
    }

    public static class Picture implements Parcelable {

        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        @SerializedName("large")
        @Expose
        private String large;

        protected Picture(Parcel in) {
            thumbnail = in.readString();
            large = in.readString();
        }

        public static final Creator<Picture> CREATOR = new Creator<Picture>() {
            @Override
            public Picture createFromParcel(Parcel in) {
                return new Picture(in);
            }

            @Override
            public Picture[] newArray(int size) {
                return new Picture[size];
            }
        };

        public String getThumbnail() {
            return thumbnail;
        }

        public String getLarge() {
            return large;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(thumbnail);
            dest.writeString(large);
        }
    }
}
