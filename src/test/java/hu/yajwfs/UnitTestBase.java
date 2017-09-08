package hu.yajwfs;

import dont.commit.Config;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.Before;

import java.io.IOException;

public class UnitTestBase {

    ShoutCastApi mShoutCastApi;

    @Before
    public void initShoutcastApi() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = okHttpBuilder.build();
        okHttpBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());


                return response;
            }
        });
        mShoutCastApi = new ShoutCastApi(Config.SHOUTCAST_API_KEY, okHttpClient);
    }
}