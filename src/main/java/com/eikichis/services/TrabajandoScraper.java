package com.eikichis.services;

import com.eikichis.model.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class TrabajandoScraper {
    public static List<JobOffer> scrape() throws Exception {
        String url = "https://www.trabajando.cl/trabajos-busqueda-java.html";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

        List<JobOffer> offers = new ArrayList<>();

        for (Element card : doc.select(".box_trabajos")) {
            String title = card.select(".titulo_oferta").text();
            String company = card.select(".empresa").text();
            String location = card.select(".ubicacion").text();
            String link = "https://www.trabajando.cl" + card.select("a").attr("href");

            offers.add(new JobOffer(title, company, location, link));
        }

        return offers;
    }
}
