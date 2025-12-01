package com.eikichis.services;

import com.eikichis.model.JobOffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetOnBoardScraper {
    public static List<JobOffer> scrape() throws Exception {
        String url = "https://www.getonbrd.com/api/v0/search/jobs?query=java";
        String json = Jsoup.connect(url)
                .ignoreContentType(true)
                .timeout(15000)
                .get()
                .body()
                .text();

        // Aquí parseas JSON de la API pública
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(json, Map.class);

        List<Map<String,Object>> data = (List<Map<String,Object>>) map.get("data");
        List<JobOffer> offers = new ArrayList<>();

        for (var item : data) {
            Map<String,Object> attrs = (Map<String,Object>) item.get("attributes");
            offers.add(new JobOffer(
                    (String) attrs.get("title"),
                    (String) attrs.get("company_name"),
                    (String) attrs.get("country"),
                    (String) attrs.get("url")
            ));
        }

        return offers;
    }
}
