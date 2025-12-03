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
                .header("Accept-Language", "es-CL,es;q=0.8")
                .header("Accept", "text/html")
                .followRedirects(true)
                .timeout(15000)
                .get();

        Elements jobs = doc.select(".result-box-container");
        List<JobOffer> list = new ArrayList<>();

        for (Element job : jobs) {
            String title = job.select("h2").text();
            String company = job.select(".type").text();
            String location = job.select(".location").text();
            String link = job.select("a").attr("href");

            list.add(new JobOffer(
                    title, company, location, link,
                    "Trabajando"
            ));
        }

        return list;
    }
}
