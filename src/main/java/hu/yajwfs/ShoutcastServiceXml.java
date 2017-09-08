package hu.yajwfs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API calls, which deliver xml payload
 */
interface ShoutcastServiceXml {

    /**
     * Get All Genres
     * <p>
     * Description: Get all the genres on SHOUTcast Radio Directory
     * URL: http://api.shoutcast.com/legacy/genrelist?k=[Your Dev ID]
     * Required Parameters:
     * k - API Dev ID.     *
     *
     * @param apiKey your api key
     * @return
     */
    @GET("legacy/genrelist")
    Call<String> genGenreList(@Query("k") String apiKey);

    /**
     * Get Stations by Genre
     * <p>
     * Description: Get stations which match the genre specified as query.
     * URL: http://api.shoutcast.com/legacy/genresearch?k=[Your Dev ID]&genre=classic
     * Required Parameters:
     * k - API Dev ID.
     * Optional Parameters:
     * limit - Limits the no of results to be returned.
     * limit with pagination - Limits the no of results with pagination included.
     * Ex: http://api.shoutcast.com/legacy/genresearch?k=[Your Dev ID]&genre=classic&limit=X,Y
     * Y is the number of results to return and X is the offset.
     * br - Filter the stations based on bitrate specified.
     * Ex: http://api.shoutcast.com/legacy/genresearch?k=[Your Dev ID]&genre=classic&br=64
     * mt - Filter the stations based on media type specified.
     * Ex: http://api.shoutcast.com/legacy/genresearch?k=[Your Dev ID]&genre=classic&mt=audio/mpeg
     * MP3 = audio/mpeg and AAC+ = audio/aacp
     *
     * @param apiKey your api key
     * @param genre  eg rock
     * @return
     */
    @GET("legacy/genresearch")
    Call<String> getStationsByGenre(@Query("k") String apiKey, @Query("genre") String genre);

    /**
     * Description: Get stations which match the keyword searched on SHOUTcast Radio Directory.
     * Note: This API returns stations which has keyword match in the following fields Station Name, Now Playing info, Genre.
     * URL: http://api.shoutcast.com/legacy/stationsearch?k=[Your Dev ID]&search=ambient+beats
     * Required Parameters:
     * search - Specify the query to search.
     * k - API Dev ID.
     * Optional Parameters:
     * limit - Limits the no of results to be returned.
     * Ex: http://api.shoutcast.com/legacy/stationsearch?k=[Your Dev ID]&search=ambient+beats&limit=10
     * limit with pagination - Limits the no of results with pagination included.
     * Ex: http://api.shoutcast.com/legacy/stationsearch?k=[Your Dev ID]&search=ambient+beats&limit=X,Y
     * Y is the number of results to return and X is the offset.
     * br - Filter the stations based on bitrate specified.
     * Ex: http://api.shoutcast.com/legacy/stationsearch?k=[Your Dev ID]&search=ambient+beats&br=64
     * mt - Filter the stations based on media type specified.
     * Ex: http://api.shoutcast.com/legacy/stationsearch?k=[Your Dev ID]&search=ambient+beats&mt=audio/mpeg
     * MP3 = audio/mpeg and AAC+ = audio/aacp
     *
     * @param apiKey
     * @param searchText
     * @return
     */
    @GET("legacy/stationsearch")
    Call<String> getStationsByKeyword(@Query("k") String apiKey, @Query("search") String searchText, @Query("limit") int limit);


}