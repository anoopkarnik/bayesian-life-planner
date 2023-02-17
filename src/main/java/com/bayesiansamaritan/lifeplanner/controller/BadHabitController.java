package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.BadHabit.BadHabitCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.BadHabit.BadHabitCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.BadHabit.BadHabitDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.BadHabitService;
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
@RequestMapping("/api/badHabit")
public class BadHabitController {
    @Autowired
    private BadHabitRepository habitRepository;

    @Autowired
    BadHabitService habitService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<BadHabitResponse>> getAllBadHabits(HttpServletRequest request, @RequestParam("habitTypeName") String habitTypeName) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
            Long userId = userProfileRepository.findByName(username).get().getId();
            List<BadHabitResponse> habits = habitService.getAllActiveBadHabits(userId,true,habitTypeName);
            if (habits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(habits , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BadHabit> createChildBadHabit(HttpServletRequest request,@RequestBody BadHabitCreateChildRequest habitCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
             BadHabit habit = habitService.createChildBadHabit(userId,habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                        habitCreateRequest.getHabitTypeName(),habitCreateRequest.getParentBadHabitName());
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/root")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BadHabit> createRootHabit(HttpServletRequest request,@RequestBody BadHabitCreateRootRequest habitCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            BadHabit habit = habitService.createRootBadHabit(userId,habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                    habitCreateRequest.getHabitTypeName());
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BadHabit> carriedOutBadHabit(HttpServletRequest request,@RequestParam("id") Long id)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            BadHabit habit = habitService.carriedOutBadHabit(userId,id);
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody BadHabitDescriptionRequest habitDescriptionRequest)
    {
        habitRepository.addDescription(habitDescriptionRequest.getId(),habitDescriptionRequest.getDescription());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        habitRepository.deleteById(id);
    }
}
