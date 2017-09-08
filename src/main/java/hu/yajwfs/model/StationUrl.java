package hu.yajwfs.model;


import java.util.List;

public class StationUrl {
    private List<String> urls;

    public StationUrl(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    @Override
    public String toString() {
        return "StationUrl{" +
                "urls=" + urls +
                '}';
    }
}
