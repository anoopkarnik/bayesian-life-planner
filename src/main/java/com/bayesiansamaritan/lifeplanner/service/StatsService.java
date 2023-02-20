package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;

import java.util.List;

public interface StatsService {

    public List<StatsResponse> getAllStats(Long userId, String statsTypeName);
    public List<StatsResponse> getAllStatsAndSubStats(Long userId, String statsTypeName);
    public Stats createRootStats(Long userId, String name, String statsTypeName, Float value, String description);
    public Stats createChildStats(Long userId, String name, String statsTypeName, Float value, String description, String parentName);
}
