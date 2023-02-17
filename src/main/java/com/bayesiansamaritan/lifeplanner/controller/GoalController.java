package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.GoalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/goal")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalService goalService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<GoalResponse>> getAllGoal(HttpServletRequest request, @RequestParam("goalTypeName") String goalTypeName) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            List<GoalResponse> goal= goalService.getAllGoals(userId,goalTypeName);
            if (goal.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(goal, HttpStatus.OK);
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
                    goalCreateRootRequest.getGoalTypeName(), goalCreateRootRequest.getTimeTaken());
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
                    goalCreateChildRequest.getGoalTypeName(),goalCreateChildRequest.getTimeTaken(),goalCreateChildRequest.getParentGoalName());
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody GoalDescriptionRequest goalDescriptionRequest)
    {
        goalRepository.addDescription(goalDescriptionRequest.getId(),goalDescriptionRequest.getDescription());
    }

    @PutMapping("/complete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void completeGoal(@RequestParam("id") Long id)
    {
        goalRepository.completeGoal(id);
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        goalRepository.deleteById(id);
    }
}
