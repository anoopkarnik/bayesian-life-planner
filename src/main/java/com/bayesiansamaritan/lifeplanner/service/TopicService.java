package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Financial.Account;
import com.bayesiansamaritan.lifeplanner.model.Item;
import com.bayesiansamaritan.lifeplanner.model.Topic;
import com.bayesiansamaritan.lifeplanner.request.Skill.TopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.response.AccountBalanceResponse;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.response.TopicResponse;

import java.util.List;
import java.util.Set;

public interface TopicService {

    public Set<TopicResponse> getAllTopics(Long userId,String skillTypeName);
    public Topic createTopic(Long userId, TopicCreateRequest topicCreateRequest);

    public void updateItemInTopic(Long userId, Long topicId, String itemName);

    public void deleteItemInTopic(Long userId, Long topicId, Long itemId);

    public void updateParagraphInTopic(Long userId, Long topicId, String paragraph);


}
