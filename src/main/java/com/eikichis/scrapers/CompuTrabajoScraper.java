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
                .timeout(20000)
                .get();

        Elements jobs = doc.select(".bRS.bClick");
        List<JobOffer> list = new ArrayList<>();

        for (Element job : jobs) {
            String title = job.select(".js-o-link.fc_base").text();
            String company = job.select(".it-emp-name").text();
            String location = job.select(".fc_base.mt5").text();
            String link = "https://cl.computrabajo.com" +
                    job.select("a").attr("href");

            list.add(new JobOffer(
                    title, company, location, link,
                    "Computrabajo"
            ));
        }

        return list;
    }
}
