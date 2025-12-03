package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
public class ChileTrabajosScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {
        String url = "https://www.chiletrabajos.cl/encuentra-un-empleo";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Elements cards = doc.select(".job-item");

        List<JobOffer> offers = new ArrayList<>();

        for (Element offer : cards) {
            String title = offer.select("h2.title a").text();
            String company = offer.select("h3.meta").first() != null
                    ? offer.select("h3.meta").first().text().split(",")[0].trim()
                    : "Sin empresa";

            String location = offer.select("h3.meta a").text();
            String link = offer.select("h2.title a").attr("href");

            if (!link.startsWith("http")) {
                link = "https://www.chiletrabajos.cl" + link;
            }

            offers.add(new JobOffer(title, company, location, link, "ChileTrabajos"));
        }

        return offers;
    }
}
