package hu.yajwfs.model;


import com.google.gson.annotations.SerializedName;

public class TuneIn {
    @SerializedName("base-m3u")
    private String baseM3u;

    private String base;
    @SerializedName("base-xspf")
    private String baseXspf;

    public TuneIn() {
    }

    public String getBaseM3u() {
        return baseM3u;
    }

    public String getBase() {
        return base;
    }

    public String getBaseXspf() {
        return baseXspf;
    }

    @Override
    public String toString() {
        return "TuneIn{" +
                "baseM3u='" + baseM3u + '\'' +
                ", base='" + base + '\'' +
                ", baseXspf='" + baseXspf + '\'' +
                '}';
    }
}

