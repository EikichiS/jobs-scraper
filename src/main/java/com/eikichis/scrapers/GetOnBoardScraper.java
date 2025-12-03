package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetOnBoardScraper implements JobScraper {

    @Override
    public List<JobOffer> scrape() throws Exception {
        String url = "https://www.getonbrd.com/api/v0/search/jobs?query=java";

        String json = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get()
                .body()
                .text();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);

        List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
        List<JobOffer> offers = new ArrayList<>();

        for (var item : data) {
            Map<String, Object> attrs = (Map<String, Object>) item.get("attributes");
            Map<String, Object> job = (Map<String, Object>) attrs.get("job");

            offers.add(new JobOffer(
                    safe(job.get("title")),
                    safe(job.get("company_name")),
                    safe(job.get("country")),
                    safe(job.get("url")),
                    "GetOnBoard"
            ));
        }

        return offers;
    }

    private String safe(Object o) { return o == null ? "" : o.toString(); }
}
