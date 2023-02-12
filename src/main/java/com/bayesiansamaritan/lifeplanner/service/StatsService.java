package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Journal;
import com.bayesiansamaritan.lifeplanner.model.Stats;
import com.bayesiansamaritan.lifeplanner.response.JournalResponse;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;

import java.util.List;

public interface StatsService {

    public List<StatsResponse> getAllStats(Long userId, String statsTypeName);
    public Stats createStats(Long userId, String name, String statsTypeName, Float value, String description);
}
