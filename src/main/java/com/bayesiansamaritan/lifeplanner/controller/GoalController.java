package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Goal.GoalModifyRequest;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;
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
    @Autowired
    DateUtils dateUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";


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
