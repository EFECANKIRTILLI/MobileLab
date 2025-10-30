package msku.ceng.madlab.week6;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable, Serializable {
    private String name;
    private String director;
    private int year;
    private List <String> stars = new ArrayList<>();
    private String description;

    public Movie(String name, String director, int year, List<String> stars, String description) {
        this.name = name;
        this.director = director;
        this.year = year;
        this.stars = stars;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getStars() {
        return stars;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.director);
        dest.writeInt(this.year);
        dest.writeStringList(this.stars);
        dest.writeString(this.description);
    }

    protected Movie(android.os.Parcel in) {
        this.name = in.readString();
        this.director = in.readString();
        this.year = in.readInt();
        this.stars = in.createStringArrayList();
        this.description = in.readString();
    }

    public static final android.os.Parcelable.Creator<Movie> CREATOR = new android.os.Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(android.os.Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}