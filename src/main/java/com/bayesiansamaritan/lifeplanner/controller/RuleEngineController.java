package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.CriteriaSet;
import com.bayesiansamaritan.lifeplanner.model.Rule.Rule;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import com.bayesiansamaritan.lifeplanner.repository.Rule.*;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Rule.*;
import com.bayesiansamaritan.lifeplanner.response.*;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.RuleEngineService;
import com.bayesiansamaritan.lifeplanner.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ruleEngine")
public class RuleEngineController {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private CriteriaRepository criteriaRepository;
    @Autowired
    private CriteriaSetRepository criteriaSetRepository;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private RuleSetRepository ruleSetRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    private RuleEngineService ruleEngineService;

    @GetMapping("/criteria")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<CriteriaResponse> getAllCriteria(HttpServletRequest request,@RequestParam("type") CriteriaEnum criteriaType) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<CriteriaResponse> criteriaList = ruleEngineService.getAllCriteria(userId, criteriaType);
        return criteriaList;
    };

    @GetMapping("/criteriaSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<CriteriaSet> getAllCriteriaSet(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<CriteriaSet> criteriaSetResponseList = ruleEngineService.getAllCriteriaSet(userId);
        return  criteriaSetResponseList;
    };

    @GetMapping("/rule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Rule> getAllRule(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<Rule> ruleResponseList = ruleEngineService.getAllRules(userId);
        return ruleResponseList;
    };

    @GetMapping("/ruleSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<RuleSet> getAllRuleSet(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<RuleSet> ruleSetResponseList = ruleEngineService.getAllRuleSets(userId);
        return ruleSetResponseList;
    };

    @PostMapping("/criteria")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createCriteria(HttpServletRequest request,@RequestBody CriteriaRequest criteriaRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        criteriaRequest.setUserId(userId);
        ruleEngineService.createCriteria(criteriaRequest);
    };

    @PostMapping("/criteriaSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createCriteriaSet(HttpServletRequest request, @RequestBody CriteriaSetRequest criteriaSetRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        criteriaSetRequest.setUserId(userId);
        ruleEngineService.createCriteriaSet(criteriaSetRequest);
    };

    @PostMapping("/rule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createRule(HttpServletRequest request, @RequestBody RuleRequest ruleRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        ruleRequest.setUserId(userId);
        ruleEngineService.createRule(ruleRequest);
    };

    @PostMapping("/ruleSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createRuleSet(HttpServletRequest request, @RequestBody RuleSetRequest ruleSetRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        ruleSetRequest.setUserId(userId);
        ruleEngineService.createRuleSet(ruleSetRequest);
    };

    @DeleteMapping("/criteria")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteCriteria(@RequestParam("id") Long id){
        try {
            criteriaRepository.deleteById(id);
        } catch (Exception e) {

        }
    }

    @DeleteMapping("/criteriaSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteCriteriaSet(@RequestParam("id") Long id){
        try {
            criteriaSetRepository.deleteById(id);
        } catch (Exception e) {

        }
    }
    @DeleteMapping("/rule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteRule(@RequestParam("id") Long id){
        try {
            ruleRepository.deleteById(id);
        } catch (Exception e) {

        }
    }
    @DeleteMapping("/ruleSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteRuleSet(@RequestParam("id") Long id){
        try {
            ruleSetRepository.deleteById(id);
        } catch (Exception e) {

        }
    }
}
