package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.scraping.ScrapingBean;
import com.ebc.ecard.application.scraping.dto.ScrapingUpdateDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScrapingMapper {
    ScrapingBean findScrapingByScrapingId(String scrapingId);
    Integer saveScrapingData(ScrapingBean scraping);
    Integer updateScrapingResult(ScrapingUpdateDto scraping);
}
