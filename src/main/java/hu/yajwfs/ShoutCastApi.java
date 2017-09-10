package hu.yajwfs;


import com.google.gson.Gson;
import hu.yajwfs.model.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoutCastApi {
    private static final String RESPONSE_FORMAT_JSON = "json";
    private final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
    private boolean isVerboseLog = true;
    private ShoutcastServiceJson shoutcastServiceJson;
    private ShoutcastServiceXml shoutcastServiceXml;
    private ShoutcastServiceString shoutcastServiceTuneIn;
    /**
     * Shoutcast API key
     */
    private String apiKey;
    private OkHttpClient okHttpClient;

    /**
     * Use this to initialize the library
     * See http://wiki.shoutcast.com/wiki/SHOUTcast_Developer
     *
     * @param apiKey       your shoutcast api key. See: https://www.shoutcast.com/Developer
     * @param okHttpClient your custom {@link OkHttpClient}. Set up your caching etc. in it.
     * @param verboseLog   true: enable some extra logging
     */
    public ShoutCastApi(String apiKey, OkHttpClient okHttpClient, boolean verboseLog) {
        this.apiKey = apiKey;
        this.isVerboseLog = verboseLog;
        //used for all json responses
        Retrofit retrofitJson = new Retrofit.Builder()
                .baseUrl("http://api.shoutcast.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        shoutcastServiceJson = retrofitJson.create(ShoutcastServiceJson.class);

        //used for all xml responses
        Retrofit retrofitXml = new Retrofit.Builder()
                .baseUrl("http://api.shoutcast.com/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        shoutcastServiceXml = retrofitXml.create(ShoutcastServiceXml.class);

        //used for the tune in stuff
        Retrofit retrofitTuneIn = new Retrofit.Builder()
                .baseUrl("http://yp.shoutcast.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        shoutcastServiceTuneIn = retrofitTuneIn.create(ShoutcastServiceString.class);

        this.okHttpClient = okHttpClient;
    }

    /**
     * http://wiki.shoutcast.com/wiki/SHOUTcast_Radio_Directory_API#Get_All_Genres
     *
     * @param callback
     */
    public void getAllGenres(final ApiManagerInterface<GenreList> callback) {
        shoutcastServiceXml.genGenreList(apiKey).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    process(response, callback, GenreList.class);
                } else {
                    handleUnsucceesfulResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    private void process(Response<String> response, ApiManagerInterface callback, Class clazz) {
        if (response.isSuccessful()) {
            String xml = response.body();
            try {
                JSONObject jsonObj;
                if (xml != null) {
                    jsonObj = XML.toJSONObject(xml);
                } else {
                    callback.onError(new IOException("response body is null"));
                    return;
                }
                if (isVerboseLog) {
                    System.out.println(jsonObj);
                }
                callback.onResult(new Gson().fromJson(jsonObj.toString(), clazz));
            } catch (JSONException e) {
                callback.onError(e);
                e.printStackTrace();
            }
        } else {
            handleUnsucceesfulResponse(response, callback);
        }
    }

    /**
     * http://wiki.shoutcast.com/wiki/SHOUTcast_Radio_Directory_API#Get_Stations_by_Genre
     *
     * @param genre
     * @param callback
     */
    public void getStationsByGenre(final String genre, final ApiManagerInterface<StationsResponseContainer> callback) {
        shoutcastServiceXml.getStationsByGenre(apiKey, genre).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(call.request().toString());
                if (response.isSuccessful()) {
                    String xml = response.body();
                    try {
                        JSONObject convertedToJsonFromXml;
                        if (xml != null) {
                            convertedToJsonFromXml = XML.toJSONObject(xml);
                        } else {
                            callback.onError(new IOException("response body is null"));
                            return;
                        }
                        if (isVerboseLog) {
                            System.out.println(convertedToJsonFromXml);
                        }
                        Object stationObject = convertedToJsonFromXml.getJSONObject("stationlist").get("station");
                        if (stationObject instanceof JSONArray) {
                            //yay normal case
                            callback.onResult(new Gson().fromJson(convertedToJsonFromXml.toString(), StationsResponseContainer.class));
                        } else {
                            Object singleStation = convertedToJsonFromXml.getJSONObject("stationlist").get("station");
                            convertedToJsonFromXml.getJSONObject("stationlist").put("station", new JSONArray(new Object[]{singleStation})); //overwrite the single object with an array which has 1 element, so gson can parse it
                            if (isVerboseLog) {
                                System.out.println("modified json:" + convertedToJsonFromXml.toString());
                            }
                            callback.onResult(new Gson().fromJson(convertedToJsonFromXml.toString(), StationsResponseContainer.class));

                        }
                    } catch (JSONException e) {
                        callback.onError(e);
                        e.printStackTrace();
                    }
                } else {
                    handleUnsucceesfulResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    /**
     * http://wiki.shoutcast.com/wiki/SHOUTcast_Radio_Directory_API#Get_Primary_Genres
     *
     * @param callback
     */
    public void getPrimaryGenres(final ApiManagerInterface<GenreList> callback) {
        shoutcastServiceJson.getPrimaryGenres(apiKey, RESPONSE_FORMAT_JSON).enqueue(new Callback<PrimaryGenreResponse>() {
            @Override
            public void onResponse(Call<PrimaryGenreResponse> call, Response<PrimaryGenreResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResponse().getData().getGenreList());
                } else {
                    handleUnsucceesfulResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<PrimaryGenreResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    /**
     * http://wiki.shoutcast.com/wiki/SHOUTcast_Radio_Directory_API#Get_Secondary_Genres
     *
     * @param callback
     */
    public void getSecondaryGenres(final ApiManagerInterface<Genre[]> callback) {
        shoutcastServiceJson.getSecondaryGenres(apiKey, RESPONSE_FORMAT_JSON, 0).enqueue(new Callback<SecondaryGenreResponse>() {
            @Override
            public void onResponse(Call<SecondaryGenreResponse> call, Response<SecondaryGenreResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResponse().getData().getGenrelist().getGenre());
                } else {
                    handleUnsucceesfulResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<SecondaryGenreResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    /**
     * http://wiki.shoutcast.com/wiki/SHOUTcast_Radio_Directory_API#How_To_Tune_Into_A_Station
     *
     * @param callback
     * @param baseM3u
     * @param stationId
     */
    public void getStationUrl(final ApiManagerInterface<StationUrl> callback, String baseM3u, long stationId) {
        shoutcastServiceTuneIn.getStreamUrl(baseM3u, stationId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    processStationUrl(callback, response);
                } else {
                    handleUnsucceesfulResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    private void handleUnsucceesfulResponse(Response response, ApiManagerInterface callback) {
        callback.onError(new IOException("response is not succesful" + response.code()));

    }

    /**
     * We get the m3u file here as a string, we need to cut it up, and get the possible url's from it.
     *
     * @param callback
     * @param response
     */
    private void processStationUrl(final ApiManagerInterface<StationUrl> callback, Response<String> response) {
        //we got the content of the m3u file here. every line contains a url. return them all maybe?
        if (response.body() == null) {
            callback.onError(new IOException("got empty response body"));
        }
        String[] m3uContent = response.body().split("\\n");
        List<String> urlList = new ArrayList<String>();
        for (String s : m3uContent) {
            if (!s.startsWith("#")) {
                urlList.add(s);
            }
        }

        StationUrl result = new StationUrl(urlList);
        callback.onResult(result);
    }

    /**
     * http://wiki.shoutcast.com/wiki/SHOUTcast_Radio_Directory_API#Get_Stations_by_Keyword_Search
     *
     * @param searchText
     * @param limit
     * @param callback
     */
    public void stationSearch(final String searchText, int limit, final ApiManagerInterface<StationsResponseContainer> callback) {
        shoutcastServiceXml.getStationsByKeyword(apiKey, searchText, limit).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(call.request().toString());
                if (response.isSuccessful()) {
                    String xml = response.body();
                    try {
                        JSONObject convertedToJsonFromXml;
                        if (xml != null) {
                            convertedToJsonFromXml = XML.toJSONObject(xml);
                        } else {
                            callback.onError(new IOException("failed to get response body xml"));
                            return;
                        }
                        if (isVerboseLog) {
                            System.out.println(convertedToJsonFromXml);
                        }
                        Object stationObject = convertedToJsonFromXml.getJSONObject("stationlist").get("station");
                        if (stationObject instanceof JSONArray) {
                            //yay normal case
                            callback.onResult(new Gson().fromJson(convertedToJsonFromXml.toString(), StationsResponseContainer.class));
                        } else {
                            Object singleStation = convertedToJsonFromXml.getJSONObject("stationlist").get("station");
                            convertedToJsonFromXml.getJSONObject("stationlist").put("station", new JSONArray(new Object[]{singleStation})); //overwrite the single object with an array which has 1 element, so gson can parse it
                            if (isVerboseLog) {
                                System.out.println("modified json:" + convertedToJsonFromXml.toString());
                            }
                            callback.onResult(new Gson().fromJson(convertedToJsonFromXml.toString(), StationsResponseContainer.class));

                        }
                    } catch (JSONException e) {
                        callback.onError(e);
                        e.printStackTrace();
                    }
                } else {
                    handleUnsucceesfulResponse(response, callback);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    /**
     * Get {@link StationInfo} using {@link StationUrl#getUrls()}
     *
     * @param stationUrl See {@link StationUrl#getUrls()}}
     * @return
     * @throws IOException
     */
    public StationInfo getStationInfo(String stationUrl) throws IOException {
        URL url;
        try {
            url = new URL(stationUrl);
        } catch (MalformedURLException e) {
            throw new IOException("failed to get station info. url invalid");
        }
        String parsedUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/7.html";
        if (!isValidURL(parsedUrl)) {
            throw new IOException("failed to get station info. url invalid");
        }

        try {
            if (isVerboseLog) {
                System.out.println("Using url:" + parsedUrl);
            }
            okhttp3.Response call = okHttpClient.newCall(new Request.Builder().get().url(parsedUrl).build()).execute();
            if (call.body() == null) {
                throw new IOException("failed to get station info. call failed");
            }
            String response = removeHtmlTags(call.body().string());
            if (isVerboseLog) {
                System.out.println("response:" + response);
            }
            int separatorCount = response.length() - response.replace(",", "").length();
            if (isVerboseLog) {
                System.out.println("separatorCount:" + separatorCount);
            }
            if (response.isEmpty() || !response.contains(",") || separatorCount != 6) {
                throw new IOException("failed to get station info. station does not support it");
            }

            //trying to parse a faulty response here:
            if (response.endsWith(",")) {
                response = response.substring(0, response.length() - 1); //cut off ending , and replace it with an empty string. This last parameter is the "now playing" text
                if (isVerboseLog) {
                    System.out.println("response, cut off extra ,:" + response);
                }
            }
            String[] cutUp = response.split(",");
            if (cutUp.length == 6) { //if just the songname is missing, that's fine.
                cutUp = new String[]{cutUp[0], cutUp[1], cutUp[2], cutUp[3], cutUp[4], cutUp[5], cutUp[6], ""};
            }
            if (cutUp.length != 7) { //it must have 7 items. the last one is the only important one, current song name
                //TODO maybe return a smaller set of values, if not all are available
                throw new IOException("failed to get station info. station does not support it all of it, just a subset");
            }
            return new StationInfo(cutUp[0], cutUp[1], cutUp[2], cutUp[3], cutUp[4], cutUp[5], cutUp[6]);
        } catch (IOException e) {
            e.printStackTrace();
            throw (e);
        }
    }


    /**
     * Is url valid? This is a stupid but cheap way to check if a url is valid
     */
    private boolean isValidURL(String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * removes html tags from the input stirng
     */
    private String removeHtmlTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("");
    }

    public interface ApiManagerInterface<T> {
        void onResult(T t);

        void onError(Throwable t);
    }
}
