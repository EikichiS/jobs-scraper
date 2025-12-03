package com.eikichis.services;

import com.eikichis.model.JobOffer;
import com.eikichis.scrapers.*;
import com.eikichis.scrapers.LaborumScraper;

import java.util.ArrayList;
import java.util.List;

public class UnifiedScraper {

    public static List<JobOffer> scrapeAll() {
        List<JobOffer> all = new ArrayList<>();

        List<JobScraper> scrapers = List.of(
                new GetOnBoardScraper(),
                new ChileTrabajosScraper(),
                new JoobleScraper(),
                new TrabajandoScraper(),
                new CompuTrabajoScraper(),
                new LaborumScraper()
        );

        for (JobScraper s : scrapers) {
            try {
                all.addAll(s.scrape());
            } catch (Exception e) {
                System.out.println("Error en " + s.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        return all;
    }
}
