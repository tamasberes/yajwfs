package hu.yajwfs.model;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class GenreList {
    @SerializedName("genre")
    private Genre[] genreList;

    public GenreList() {
    }

    public Genre[] getGenreList() {
        return genreList;
    }

    @Override
    public String toString() {
        return "GenreList{" +
                "genreList=" + Arrays.toString(genreList) +
                '}';
    }
}
