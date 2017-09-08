package hu.yajwfs.model;


public class Data {
    private Stationlist stationlist;

    public Data() {
    }

    public Stationlist getStationlist() {
        return this.stationlist;
    }

    @Override
    public String toString() {
        return "Data{" +
                "stationlist=" + stationlist +
                '}';
    }
}
