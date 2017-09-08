package hu.yajwfs;


import hu.yajwfs.model.GenreList;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GetPrimaryGenres extends UnitTestBase {


    @Test
    public void getPrimaryGenres() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        mShoutCastApi.getPrimaryGenres(new ShoutCastApi.ApiManagerInterface<GenreList>() {
            @Override
            public void onResult(GenreList genreList) {
                assertNotNull("is null :(", genreList);
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
