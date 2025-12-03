package com.eikichis;

import com.eikichis.model.JobOffer;
import com.eikichis.scrapers.GetOnBoardScraper;
import com.eikichis.services.LaborumScraper;
import com.eikichis.services.TigrisUploader;
import com.eikichis.scrapers.TrabajandoScraper;
import com.eikichis.services.UnifiedScraper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {


        System.out.println("Starting mega scraper...");

        List<JobOffer> all = UnifiedScraper.scrapeAll();

        Map<String, Object> payload = Map.of(
                "scrapedAt", Instant.now().toString(),
                "offers", all
        );

        ObjectMapper mapper = new ObjectMapper();
        byte[] json = mapper.writeValueAsBytes(payload);

        TigrisUploader.upload("latest.json", json);

        System.out.println("Scraper finished OK. Total: " + all.size());
        }
}
