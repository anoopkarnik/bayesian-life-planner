package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Topic.Link;
import com.bayesiansamaritan.lifeplanner.model.Topic.SubTopic;
import com.bayesiansamaritan.lifeplanner.model.Topic.Topic;
import com.bayesiansamaritan.lifeplanner.repository.Skill.*;
import com.bayesiansamaritan.lifeplanner.request.Topic.LinkCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.SubTopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.TopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.response.LinkResponse;
import com.bayesiansamaritan.lifeplanner.response.SubTopicResponse;
import com.bayesiansamaritan.lifeplanner.response.TopicResponse;
import com.bayesiansamaritan.lifeplanner.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    LinkRepository linkRepository;

    @Autowired
    SubTopicRepository subTopicRepository;
    @Autowired
    SkillTypeRepository skillTypeRepository;
    @Autowired
    SkillRepository skillRepository;

    @Override
    public Set<TopicResponse> getAllTopics(Long userId,String skillTypeName){
        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        List<Topic> topicList = topicRepository.findTopicByUserIdAndSkillTypeId(userId,skillType.getId());
        Set<TopicResponse> topicResponses = new HashSet<>();
        for (Topic topic : topicList){
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
            topicResponses.add(new TopicResponse(topic.getId(),topic.getCreatedAt(),topic.getUpdatedAt(),topic.getName(),
                    skillTypeName, topic.getSummary(),topic.getDescription(),linkResponses,subTopicResponses));
        }
        return topicResponses;

    }

    @Override
    public Topic createTopic(Long userId, TopicCreateRequest topicCreateRequest){
        SkillType skillType = skillTypeRepository.findByNameAndUserId(topicCreateRequest.getSkillTypeName(),userId);
        Topic topic = topicRepository.save(new Topic(skillType.getId(),userId,topicCreateRequest.getName(),
                topicCreateRequest.getDescription()));
        return topic;
    };

    @Override
    public void addLinkInTopic(Long userId, LinkCreateRequest linkCreateRequest){
        Link link = new Link(userId,linkCreateRequest.getName(),linkCreateRequest.getUrl(),
                linkCreateRequest.getManualSummary(),linkCreateRequest.getAiSummary(),
                linkCreateRequest.getTranscript());
        Topic topic = topicRepository.findById(linkCreateRequest.getTopicId()).get();
        topic.getLinks().add(link);
        topicRepository.save(topic);
    }

    @Override
    public void addSubTopicInTopic(Long userId, SubTopicCreateRequest subTopicCreateRequest){
        SubTopic subTopic= new SubTopic(userId,subTopicCreateRequest.getName(),
                subTopicCreateRequest.getText());
        Topic topic = topicRepository.findById(subTopicCreateRequest.getTopicId()).get();
        topic.getSubTopics().add(subTopic);
        topicRepository.save(topic);
    }

    @Override
    public void deleteLinkInTopic(Long userId, Long topicId, Long linkId){
        Link link = linkRepository.findById(linkId).get();
        Topic topic = topicRepository.findById(topicId).get();
        topic.getLinks().remove(link);
        topicRepository.save(topic);
    }

    @Override
    public void deleteSubTopicInTopic(Long userId, Long topicId, Long subTopicId){
        SubTopic subTopic = subTopicRepository.findById(subTopicId).get();
        Topic topic = topicRepository.findById(topicId).get();
        topic.getSubTopics().remove(subTopic);
        topicRepository.save(topic);
    }
}


