package com.eikichis.scrapers;

import com.eikichis.model.JobOffer;

import java.util.List;

public interface JobScraper {
    List<JobOffer> scrape() throws Exception;
}
