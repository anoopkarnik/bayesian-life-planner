package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.CriteriaSet;
import com.bayesiansamaritan.lifeplanner.model.Rule.Rule;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import com.bayesiansamaritan.lifeplanner.repository.Rule.*;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Rule.*;
import com.bayesiansamaritan.lifeplanner.response.*;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.RuleEngineService;
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

    @PatchMapping("/criteria")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody CriteriaRequest criteriaRequest)
    {
        criteriaRepository.modifyParams(criteriaRequest.getId(),criteriaRequest.getName(),criteriaRequest.getCriteriaType(),
                criteriaRequest.getCondition(),criteriaRequest.getCategory(),criteriaRequest.getWeightage(),criteriaRequest.getValue(),
                criteriaRequest.getCategoryName());
    }
    @PatchMapping("/criteriaSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(HttpServletRequest request, @RequestBody CriteriaSetModifyRequest criteriaSetModifyRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        criteriaSetModifyRequest.setUserId(userId);
        ruleEngineService.modifyCriteriaSet(criteriaSetModifyRequest);
    }
    @PatchMapping("/rule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(HttpServletRequest request, @RequestBody RulesModifyRequest ruleModifyRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        ruleModifyRequest.setUserId(userId);
        ruleEngineService.modifyRule(ruleModifyRequest);
    }
    @PatchMapping("/ruleSet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(HttpServletRequest request, @RequestBody RuleSetModifyRequest ruleSetModifyRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        ruleSetModifyRequest.setUserId(userId);
        ruleEngineService.modifyRuleSet(ruleSetModifyRequest);
    }

    @GetMapping("/types")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<TypesResponse> getAllTypes(HttpServletRequest request, @RequestParam("type") String type) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, ""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<TypesResponse> typesResponses = ruleEngineService.getAllTypes(userId,type);
        return typesResponses;
    };

    @GetMapping("/names")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<NamesResponse> getAllNames(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("name") String name) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, ""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<NamesResponse> namesResponses = ruleEngineService.getAllNames(userId,type,name);
        return namesResponses;
    };

//    @GetMapping("/completedPercentage")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public Float getCompletedPercentage(HttpServletRequest request, @RequestParam("goalId") Long goalId) {
//        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
//        Long userId = userProfileRepository.findByName(username).get().getId();
//        Float completedPercentage = ruleEngineService.getCompletedPercentage(userId,goalId);
//        return completedPercentage;
//    };
//
//    @GetMapping("/workPercentage")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public Float getWorkPercentage(HttpServletRequest request, @RequestParam("goalId") Long goalId) {
//        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
//        Long userId = userProfileRepository.findByName(username).get().getId();
//        Float workPercentage = ruleEngineService.getWorkPercentage(userId,goalId);
//        return workPercentage;
//    };
}
