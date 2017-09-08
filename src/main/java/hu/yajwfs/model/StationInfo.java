package hu.yajwfs.model;


public class StationInfo {

    private final String currentlisteners;
    private final String streamstatus;
    private final String peaklisteners;
    private final String maxlisteners;
    private final String uniquelisteners;
    private final String bitrate;
    private final String songtitle;

    public StationInfo(String currentlisteners, String streamstatus, String peaklisteners, String maxlisteners, String uniquelisteners, String bitrate, String songtitle) {
        this.currentlisteners = currentlisteners;
        this.streamstatus = streamstatus;
        this.peaklisteners = peaklisteners;
        this.maxlisteners = maxlisteners;
        this.uniquelisteners = uniquelisteners;
        this.bitrate = bitrate;
        this.songtitle = songtitle;
    }

    @Override
    public String toString() {
        return "StationInfo{" +
                "currentlisteners='" + currentlisteners + '\'' +
                ", streamstatus='" + streamstatus + '\'' +
                ", peaklisteners='" + peaklisteners + '\'' +
                ", maxlisteners='" + maxlisteners + '\'' +
                ", uniquelisteners='" + uniquelisteners + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", songtitle='" + songtitle + '\'' +
                '}';
    }

    public String getCurrentlisteners() {
        return currentlisteners;
    }

    public String getStreamstatus() {
        return streamstatus;
    }

    public String getPeaklisteners() {
        return peaklisteners;
    }

    public String getMaxlisteners() {
        return maxlisteners;
    }

    public String getUniquelisteners() {
        return uniquelisteners;
    }

    public String getBitrate() {
        return bitrate;
    }

    public String getSongtitle() {
        return songtitle;
    }

}
