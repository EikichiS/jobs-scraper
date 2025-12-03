package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoobleScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {
        String url = "https://cl.jooble.org/SearchResult?rgns=Chile&ukw=java";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Elements cards = doc.select(".result-item");

        List<JobOffer> list = new ArrayList<>();

        for (Element c : cards) {
            String title = c.select(".result-item__title").text();
            String company = c.select(".result-item__subtitle").text();
            String location = c.select(".result-item__location").text();
            String link = c.select("a.result-item__title").attr("href");

            list.add(new JobOffer(title, company, location, link, "Jooble"));
        }

        return list;
    }
}
