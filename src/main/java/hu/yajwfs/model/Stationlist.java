package hu.yajwfs.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Stationlist {
    private ArrayList<Station> station;
    @SerializedName("tunein")
    private TuneIn tuneIn;

    public Stationlist() {
    }

    public ArrayList<Station> getStation() {
        return this.station;
    }

    public TuneIn getTuneIn() {
        return this.tuneIn;
    }

    @Override
    public String toString() {
        return "Stationlist{" +
                "station=" + station +
                ", tuneIn=" + tuneIn +
                '}';
    }
}
