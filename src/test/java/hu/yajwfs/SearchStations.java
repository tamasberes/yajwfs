package hu.yajwfs;


import hu.yajwfs.model.StationsResponseContainer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SearchStations extends UnitTestBase {


    private static final String STATION = "ROCK ANTENNE Deutschland";

    @Test
    public void searchStations() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        mShoutCastApi.stationSearch(STATION, 2, new ShoutCastApi.ApiManagerInterface<StationsResponseContainer>() {
            @Override
            public void onResult(StationsResponseContainer stationlist) {
                try {
                    assertNotNull("is null :(", stationlist);
                    assertNotNull("is null :(", stationlist.getContainer().getStationArray());
                    assertTrue(stationlist.getContainer().getStationArray()[0].getName().equals(STATION));
                } finally {
                    lock.countDown();
                }
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
