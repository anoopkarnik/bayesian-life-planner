package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Topic.Link;
import com.bayesiansamaritan.lifeplanner.model.Topic.SubTopic;
import com.bayesiansamaritan.lifeplanner.model.Topic.Topic;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.TopicRepository;
import com.bayesiansamaritan.lifeplanner.response.LinkResponse;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.response.SubTopicResponse;
import com.bayesiansamaritan.lifeplanner.response.TopicResponse;
import com.bayesiansamaritan.lifeplanner.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillTypeRepository skillTypeRepository;

    @Autowired
    TopicRepository topicRepository;

    @Override
    public List<SkillResponse> getAllSkills(Long userId,Boolean active, String skillTypeName){

        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        List<Skill> skills = skillRepository.findRootSkillsByUserIdAndActiveAndSkillTypeId(userId,active,skillType.getId());
        List<SkillResponse> skillResponses = new ArrayList<>();
        for (Skill skill: skills){
            Optional<List<Skill>> childSkills1 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,skill.getId());
            SkillResponse skillResponse = new SkillResponse(skill.getId(),skill.getCreatedAt(),
                    skill.getUpdatedAt(),skill.getName(),skill.getTimeTaken(),skillType.getName(),
                    skill.getDescription(),skill.getActive(),skill.getHidden(),skill.getCompleted());
            if (childSkills1.isPresent()){
                List<SkillResponse> childSkillResponses1 = new ArrayList<>();
                for(Skill childSkill1 : childSkills1.get()) {
                    Optional<SkillType> childSkillType1 = skillTypeRepository.findById(childSkill1.getSkillTypeId());
                    Optional<List<Skill>> childSkills2 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill1.getId());
                    SkillResponse childSkillResponse1 = new SkillResponse(childSkill1.getId(), childSkill1.getCreatedAt(),
                            childSkill1.getUpdatedAt(), childSkill1.getName(), childSkill1.getTimeTaken(), childSkillType1.get().getName(),
                            childSkill1.getDescription(),childSkill1.getActive(),childSkill1.getHidden(),childSkill1.getCompleted());
                    if (childSkills2.isPresent()){
                        List<SkillResponse> childSkillResponses2 = new ArrayList<>();
                        for(Skill childSkill2 : childSkills2.get()){
                            Optional<SkillType>  childSkillType2 = skillTypeRepository.findById(childSkill2.getSkillTypeId());
                            Optional<List<Skill>> childSkills3 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill2.getId());
                            SkillResponse childSkillResponse2 = new SkillResponse(childSkill2.getId(), childSkill2.getCreatedAt(),
                                    childSkill2.getUpdatedAt(), childSkill2.getName(), childSkill2.getTimeTaken(), childSkillType2.get().getName(),
                                   childSkill2.getDescription(),childSkill2.getActive(),childSkill2.getHidden(),childSkill2.getCompleted());
                            if (childSkills3.isPresent()){
                                List<SkillResponse> childSkillResponses3 = new ArrayList<>();
                                for(Skill childSkill3 : childSkills3.get()){
                                    Optional<SkillType>  childSkillType3 = skillTypeRepository.findById(childSkill2.getSkillTypeId());
                                    Optional<List<Skill>> childSkills4 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill3.getId());
                                    SkillResponse childSkillResponse3 = new SkillResponse(childSkill3.getId(), childSkill3.getCreatedAt(),
                                            childSkill3.getUpdatedAt(), childSkill3.getName(), childSkill3.getTimeTaken(), childSkillType3.get().getName(),
                                            childSkill3.getDescription(),childSkill3.getActive(),childSkill3.getHidden(),childSkill3.getCompleted());
                                    if (childSkills4.isPresent()){
                                        List<SkillResponse> childSkillResponses4 = new ArrayList<>();
                                        for(Skill childSkill4 : childSkills4.get()){
                                            Optional<SkillType>  childSkillType4 = skillTypeRepository.findById(childSkill3.getSkillTypeId());
                                            Optional<List<Skill>> childSkills5 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill4.getId());
                                            SkillResponse childSkillResponse4 = new SkillResponse(childSkill4.getId(), childSkill4.getCreatedAt(),
                                                    childSkill4.getUpdatedAt(), childSkill4.getName(), childSkill4.getTimeTaken(), childSkillType4.get().getName(),
                                                    childSkill4.getDescription(),childSkill4.getActive(),childSkill4.getHidden(),childSkill4.getCompleted());
                                            childSkillResponses4.add(childSkillResponse4);
                                        }
                                        childSkillResponse3.setSkillResponses(childSkillResponses4);
                                    }
                                    childSkillResponses3.add(childSkillResponse3);
                                }
                                childSkillResponse2.setSkillResponses(childSkillResponses3);
                            }
                            childSkillResponses2.add(childSkillResponse2);
                        }
                        childSkillResponse1.setSkillResponses(childSkillResponses2);
                    }
                    childSkillResponses1.add(childSkillResponse1);
                }
                skillResponse.setSkillResponses(childSkillResponses1);
            }
            skillResponses.add(skillResponse);
        }
        return skillResponses;
    };

    @Override
    public List<SkillResponse> getAllSkillsAndSubSkills(Long userId, Boolean active, String skillTypeName){

        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        List<Skill> skills = skillRepository.findRootSkillsByUserIdAndActiveAndSkillTypeId(userId,active,skillType.getId());
        List<SkillResponse> skillResponses = new ArrayList<>();
        for (Skill skill: skills){
            Optional<List<Skill>> childSkills1 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,skill.getId());
            SkillResponse skillResponse = new SkillResponse(skill.getId(),skill.getCreatedAt(),
                    skill.getUpdatedAt(),skill.getName(),skill.getTimeTaken(),skillType.getName(),
                    skill.getDescription(),skill.getActive(),skill.getHidden(),skill.getCompleted());
            if (childSkills1.isPresent()){
                for(Skill childSkill1 : childSkills1.get()) {
                    Optional<SkillType> childSkillType1 = skillTypeRepository.findById(childSkill1.getSkillTypeId());
                    Optional<List<Skill>> childSkills2 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill1.getId());
                    SkillResponse childSkillResponse1 = new SkillResponse(childSkill1.getId(), childSkill1.getCreatedAt(),
                            childSkill1.getUpdatedAt(), childSkill1.getName(), childSkill1.getTimeTaken(), childSkillType1.get().getName(),
                            childSkill1.getDescription(),childSkill1.getActive(),childSkill1.getHidden(),childSkill1.getCompleted());
                    if (childSkills2.isPresent()){
                        for(Skill childSkill2 : childSkills2.get()){
                            Optional<SkillType>  childSkillType2 = skillTypeRepository.findById(childSkill2.getSkillTypeId());
                            Optional<List<Skill>> childSkills3 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill2.getId());
                            SkillResponse childSkillResponse2 = new SkillResponse(childSkill2.getId(), childSkill2.getCreatedAt(),
                                    childSkill2.getUpdatedAt(), childSkill2.getName(), childSkill2.getTimeTaken(), childSkillType2.get().getName(),
                                    childSkill2.getDescription(),childSkill2.getActive(),childSkill2.getHidden(),childSkill2.getCompleted());
                            if (childSkills3.isPresent()){
                                for(Skill childSkill3 : childSkills3.get()){
                                    Optional<SkillType>  childSkillType3 = skillTypeRepository.findById(childSkill2.getSkillTypeId());
                                    Optional<List<Skill>> childSkills4 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill3.getId());
                                    SkillResponse childSkillResponse3 = new SkillResponse(childSkill3.getId(), childSkill3.getCreatedAt(),
                                            childSkill3.getUpdatedAt(), childSkill3.getName(), childSkill3.getTimeTaken(), childSkillType3.get().getName(),
                                            childSkill3.getDescription(),childSkill3.getActive(),childSkill3.getHidden(),childSkill3.getCompleted());
                                    if (childSkills4.isPresent()){
                                        for(Skill childSkill4 : childSkills4.get()){
                                            Optional<SkillType>  childSkillType4 = skillTypeRepository.findById(childSkill3.getSkillTypeId());
                                            Optional<List<Skill>> childSkills5 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,active,childSkill4.getId());
                                            SkillResponse childSkillResponse4 = new SkillResponse(childSkill4.getId(), childSkill4.getCreatedAt(),
                                                    childSkill4.getUpdatedAt(), childSkill4.getName(), childSkill4.getTimeTaken(), childSkillType4.get().getName(),
                                                    childSkill4.getDescription(),childSkill4.getActive(),childSkill4.getHidden(),childSkill4.getCompleted());
                                            skillResponses.add(childSkillResponse4);
                                        }
                                    }
                                    skillResponses.add(childSkillResponse3);
                                }
                            }
                            skillResponses.add(childSkillResponse2);
                        }
                    }
                    skillResponses.add(childSkillResponse1);
                }
            }
            skillResponses.add(skillResponse);
        }
        return skillResponses;
    };

    @Override
    public Skill createRootSkill(Long userId, String name, String skillTypeName, Long timeTaken, Boolean active){
        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        Skill skill = skillRepository.save(new Skill(name,timeTaken,skillType.getId(),active,userId,false));
        return skill;
    }
    @Override
    public Skill createChildSkill(Long userId, String name, String skillTypeName, Long timeTaken,String parentName, Boolean active){
        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        Skill skill = skillRepository.findByUserIdAndName(userId,parentName);
        Skill newSkill = skillRepository.save(new Skill(name,timeTaken,skillType.getId(),active,userId,false,skill.getId()));
        return newSkill;
    }

    @Override
    public void addTopic(Long skillId, Long topicId){
        Topic topic = topicRepository.findById(topicId).get();
        Skill skill = skillRepository.findById(skillId).get();
        skill.getTopicSet().add(topic);
        skillRepository.save(skill);
    }

    @Override
    public void removeTopic(Long skillId, Long topicId){
        Topic topic = topicRepository.findById(topicId).get();
        Skill skill = skillRepository.findById(skillId).get();
        skill.getTopicSet().remove(topic);
        skillRepository.save(skill);
    }

    @Override
    public Set<TopicResponse> getTopicsFromSkills(Long skillId){
        Skill skill = skillRepository.findById(skillId).get();
        Set<TopicResponse> topicResponses = new HashSet<>();
        for (Topic topic : skill.getTopicSet()) {
            Set<LinkResponse> linkResponses = new HashSet<>();
            Set<SubTopicResponse> subTopicResponses = new HashSet<>();
            for (Link link : topic.getLinks()) {
                linkResponses.add(new LinkResponse(link.getId(), link.getCreatedAt(),
                        link.getUpdatedAt(), link.getName(),link.getUrl(), link.getManualSummary(),
                        link.getAiSummary(), link.getTranscript()));
            }
            for (SubTopic subTopic : topic.getSubTopics()) {
                subTopicResponses.add(new SubTopicResponse(subTopic.getId(), subTopic.getCreatedAt(),
                        subTopic.getUpdatedAt(), subTopic.getName(), subTopic.getText()));
            }
            topicResponses.add(new TopicResponse(topic.getId(), topic.getCreatedAt(), topic.getUpdatedAt(), topic.getName(),
                    skill.getName(), topic.getSummary(), topic.getDescription(), linkResponses, subTopicResponses));
        }
        return topicResponses;
    }

}
