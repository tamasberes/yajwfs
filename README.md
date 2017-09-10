# yajwfs
Yet Another Java Wrapper For Shoutcast-API

[![Release](https://jitpack.io/v/tamasberes/yajwfs.svg)](https://jitpack.io/#tamasberes/yajwfs)

See Shoutcast API docs here:
https://www.shoutcast.com/Developer

Sample usage on Android:

Set up dependency:

build.gradle
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        ...
    }
}
```

app build.gradle
```
dependencies {
    compile 'com.github.tamasberes:yajwfs:0.1.2'
    ...
}  
```

Init library:
```
import android.support.annotation.NonNull;
import java.io.File;
import java.io.IOException;
import at.myhomehub.App;
import hu.btom.myhomehub.config.Config;
import hu.yajwfs.ShoutCastApi;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public final class ShoutcastHelper {

    private static final Interceptor rewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkUtils.isOnline()) {
                int maxAge = 24 * 60; // read from cache for 24h
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    private static ShoutCastApi mShoutCastApi;

    public static ShoutCastApi get() {
        if (mShoutCastApi == null) {
            initShoutcastApi();
        }
        return mShoutCastApi;
    }

    private static void initShoutcastApi() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(rewriteCacheControlInterceptor);
        //setup cache
        File httpCacheDirectory = new File(App.getContext().getCacheDir(), "responses");
        int cacheSize = 50 * 1024 * 1024; // 50 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        okHttpBuilder.cache(cache);
        OkHttpClient okHttpClient = okHttpBuilder.build();
        mShoutCastApi = new ShoutCastApi(Config.SHOUTCAST_API_KEY, okHttpClient, BuildConfig.DEBUG);
    }
}
```

Use library:
```
private void searchStation(String searchText, int limit) {
	ShoutcastHelper.get().stationSearch(searchText, limit, new ShoutCastApi.ApiManagerInterface<StationsResponseContainer>() {
		@Override
		public void onResult(StationsResponseContainer stationlist) {
			//check results
		}

		@Override
		public void onError(Throwable t) {
			//handle error
		}
	});
}
```


