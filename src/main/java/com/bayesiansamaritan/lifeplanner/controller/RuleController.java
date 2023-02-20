package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.enums.BadHabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.TaskEnum;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.model.Goal.*;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsType;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.*;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Goal.*;
import com.bayesiansamaritan.lifeplanner.response.*;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rule")
public class RuleController {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";
    @Autowired
    private RuleService ruleService;
    @Autowired
    private TaskRuleRepository taskRuleRepository;
    @Autowired
    private HabitRuleRepository habitRuleRepository;
    @Autowired
    private BadHabitRuleRepository badHabitRuleRepository;
    @Autowired
    private SkillRuleRepository skillRuleRepository;
    @Autowired
    private StatRuleRepository statRuleRepository;


    @GetMapping("/completed")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<RuleResponse> getAllCompletedRules(HttpServletRequest request, @RequestParam("goalId") Long goalId) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<RuleResponse> ruleResponses = ruleService.getAllCompletedRules(userId,goalId);
        return ruleResponses;
    };
    @GetMapping("/work")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<RuleResponse> getAllWorkRules(HttpServletRequest request, @RequestParam("goalId") Long goalId) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<RuleResponse> ruleResponses = ruleService.getAllWorkRules(userId,goalId);
        return ruleResponses;
    };

    @GetMapping("/completedPercentage")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Float getCompletedPercentage(HttpServletRequest request, @RequestParam("goalId") Long goalId) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        Float completedPercentage = ruleService.getCompletedPercentage(userId,goalId);
        return completedPercentage;
    };

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addRule(HttpServletRequest request,@RequestBody RuleCreateRequest ruleRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        ruleService.createRule(userId,ruleRequest);
    };

    @GetMapping("/types")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<TypesResponse> getAllTypes(HttpServletRequest request, @RequestParam("type") String type) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, ""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<TypesResponse> typesResponses = ruleService.getAllTypes(userId,type);
        return typesResponses;
    };

    @GetMapping("/names")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<NamesResponse> getAllNames(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("name") String name) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, ""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        List<NamesResponse> namesResponses = ruleService.getAllNames(userId,type,name);
        return namesResponses;
    };

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteRule(HttpServletRequest request, @RequestParam("id") Long id,@RequestParam("ruleType") String ruleType) {
        try{
            if (ruleType.equals("task")){
                taskRuleRepository.deleteById(id);
            }
            else if (ruleType.equals("habit")){
                habitRuleRepository.deleteById(id);
            }
            else if (ruleType.equals("badHabit")){
                badHabitRuleRepository.deleteById(id);
            }
            else if (ruleType.equals("skill")){
                skillRuleRepository.deleteById(id);
            }
            else if (ruleType.equals("stat")){
                statRuleRepository.deleteById(id);
            }
        }catch (Exception e){

        }
    };
}
