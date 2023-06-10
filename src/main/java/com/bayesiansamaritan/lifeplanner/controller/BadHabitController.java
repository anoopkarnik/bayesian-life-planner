package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.BadHabit.BadHabitCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.BadHabit.BadHabitCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.BadHabit.BadHabitModifyRequest;
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
    private BadHabitTypeRepository badHabitTypeRepository;

    @Autowired
    BadHabitService habitService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public BadHabitResponse getBadHabit(@PathVariable("id") Long id) {
        BadHabit badHabit = habitRepository.findById(id).get();
        BadHabitType badHabitType = badHabitTypeRepository.findById(badHabit.getBadHabitTypeId()).get();
        BadHabitResponse badHabitResponse = new BadHabitResponse(badHabit.getId(),badHabit.getCreatedAt(),
                badHabit.getUpdatedAt(),badHabit.getName(),badHabit.getStartDate(),badHabitType.getName(),
                badHabit.getTotalTimes(),badHabit.getDescription(),badHabit.getActive(),badHabit.getHidden(),
                badHabit.getCompleted());
        return badHabitResponse;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<BadHabitResponse>> getAllBadHabits(HttpServletRequest request, @RequestParam("habitTypeName") String habitTypeName,
                                                                  @RequestParam("active") Boolean active) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
            Long userId = userProfileRepository.findByName(username).get().getId();
            List<BadHabitResponse> habits = habitService.getAllBadHabits(userId,active,habitTypeName);
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
                        habitCreateRequest.getBadHabitTypeName(),habitCreateRequest.getParentBadHabitName(),habitCreateRequest.getActive());
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
                    habitCreateRequest.getBadHabitTypeName(),habitCreateRequest.getActive());
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

    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody BadHabitModifyRequest badHabitModifyRequest )
    {
        habitRepository.modifyParams(badHabitModifyRequest.getId(), badHabitModifyRequest.getName(),badHabitModifyRequest.getStartDate(),
                badHabitModifyRequest.getDescription(),badHabitModifyRequest.getActive(),badHabitModifyRequest.getHidden(),badHabitModifyRequest.getCompleted(),
                badHabitModifyRequest.getTotalTimes());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        habitRepository.deleteById(id);
    }
}
