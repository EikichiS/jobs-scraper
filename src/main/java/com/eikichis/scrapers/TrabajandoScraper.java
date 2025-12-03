package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TrabajandoScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {

        String url = "https://www.trabajando.cl/trabajo-empleo/";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Elements boxes = doc.select(".result-box-container");

        List<JobOffer> offers = new ArrayList<>();

        for (Element box : boxes) {
            String title = box.select("h2").text();
            String company = box.select(".type").text();
            String location = box.select(".location").text();
            String link = box.select("a[href]").attr("href");

            if (!link.startsWith("http"))
                link = "https://www.trabajando.cl" + link;

            offers.add(new JobOffer(title, company, location, link, "Trabajando"));
        }

        return offers;
    }
}
