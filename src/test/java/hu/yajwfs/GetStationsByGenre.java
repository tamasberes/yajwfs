package hu.yajwfs;


import hu.yajwfs.model.StationsResponseContainer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GetStationsByGenre extends UnitTestBase {


    @Test
    public void getStationsByGenre() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        mShoutCastApi.getStationsByGenre("rock", new ShoutCastApi.ApiManagerInterface<StationsResponseContainer>() {
            @Override
            public void onResult(StationsResponseContainer stationlist) {
                assertNotNull("is null :(", stationlist);
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
