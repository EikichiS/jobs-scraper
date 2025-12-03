package com.eikichis.scrapers;


import com.eikichis.model.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LaborumScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {

        String url = "https://www.laborum.cl/empleos-java.html";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Elements cards = doc.select("article");

        List<JobOffer> list = new ArrayList<>();

        for (Element c : cards) {
            String title = c.select("h2").text();
            String company = c.select("h3").text();
            String location = c.select(".sc-jtEBsj").text();
            String link = c.select("a[href]").attr("href");

            if (!link.startsWith("http"))
                link = "https://www.laborum.cl" + link;

            list.add(new JobOffer(title, company, location, link, "Laborum"));
        }

        return list;
    }
}
