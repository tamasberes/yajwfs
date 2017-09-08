package hu.yajwfs;


import hu.yajwfs.model.Genre;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GetSecondaryGenres extends UnitTestBase {


    @Test
    public void getSecondaryGenres() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        mShoutCastApi.getSecondaryGenres(new ShoutCastApi.ApiManagerInterface<Genre[]>() {
            @Override
            public void onResult(Genre[] genres) {
                assertNotNull("is null :(", genres);
                lock.countDown();
            }

            @Override
            public void onError(Throwable t) {
                assertNull("got an exception", t);
                lock.countDown();
            }
        });

        lock.await(60, TimeUnit.SECONDS);
    }
}
