package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Topic;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.TopicRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Skill.*;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.response.TopicResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.SkillService;
import com.bayesiansamaritan.lifeplanner.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Set<TopicResponse> getAllTopic(HttpServletRequest request,@RequestParam("skillTypeName") String skillTypeName) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        Set<TopicResponse> topicList = topicService.getAllTopics(userId,skillTypeName);
        return topicList;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Topic> addTopic(HttpServletRequest request, @RequestBody TopicCreateRequest topicCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Topic topic = topicService.createTopic(userId,topicCreateRequest);
            return new ResponseEntity<>(topic, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/updateItem")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void updateItem(HttpServletRequest request, @RequestBody TopicModifyRequest topicModifyRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.updateItemInTopic(userId,topicModifyRequest.getId(), topicModifyRequest.getItemName());
    }

    @PatchMapping("/deleteItem")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteItem(HttpServletRequest request, @RequestBody TopicModifyRequest topicModifyRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.deleteItemInTopic(userId,topicModifyRequest.getId(),topicModifyRequest.getItemId());
    }

    @PatchMapping("/updateParagraph")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void updateParagraph(HttpServletRequest request, @RequestBody TopicCreateRequest topicCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.updateParagraphInTopic(userId,topicCreateRequest.getId(),topicCreateRequest.getParagraph());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@PathVariable("id") Long id){
        topicRepository.deleteById(id);
    }
}
