package hu.yajwfs.model;

import com.google.gson.annotations.SerializedName;

public class StationsResponseContainer {

    @SerializedName("stationlist")
    private Container container;

    public StationsResponseContainer() {
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public String toString() {
        return "StationsResponseContainer{" +
                "container=" + container +
                '}';
    }

    public class Container {

        @SerializedName("tunein")
        private TuneIn tuneIn;
        @SerializedName("station")
        private Station[] stationArray;

        public Container() {
        }

        public TuneIn getTuneIn() {
            return tuneIn;
        }

        public Station[] getStationArray() {
            return stationArray;
        }
    }

}
