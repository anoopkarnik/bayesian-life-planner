package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Stats;
import com.bayesiansamaritan.lifeplanner.model.StatsTranscription;
import com.bayesiansamaritan.lifeplanner.repository.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.StatsTranscriptionRepository;
import com.bayesiansamaritan.lifeplanner.request.StatsCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.StatsDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.request.StatsValueRequest;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;
import com.bayesiansamaritan.lifeplanner.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stats")
public class StatsController {
    @Autowired
    private StatsTranscriptionRepository statsTranscriptionRepository;
    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private StatsService statsService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<StatsResponse>> getAllStats(@RequestParam("userId") Long userId, @RequestParam("statsTypeName") String statsTypeName) {
        try {
            List<StatsResponse> stats= statsService.getAllStats(userId,statsTypeName);
            if (stats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Stats> createStats(@RequestBody StatsCreateRequest statsCreateRequest)
    {
        try {
            Stats stats = statsService.createStats(statsCreateRequest.getUserId(),statsCreateRequest.getName(),statsCreateRequest.getStatsTypeName(),
                    statsCreateRequest.getValue(),statsCreateRequest.getDescription());
            statsTranscriptionRepository.save(new StatsTranscription(stats.getName(),stats.getStatsTypeId(),stats.getUserId(),
                    stats.getValue(),stats.getDescription()));
            return new ResponseEntity<>(stats, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody StatsDescriptionRequest statsDescriptionRequest)
    {
        statsRepository.addDescription(statsDescriptionRequest.getId(),statsDescriptionRequest.getDescription());
    }

    @PatchMapping("/value")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addValue(@RequestBody StatsValueRequest statsValueRequest)
    {
        statsRepository.addValue(statsValueRequest.getId(),statsValueRequest.getValue());
        Optional<Stats> stats = statsRepository.findById(statsValueRequest.getId());
        statsTranscriptionRepository.save(new StatsTranscription(stats.get().getName(),stats.get().getStatsTypeId(),stats.get().getUserId(),
                stats.get().getValue(),stats.get().getDescription()));
    }



    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        statsRepository.deleteById(id);
    }
}
