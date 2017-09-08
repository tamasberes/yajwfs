package hu.yajwfs;

import hu.yajwfs.model.PrimaryGenreResponse;
import hu.yajwfs.model.SecondaryGenreResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API calls, which deliver json payload
 */
interface ShoutcastServiceJson {


    /**
     * Get Primary Genres
     * <p>
     * Description: Get only the Primary Genres on SHOUTcast Radio Directory
     * URL: http://api.shoutcast.com/genre/primary?k=[Your Dev ID]&f=xml
     * Required Parameters:
     * f - the response format (xml, json,rss). You can choose xml, json or rss based results.
     * k - API Dev ID.
     * Optional Parameters:
     * c - The callback function to invoke in the response (appropriate for JSON responses only).
     *
     * @param apiKey
     * @param responseFormat
     * @return
     */
    @GET("genre/primary")
    Call<PrimaryGenreResponse> getPrimaryGenres(@Query("k") String apiKey, @Query("f") String responseFormat);

    /**
     * Get Secondary Genres
     * <p>
     * Description: Get secondary genre list (if present) for a specified primary genre.
     * URL: http://api.shoutcast.com/genre/secondary?parentid=0&k=[Your Dev ID]&f=xml
     * Required Parameters:
     * parentid - Genreid of the primary genre. You can retreive the entire genre set by passing parentid=0.
     * f - the response format (xml, json, rss). You can choose xml,json or rss based results.
     * k - API Dev ID.
     * Optional Parameters:
     * c - The callback function to invoke in the response (appropriate for JSON responses only).
     *
     * @param apiKey
     * @param responseFormat
     * @return
     */
    @GET("genre/secondary")
    Call<SecondaryGenreResponse> getSecondaryGenres(@Query("k") String apiKey, @Query("f") String responseFormat, @Query("parentid") long parentid);
}
