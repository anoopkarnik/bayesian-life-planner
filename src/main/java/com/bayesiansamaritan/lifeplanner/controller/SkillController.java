package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Skill.SkillCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Skill.SkillCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Skill.SkillModifyRequest;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.SkillService;
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
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillService skillService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<SkillResponse>> getAllSkill(HttpServletRequest request, @RequestParam("skillTypeName") String skillTypeName,
                                                           @RequestParam("active") Boolean active) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            List<SkillResponse> skill= skillService.getAllSkills(userId,active,skillTypeName);
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
    public ResponseEntity<Skill> createRootSkill(HttpServletRequest request,@RequestBody SkillCreateRootRequest skillCreateRootRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Skill skill = skillService.createRootSkill(userId, skillCreateRootRequest.getName(),
                    skillCreateRootRequest.getSkillTypeName(), skillCreateRootRequest.getTimeTaken(),skillCreateRootRequest.getActive());
            return new ResponseEntity<>(skill, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Skill> createChildSkill(HttpServletRequest request,@RequestBody SkillCreateChildRequest skillCreateChildRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Skill skill = skillService.createChildSkill(userId, skillCreateChildRequest.getName(),
                    skillCreateChildRequest.getSkillTypeName(),skillCreateChildRequest.getTimeTaken(),skillCreateChildRequest.getParentSkillName(),
                    skillCreateChildRequest.getActive());
            return new ResponseEntity<>(skill, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody SkillModifyRequest skillModifyRequest)
    {
        skillRepository.modifyParams(skillModifyRequest.getId(),skillModifyRequest.getName(),skillModifyRequest.getStartDate(),
                skillModifyRequest.getDescription(),skillModifyRequest.getActive(),skillModifyRequest.getHidden(),skillModifyRequest.getCompleted(),
                skillModifyRequest.getTimeTaken());
    }

    @PutMapping("/complete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void completeSkill(@RequestParam("id") Long id)
    {
        skillRepository.completeSkill(id,true);
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        skillRepository.deleteById(id);
    }
}
