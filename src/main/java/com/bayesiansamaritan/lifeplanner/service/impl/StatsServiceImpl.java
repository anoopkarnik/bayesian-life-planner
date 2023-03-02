package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Stats.*;
import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsType;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;
import com.bayesiansamaritan.lifeplanner.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class
StatsServiceImpl implements StatsService {

    @Autowired
    StatsRepository statsRepository;

    @Autowired
    StatsTypeRepository statsTypeRepository;
    @Autowired
    private StatsTransactionRepository statsTransactionRepository;

    @Override
    public List<StatsResponse> getAllStats(Long userId, String statsTypeName){

        StatsType statsType = statsTypeRepository.findByNameAndUserId(statsTypeName,userId);
        List<Stats> statList = statsRepository. findRootStatsByUserIdAndStatsTypeId(userId,statsType.getId());
        List<StatsResponse> statsResponses = new ArrayList<>();
        for (Stats stats: statList){
            Optional<List<Stats>> childStatsList1=  statsRepository.findChildStatsByUserIdAndParentStatsId(userId,stats.getId());
            StatsResponse statsResponse = new StatsResponse(stats.getId(),stats.getCreatedAt(),
                    stats.getUpdatedAt(),stats.getName(),statsType.getName(),stats.getValue(),
                    stats.getDescription(),stats.getActive(),stats.getHidden(),stats.getCompleted());
            if (childStatsList1.isPresent()){
                List<StatsResponse> childStatsResponses1 = new ArrayList<>();
                for(Stats childStats1 : childStatsList1.get()) {
                    Optional<StatsType> childStatsType1 = statsTypeRepository.findById(childStats1.getStatsTypeId());
                    StatsResponse childStatsResponse1 = new StatsResponse(childStats1.getId(),childStats1.getCreatedAt(),
                            childStats1.getUpdatedAt(),childStats1.getName(),childStatsType1.get().getName(),
                            childStats1.getValue(),childStats1.getDescription(),childStats1.getActive(),childStats1.getHidden(),childStats1.getCompleted());
                    childStatsResponses1.add(childStatsResponse1);
                }
                statsResponse.setStatsResponses(childStatsResponses1);
            }
            statsResponses.add(statsResponse);
        }
        return statsResponses;
    };

    @Override
    public List<StatsResponse> getAllStatsAndSubStats(Long userId, String statsTypeName){

        StatsType statsType = statsTypeRepository.findByNameAndUserId(statsTypeName,userId);
        List<Stats> statList = statsRepository. findRootStatsByUserIdAndStatsTypeId(userId,statsType.getId());
        List<StatsResponse> statsResponses = new ArrayList<>();
        for (Stats stats: statList){
            Optional<List<Stats>> childStatsList1=  statsRepository.findChildStatsByUserIdAndParentStatsId(userId,stats.getId());
            StatsResponse statsResponse = new StatsResponse(stats.getId(),stats.getCreatedAt(),
                    stats.getUpdatedAt(),stats.getName(),statsType.getName(),stats.getValue(),
                    stats.getDescription(),stats.getActive(),stats.getHidden(),stats.getCompleted());
            if (childStatsList1.isPresent()){
                for(Stats childStats1 : childStatsList1.get()) {
                    Optional<StatsType> childStatsType1 = statsTypeRepository.findById(childStats1.getStatsTypeId());
                    StatsResponse childStatsResponse1 = new StatsResponse(childStats1.getId(),childStats1.getCreatedAt(),
                            childStats1.getUpdatedAt(),childStats1.getName(),childStatsType1.get().getName(),
                            childStats1.getValue(),childStats1.getDescription(),childStats1.getActive(),childStats1.getHidden(),
                            childStats1.getCompleted());
                    statsResponses.add(childStatsResponse1);
                }
            }
            statsResponses.add(statsResponse);
        }
        return statsResponses;
    };
    @Override
    public Stats createRootStats(Long userId, String name, String statsTypeName, Float value, String description, Boolean active){
        StatsType statsType = statsTypeRepository.findByNameAndUserId(statsTypeName,userId);
        Stats stats = statsRepository.save(new Stats(name,statsType.getId(),userId,value,description,active));
        statsTransactionRepository.save(new StatsTransaction(stats.getName(),stats.getStatsTypeId(),stats.getUserId(),stats.getValue(),
                stats.getDescription(),stats.getId()));
        return stats;
    };

    @Override
    public Stats createChildStats(Long userId, String name, String statsTypeName, Float value, String description, String parentName, Boolean active){
        StatsType statsType = statsTypeRepository.findByNameAndUserId(statsTypeName,userId);
        Stats oldStats = statsRepository.findByUserIdAndName(userId,parentName);
        Stats stats = statsRepository.save(new Stats(name,statsType.getId(),userId,value,description,oldStats.getId(),active));
        statsTransactionRepository.save(new StatsTransaction(stats.getName(),stats.getStatsTypeId(),stats.getUserId(),stats.getValue(),
                stats.getDescription(),stats.getId(),stats.getParentId()));
        return stats;
    };
}
