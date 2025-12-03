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
        String url = "https://www.laborum.cl/empleos.html?keyword=java";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(20000)
                .get();

        Elements jobs = doc.select(".sc-lhyNDq");
        List<JobOffer> list = new ArrayList<>();

        for (Element job : jobs) {
            String title = job.select("h2").text();
            String company = job.select("h3").first() != null ? job.select("h3").first().text() : "";
            String location = job.select("h3.sc-jtEBsj").text();
            String link = job.select("a").attr("href");

            list.add(new JobOffer(
                    title, company, location, link,
                    "Laborum"
            ));
        }

        return list;
    }
}
