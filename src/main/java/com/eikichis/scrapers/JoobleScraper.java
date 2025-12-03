package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoobleScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {
        String url = "https://cl.jooble.org/api/?keywords=java&page=1";

        String json = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0")
                .timeout(20000)
                .get()
                .body()
                .text();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resp = mapper.readValue(json, Map.class);

        List<Map<String, Object>> data = (List<Map<String, Object>>) resp.get("jobs");
        List<JobOffer> list = new ArrayList<>();

        for (Map<String, Object> job : data) {
            list.add(new JobOffer(
                    safe(job.get("title")),
                    safe(job.get("company")),
                    safe(job.get("location")),
                    safe(job.get("link")),
                    "Jooble"
            ));
        }

        return list;
    }

    private String safe(Object o) { return o == null ? "" : o.toString(); }
}
