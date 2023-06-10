package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.CriteriaSet;
import com.bayesiansamaritan.lifeplanner.model.Rule.Rule;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Rule.CriteriaRepository;
import com.bayesiansamaritan.lifeplanner.repository.Rule.CriteriaSetRepository;
import com.bayesiansamaritan.lifeplanner.repository.Rule.RuleRepository;
import com.bayesiansamaritan.lifeplanner.repository.Rule.RuleSetRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalModifyRequest;
import com.bayesiansamaritan.lifeplanner.request.Rule.AddRuleRequest;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;
import com.bayesiansamaritan.lifeplanner.response.RuleEngineResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.GoalService;
import com.bayesiansamaritan.lifeplanner.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/goal")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private GoalTypeRepository goalTypeRepository;

    @Autowired
    private GoalService goalService;
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
    @Autowired
    DateUtils dateUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public GoalResponse getGoal(@PathVariable("id") Long id) {
        Goal goal = goalRepository.findById(id).get();
        GoalType goalType = goalTypeRepository.findById(goal.getGoalTypeId()).get();
        GoalResponse goalResponse = new GoalResponse(goal.getId(),goal.getCreatedAt(),goal.getUpdatedAt(),
                goal.getName(),goal.getDueDate(),goalType.getName(),goal.getDescription(), goal.getCompletedPercentage(),
                goal.getWorkPercentage(),goal.getStartDate(),
                goal.getTimeTaken(),goal.getActive(),goal.getHidden(),goal.getCompleted());
        return goalResponse;
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<GoalResponse>> getAllGoal(HttpServletRequest request, @RequestParam("goalTypeName") String goalTypeName,
                                                         @RequestParam("active") Boolean active) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            List<GoalResponse> goal= goalService.getAllGoals(userId,active,goalTypeName);
            if (goal.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(goal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<RuleEngineResponse> getWorkRule(@RequestParam("id") Long id,@RequestParam("ruleType") String ruleType) {
        Optional<Goal> goal = goalRepository.findById(id);
        try {
            String str;
            if(ruleType.equals("Work")){
                str = goal.get().getWorkRuleEngineReference();
            }
            else{
                str = goal.get().getCompletedRuleEngineReference();
            }
            String[] arrOfStr = str.split("/");
            String ruleReferenceType = arrOfStr[0];
            String ruleReferenceId = arrOfStr[1];
            RuleEngineResponse ruleEngineResponse = new RuleEngineResponse();
            if(ruleReferenceType.equals("Criteria")) {
                Optional<Criteria> criteria = criteriaRepository.findById(Long.valueOf(ruleReferenceId));
                ruleEngineResponse.setId(criteria.get().getId());
                ruleEngineResponse.setName(criteria.get().getName());
            } else if (ruleReferenceType.equals("Criteria Set")) {
                Optional<CriteriaSet> criteria = criteriaSetRepository.findById(Long.valueOf(ruleReferenceId));
                ruleEngineResponse.setId(criteria.get().getId());
                ruleEngineResponse.setName(criteria.get().getName());
            }else if (ruleReferenceType.equals("Rule")) {
                Optional<Rule> criteria = ruleRepository.findById(Long.valueOf(ruleReferenceId));
                ruleEngineResponse.setId(criteria.get().getId());
                ruleEngineResponse.setName(criteria.get().getName());
            }else if (ruleReferenceType.equals("Rule Set")) {
                Optional<RuleSet> criteria = ruleSetRepository.findById(Long.valueOf(ruleReferenceId));
                ruleEngineResponse.setId(criteria.get().getId());
                ruleEngineResponse.setName(criteria.get().getName());
            }
            return new ResponseEntity<>(ruleEngineResponse , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/root")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Goal> createRootGoal(HttpServletRequest request,@RequestBody GoalCreateRootRequest goalCreateRootRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Goal goal = goalService.createRootGoal(userId, goalCreateRootRequest.getName(),
                    goalCreateRootRequest.getGoalTypeName(), goalCreateRootRequest.getDueDate(),goalCreateRootRequest.getActive());
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Goal> createChildGoal(HttpServletRequest request,@RequestBody GoalCreateChildRequest goalCreateChildRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Goal goal = goalService.createChildGoal(userId, goalCreateChildRequest.getName(),
                    goalCreateChildRequest.getGoalTypeName(),goalCreateChildRequest.getDueDate(),goalCreateChildRequest.getParentGoalName(),
                    goalCreateChildRequest.getActive());
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/addCompletedRule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addCompletedRule(@RequestBody AddRuleRequest addRuleRequest)
    {
        goalRepository.addCompletedRule(addRuleRequest.getId(),addRuleRequest.getRuleEngineReference());
    }

    @PatchMapping("/addWorkRule")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addWorkRule(@RequestBody AddRuleRequest addRuleRequest)
    {
        goalRepository.addWorkRule(addRuleRequest.getId(),addRuleRequest.getRuleEngineReference());
    }

    @DeleteMapping("/completedRule/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void completedRule(@PathVariable Long id)
    {
        goalRepository.addCompletedRule(id,null);
    }

    @DeleteMapping("/workRule/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void workRule(@PathVariable Long id)
    {
        goalRepository.addWorkRule(id,null);
    }

    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody GoalModifyRequest goalModifyRequest)
    {
        Date newDueDate = dateUtils.getEndOfDay(goalModifyRequest.getDueDate());
        goalRepository.modifyParams(goalModifyRequest.getId(),goalModifyRequest.getName(),goalModifyRequest.getStartDate(),goalModifyRequest.getDescription(),
                goalModifyRequest.getActive(),goalModifyRequest.getHidden(),goalModifyRequest.getCompleted(),newDueDate,
                goalModifyRequest.getTimeTaken());
    }

    @PutMapping("/complete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void completeGoal(@RequestParam("id") Long id)
    {
        goalRepository.completeGoal(id,true);
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        goalRepository.deleteById(id);
    }
}
