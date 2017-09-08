package hu.yajwfs;


import hu.yajwfs.model.GenreList;
import hu.yajwfs.model.StationUrl;
import hu.yajwfs.model.StationsResponseContainer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GetStationUrl extends UnitTestBase {


    @Test
    public void getStationUrl() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        mShoutCastApi.getPrimaryGenres(new ShoutCastApi.ApiManagerInterface<GenreList>() {
            @Override
            public void onResult(GenreList genreList) {
                mShoutCastApi.getStationsByGenre(genreList.getGenreList()[5].getName(), new ShoutCastApi.ApiManagerInterface<StationsResponseContainer>() {
                    @Override
                    public void onResult(StationsResponseContainer stationsResponseContainer) {
                        assertNotNull("is null :(", stationsResponseContainer);
                        mShoutCastApi.getStationUrl(new ShoutCastApi.ApiManagerInterface<StationUrl>() {
                            @Override
                            public void onResult(StationUrl stationUrl) {
                                assertNotNull("is null :(", stationUrl);
                                lock.countDown();
                            }

                            @Override
                            public void onError(Throwable t) {
                                assertNull("got an exception", t);
                                lock.countDown();
                            }
                        }, stationsResponseContainer.getContainer().getTuneIn().getBaseM3u(), stationsResponseContainer.getContainer().getStationArray()[0].getId());
                    }

                    @Override
                    public void onError(Throwable t) {
                        assertNull("got an exception", t);
                        lock.countDown();
                    }
                });

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
