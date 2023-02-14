package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Skill;
import com.bayesiansamaritan.lifeplanner.repository.SkillRepository;
import com.bayesiansamaritan.lifeplanner.request.SkillCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.SkillCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.SkillDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.service.SkillService;
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
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillService skillService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<SkillResponse>> getAllSkill(@RequestParam("userId") Long userId, @RequestParam("skillTypeName") String skillTypeName) {
        try {
            List<SkillResponse> skill= skillService.getAllSkills(userId,skillTypeName);
            if (skill.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(skill, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/root")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Skill> createRootSkill(@RequestBody SkillCreateRootRequest skillCreateRootRequest)
    {
        try {
            Skill skill = skillService.createRootSkill(skillCreateRootRequest.getUserId(), skillCreateRootRequest.getName(),
                    skillCreateRootRequest.getSkillTypeName(), skillCreateRootRequest.getTimeTaken());
            return new ResponseEntity<>(skill, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Skill> createChildSkill(@RequestBody SkillCreateChildRequest skillCreateChildRequest)
    {
        try {
            Skill skill = skillService.createChildSkill(skillCreateChildRequest.getUserId(), skillCreateChildRequest.getName(),
                    skillCreateChildRequest.getSkillTypeName(),skillCreateChildRequest.getTimeTaken(),skillCreateChildRequest.getParentSkillName());
            return new ResponseEntity<>(skill, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody SkillDescriptionRequest skillDescriptionRequest)
    {
        skillRepository.addDescription(skillDescriptionRequest.getId(),skillDescriptionRequest.getDescription());
    }

    @PutMapping("/complete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void completeSkill(@RequestParam("id") Long id)
    {
        skillRepository.completeSkill(id);
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        skillRepository.deleteById(id);
    }
}
