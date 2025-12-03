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

        Elements jobs = doc.select(".job-item");
        List<JobOffer> list = new ArrayList<>();

        for (Element job : jobs) {
            String title = job.select("h2.title a").text();
            String company = job.select("h3.meta").first() != null
                    ? job.select("h3.meta").first().text().split(",")[0]
                    : "";
            String location = job.select("h3.meta a").text();
            String link = "https://www.chiletrabajos.cl" +
                    job.select("h2.title a").attr("href");

            list.add(new JobOffer(
                    title, company, location, link,
                    "ChileTrabajos"
            ));
        }

        return list;
    }
}

