package hu.yajwfs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API calls, which deliver xml payload
 */
interface ShoutcastServiceString {
    /**
     * How To Tune Into A Station
     * <p>
     * To tune into a station, find the "id" of the station from the API results & make a call to http://yp.shoutcast.com<base>?id=[Station_id] by appending the station id.
     * The <base> value is taken from the tunein node and based on the playlist format required (as PLS, M3U and XSPF formats are supported) you then need to choose the appropriate attribute to get the complete playlist url to use.
     * Ex: If the station id is 1025, Call => http://yp.shoutcast.com/<base>?id=1025
     *
     * @param base
     * @param stationId
     * @return
     */
    @GET("{base}")
    Call<String> getStreamUrl(@Path("base") String base, @Query("id") long stationId);


}
