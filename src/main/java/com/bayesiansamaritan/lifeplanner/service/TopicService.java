package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Topic.Topic;
import com.bayesiansamaritan.lifeplanner.request.Topic.LinkCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.SubTopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Topic.TopicCreateRequest;
import com.bayesiansamaritan.lifeplanner.response.TopicResponse;

import java.util.Set;

public interface TopicService {

    public Set<TopicResponse> getAllTopics(Long userId,String skillTypeName);
    public Topic createTopic(Long userId, TopicCreateRequest topicCreateRequest);

    public void addLinkInTopic(Long userId, LinkCreateRequest linkCreateRequest);
    public void addSubTopicInTopic(Long userId, SubTopicCreateRequest subTopicCreateRequest);

    public void deleteLinkInTopic(Long userId, Long topicId, Long linkId);
    public void deleteSubTopicInTopic(Long userId, Long topicId, Long subTopicId);

}
