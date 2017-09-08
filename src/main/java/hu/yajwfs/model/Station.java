package hu.yajwfs.model;


public class Station {
    private int id;
    private String genre;
    private String mt;
    private String name;
    /**
     * Listener count!
     */
    private int lc;
    private int ml;
    /**
     * Bitrate
     */
    private int br;
    private String ct;
    private String genre3;
    private String logo;
    private String genre4;
    private String genre2;

    public Station() {
    }

    public int getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public String getMt() {
        return mt;
    }

    public String getName() {
        return name;
    }

    public int getLc() {
        return lc;
    }

    public int getMl() {
        return ml;
    }

    public int getBr() {
        return br;
    }

    public String getCt() {
        return ct;
    }

    public String getGenre3() {
        return genre3;
    }

    public String getLogo() {
        return logo;
    }

    public String getGenre4() {
        return genre4;
    }

    public String getGenre2() {
        return genre2;
    }
}
