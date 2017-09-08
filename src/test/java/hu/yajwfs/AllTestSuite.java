package hu.yajwfs;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        GetAllGenresTest.class,
        GetPrimaryGenres.class,
        GetSecondaryGenres.class,
        GetStationInfo.class,
        GetStationsByGenre.class,
        GetStationUrl.class,
        SearchStations.class
})
public class AllTestSuite {
}
