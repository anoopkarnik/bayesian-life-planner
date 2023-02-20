package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsTransaction;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Stats.StatsCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Stats.StatsCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Stats.StatsDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.request.Stats.StatsValueRequest;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stats")
public class StatsController {
    @Autowired
    private StatsTransactionRepository statsTransactionRepository;
    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private StatsService statsService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<StatsResponse>> getAllStats(HttpServletRequest request, @RequestParam("statsTypeName") String statsTypeName) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
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

    @PostMapping("/root")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Stats> createRootStats(HttpServletRequest request,@RequestBody StatsCreateRootRequest statsCreateRootRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Stats stats = statsService.createRootStats(userId,statsCreateRootRequest.getName(),
                    statsCreateRootRequest.getStatsTypeName(),statsCreateRootRequest.getValue(),statsCreateRootRequest.getDescription());
            statsTransactionRepository.save(new StatsTransaction(stats.getName(),stats.getStatsTypeId(),stats.getUserId(),
                    stats.getValue(),stats.getDescription(),stats.getId()));
            return new ResponseEntity<>(stats, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Stats> createChildStats(HttpServletRequest request,@RequestBody StatsCreateChildRequest statsCreateChildRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Stats stats = statsService.createChildStats(userId, statsCreateChildRequest.getName(), statsCreateChildRequest.getStatsTypeName(),
                    statsCreateChildRequest.getValue(), statsCreateChildRequest.getDescription(),statsCreateChildRequest.getParentStatsName());
            statsTransactionRepository.save(new StatsTransaction(stats.getName(),stats.getStatsTypeId(),stats.getUserId(),
                    stats.getValue(),stats.getDescription(),stats.getId()));
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
        statsTransactionRepository.save(new StatsTransaction(stats.get().getName(),stats.get().getStatsTypeId(),stats.get().getUserId(),
                stats.get().getValue(),stats.get().getDescription(),stats.get().getId()));
    }



    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        statsRepository.deleteById(id);
    }
}
