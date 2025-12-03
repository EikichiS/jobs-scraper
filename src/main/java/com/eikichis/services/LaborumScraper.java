package com.eikichis.services;

import com.eikichis.model.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class LaborumScraper {
    public static List<JobOffer> scrape() throws Exception {
        String url = "https://www.laborum.cl/empleos-busqueda-java.html";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        List<JobOffer> results = new ArrayList<>();

        for (Element offer : doc.select(".sc-aXZVg.job-card")) {
            String title = offer.select(".job-card__title").text();
            String company = offer.select(".job-card__company").text();
            String location = offer.select(".job-card__location").text();
            String link = "https://www.laborum.cl" + offer.select("a").attr("href");

            results.add(new JobOffer(title, company, location, link));
        }

        return results;
    }
}
