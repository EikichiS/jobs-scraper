package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
public class CompuTrabajoScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {
        String url = "https://cl.computrabajo.com/trabajo-de-java";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        Elements jobs = doc.select(".bJS");

        List<JobOffer> list = new ArrayList<>();

        for (Element j : jobs) {
            String title = j.select("a.js-o-link.fc_base").text();
            String company = j.select(".fc_aux").text();
            String location = j.select(".fc_aux.t_small").text();
            String link = j.select("a").attr("href");

            if (!link.startsWith("http"))
                link = "https://cl.computrabajo.com" + link;

            list.add(new JobOffer(title, company, location, link, "CompuTrabajo"));
        }

        return list;
    }
}
