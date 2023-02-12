package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Stats;
import com.bayesiansamaritan.lifeplanner.model.StatsType;
import com.bayesiansamaritan.lifeplanner.repository.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;
import com.bayesiansamaritan.lifeplanner.service.StatsService;
import com.bayesiansamaritan.lifeplanner.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    StatsRepository statsRepository;

    @Autowired
    StatsTypeRepository statsTypeRepository;

    @Override
    public List<StatsResponse> getAllStats(Long userId, String statsTypeName){

        StatsType statsType = statsTypeRepository.findByNameAndUserId(statsTypeName,userId);
        List<Stats> stats= statsRepository.findByUserIdAndStatsTypeId(userId,statsType.getId());
        List<StatsResponse> statsResponses = new ArrayList<>();
        for (Stats stat: stats){
            StatsResponse statsResponse = new StatsResponse(stat.getId(),stat.getCreatedAt(),stat.getUpdatedAt(),stat.getName(),statsTypeName,
                    stat.getValue(),stat.getDescription());
            statsResponses.add(statsResponse);
        }
        return statsResponses;
    };

    @Override
    public Stats createStats(Long userId, String name, String statsTypeName, Float value, String description){
        StatsType statsType = statsTypeRepository.findByNameAndUserId(statsTypeName,userId);
        Stats stats = statsRepository.save(new Stats(name,statsType.getId(),userId,value,description));
        return stats;
    };
}
