package com.notifyme.crawlers;

import java.util.List;
import com.notifyme.crawlers.custom.TanieLotyCrawler;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

public class TanieLotyCrawlerTest
{

    private TanieLotyCrawler tanieLotyCrawler = new TanieLotyCrawler();

    @Test
    public void testTanieLotyPageE2E()
    {
        // when
        String URL = "http://tanie-loty.com.pl/blogi/promocje-loty";

        // then
        List<RetrievedData> offers = tanieLotyCrawler.getDataByLink(URL, 5);

        //verify
        Assert.assertThat(offers.size(), is(5));
    }
}
