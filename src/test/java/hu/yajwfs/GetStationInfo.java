package hu.yajwfs;


import hu.yajwfs.model.GenreList;
import hu.yajwfs.model.StationInfo;
import hu.yajwfs.model.StationUrl;
import hu.yajwfs.model.StationsResponseContainer;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GetStationInfo extends UnitTestBase {

    @Test
    public void getStationInfo() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);
        mShoutCastApi.getPrimaryGenres(new ShoutCastApi.ApiManagerInterface<GenreList>() {
            @Override
            public void onResult(GenreList genreList) {
                mShoutCastApi.getStationsByGenre(genreList.getGenreList()[4].getName(), new ShoutCastApi.ApiManagerInterface<StationsResponseContainer>() {
                    @Override
                    public void onResult(StationsResponseContainer stationsResponseContainer) {
                        assertNotNull("is null :(", stationsResponseContainer);
                        mShoutCastApi.getStationUrl(new ShoutCastApi.ApiManagerInterface<StationUrl>() {
                            @Override
                            public void onResult(StationUrl stationUrl) {
                                assertNotNull("is null :(", stationUrl);

                                StationInfo stationInfo = null;
                                try {
                                    stationInfo = mShoutCastApi.getStationInfo(stationUrl.getUrls().get(0));
                                } catch (IOException e) {
                                    assertNull(e);
                                }
                                assertNotNull("is null :(", stationInfo);
                                System.out.println(stationInfo);
                                lock.countDown();
                            }

                            @Override
                            public void onError(Throwable t) {
                                assertNull("got an exception", t);
                                lock.countDown();
                            }
                        }, stationsResponseContainer.getContainer().getTuneIn().getBaseM3u(), stationsResponseContainer.getContainer().getStationArray()[1].getId());
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
