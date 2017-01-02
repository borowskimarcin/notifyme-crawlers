package com.notifyme.crawlers.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.notifyme.crawlers.Crawler;
import com.notifyme.crawlers.HttpGetQueryExecutorWithJsoup;
import com.notifyme.crawlers.RetrievedData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/***
 * @author Marcin Borowski
 *
 * We want to implement custom crawling for http://tanie-loty.com.pl/blogi/promocje-loty page
 *
 * ClassName of crawler should have convention:
 * PageNameCraweler.class
 *
 */

public class TanieLotyCrawler implements Crawler
{
    private final Log log = LogFactory.getLog(getClass());

    @Override
    public List<RetrievedData> getDataByLink(String url, int maxOffersNumber)
    {
        log.debug("Crawler started for url: " + url + " and offer limit: " + maxOffersNumber);
        Document page = new HttpGetQueryExecutorWithJsoup().execute(url);

        return page.select("div.eb-post").stream()
                .map(this::retrieveData)
                .limit(maxOffersNumber)
                .collect(Collectors.toList());
    }

    private RetrievedData retrieveData(Element offerElement)
    {
        return new RetrievedData()
                .withName(offerElement.select("h2").select("a").get(0).childNode(0).toString())
                .withDescription(offerElement.select("div.ebd-block").select("p").get(0).toString())
                .withImage(offerElement.select("img").attr("src"))
                .withUrl("http://" + getDomainAddress() + offerElement.select("h2").select("a").attr("href"));
    }

    @Override
    public String getDomainAddress()
    {
        return "tanie-loty.com.pl";
    }
}
