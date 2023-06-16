package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Topic.Link;
import com.bayesiansamaritan.lifeplanner.model.Topic.SubTopic;
import com.bayesiansamaritan.lifeplanner.model.Topic.Topic;
import com.bayesiansamaritan.lifeplanner.repository.Skill.LinkRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SubTopicRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.TopicRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitModifyRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.LinkCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.SubTopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.TopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.TopicModifyRequest;
import com.bayesiansamaritan.lifeplanner.response.LinkResponse;
import com.bayesiansamaritan.lifeplanner.response.SubTopicResponse;
import com.bayesiansamaritan.lifeplanner.response.TopicResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
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
    private SubTopicRepository subTopicRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private SkillTypeRepository skillTypeRepository;

    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public TopicResponse getTopic(@PathVariable("id") Long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        SkillType skillType = skillTypeRepository.findById(topic.getSkillTypeId()).get();
        Set<LinkResponse> linkResponses = new HashSet<>();
        Set<SubTopicResponse> subTopicResponses = new HashSet<>();
        for (Link link : topic.getLinks()){
            linkResponses.add(new LinkResponse(link.getId(),link.getCreatedAt(),
                    link.getUpdatedAt(),link.getName(),link.getUrl(),link.getManualSummary(),
                    link.getAiSummary(),link.getTranscript()));
        }
        for (SubTopic subTopic: topic.getSubTopics()){
            subTopicResponses.add(new SubTopicResponse(subTopic.getId(),subTopic.getCreatedAt(),
                    subTopic.getUpdatedAt(),subTopic.getName(),subTopic.getText()));
        }
        TopicResponse topicResponse = new TopicResponse(topic.getId(),topic.getCreatedAt(),topic.getUpdatedAt(),topic.getName(),
                skillType.getName(), topic.getSummary(),topic.getDescription(),linkResponses,subTopicResponses);
        return topicResponse;
    }


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
    @PatchMapping("/addLink")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addLink(HttpServletRequest request, @RequestBody LinkCreateRequest linkCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.addLinkInTopic(userId,linkCreateRequest);
    }

    @PatchMapping("/addSubTopic")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addSubTopic(HttpServletRequest request, @RequestBody SubTopicCreateRequest subTopicCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.addSubTopicInTopic(userId,subTopicCreateRequest);
    }

    @PatchMapping("/deleteLink")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteLink(HttpServletRequest request, @RequestBody LinkCreateRequest linkCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.deleteLinkInTopic(userId,linkCreateRequest.getTopicId(),linkCreateRequest.getId());
    }

    @PatchMapping("/deleteSubTopic")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTopic(HttpServletRequest request, @RequestBody SubTopicCreateRequest subTopicCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        topicService.deleteSubTopicInTopic(userId,subTopicCreateRequest.getTopicId(),subTopicCreateRequest.getId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@PathVariable("id") Long id){
        topicRepository.deleteById(id);
    }

    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody TopicModifyRequest topicModifyRequest)
    {
        topicRepository.modifyParams(topicModifyRequest.getId(),topicModifyRequest.getName(),topicModifyRequest.getSummary(),
                topicModifyRequest.getDescription());
    }

    @PatchMapping("/subTopic/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody SubTopicCreateRequest subTopicCreateRequest)
    {
        subTopicRepository.modifyParams(subTopicCreateRequest.getId(), subTopicCreateRequest.getName(),subTopicCreateRequest.getText());
    }

    @PatchMapping("/link/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody LinkCreateRequest linkCreateRequest)
    {
        linkRepository.modifyParams(linkCreateRequest.getId(), linkCreateRequest.getName(),linkCreateRequest.getUrl(),
                linkCreateRequest.getManualSummary());
    }
}
