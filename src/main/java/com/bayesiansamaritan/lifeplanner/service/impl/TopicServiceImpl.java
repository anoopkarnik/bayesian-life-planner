package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import com.bayesiansamaritan.lifeplanner.model.Item;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Topic;
import com.bayesiansamaritan.lifeplanner.repository.Skill.ItemRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.TopicRepository;
import com.bayesiansamaritan.lifeplanner.request.Skill.TopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.response.ItemResponse;
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
    ItemRepository itemRepository;
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
            Set<ItemResponse> itemResponses = new HashSet<>();
            for (Item item :topic.getItems()){
                itemResponses.add(new ItemResponse(item.getId(),item.getCreatedAt(),item.getUpdatedAt(),item.getText()));
            }
            topicResponses.add(new TopicResponse(topic.getId(),topic.getCreatedAt(),topic.getUpdatedAt(),topic.getName(),topic.getTopicTypeEnum(),topic.getParagraph(),
                    skillTypeName,itemResponses));
        }
        return topicResponses;

    }

    @Override
    public Topic createTopic(Long userId, TopicCreateRequest topicCreateRequest){

        Topic topic = new Topic();
        SkillType skillType = skillTypeRepository.findByNameAndUserId(topicCreateRequest.getSkillTypeName(),userId);
        if(topicCreateRequest.getTopicTypeEnum().equals(TopicTypeEnum.TOPIC_PARAGRAPH)){
            topic = topicRepository.save(new Topic(topicCreateRequest.getTopicTypeEnum(),topicCreateRequest.getParagraph(),
                    skillType.getId(),userId,topicCreateRequest.getName()));
        }
        else if(topicCreateRequest.getTopicTypeEnum().equals(TopicTypeEnum.TOPIC_LIST) ){
            Set<Item> items = new HashSet<>();

            for (String item : topicCreateRequest.getItems()){
                Item savedItem = new Item(item);
                items.add(savedItem);
            }
            topic = topicRepository.save(new Topic(topicCreateRequest.getTopicTypeEnum(),items,
                    skillType.getId(),userId,topicCreateRequest.getName()));
        }
        else if(topicCreateRequest.getTopicTypeEnum().equals(TopicTypeEnum.TOPIC_URL) ){
            Set<Item> items = new HashSet<>();

            for (String item : topicCreateRequest.getItems()){
                Item savedItem = new Item(item);
                items.add(savedItem);
            }
            topic = topicRepository.save(new Topic(topicCreateRequest.getTopicTypeEnum(),items,
                    skillType.getId(),userId,topicCreateRequest.getName()));
        }
        return topic;
    };

    @Override
    public void updateItemInTopic(Long userId, Long topicId, String itemName){
        Item item = itemRepository.save(new Item(itemName));
        Topic topic = topicRepository.findById(topicId).get();
        topic.getItems().add(item);
        topicRepository.save(topic);
    }

    @Override
    public void deleteItemInTopic(Long userId, Long topicId, Long itemId){
        Item item = itemRepository.findById(itemId).get();
        Topic topic = topicRepository.findById(topicId).get();
        topic.getItems().remove(item);
        topicRepository.save(topic);
    }

    @Override
    public void updateParagraphInTopic(Long userId, Long topicId, String paragraph){
        topicRepository.updateParagraphByTopic(userId,topicId,paragraph);

    }



}


