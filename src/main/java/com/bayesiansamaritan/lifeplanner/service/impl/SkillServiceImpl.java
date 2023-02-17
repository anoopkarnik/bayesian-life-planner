package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    SkillTypeRepository skillTypeRepository;

    @Override
    public List<SkillResponse> getAllSkills(Long userId, String skillTypeName){

        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        List<Skill> skills = skillRepository.findRootSkillsByUserIdAndActiveAndSkillTypeId(userId,true,skillType.getId());
        List<SkillResponse> skillResponses = new ArrayList<>();
        for (Skill skill: skills){
            Optional<List<Skill>> childSkills1 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,true,skill.getId());
            SkillResponse skillResponse = new SkillResponse(skill.getId(),skill.getCreatedAt(),
                    skill.getUpdatedAt(),skill.getName(),skill.getTimeTaken(),skillType.getName(),
                    skill.getCompleted(),skill.getDescription());
            if (childSkills1.isPresent()){
                List<SkillResponse> childSkillResponses1 = new ArrayList<>();
                for(Skill childSkill1 : childSkills1.get()) {
                    Optional<SkillType> childSkillType1 = skillTypeRepository.findById(childSkill1.getSkillTypeId());
                    Optional<List<Skill>> childSkills2 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,true,childSkill1.getId());
                    SkillResponse childSkillResponse1 = new SkillResponse(childSkill1.getId(), childSkill1.getCreatedAt(),
                            childSkill1.getUpdatedAt(), childSkill1.getName(), childSkill1.getTimeTaken(), childSkillType1.get().getName(),
                            childSkill1.getCompleted(), childSkill1.getDescription());
                    if (childSkills2.isPresent()){
                        List<SkillResponse> childSkillResponses2 = new ArrayList<>();
                        for(Skill childSkill2 : childSkills2.get()){
                            Optional<SkillType>  childSkillType2 = skillTypeRepository.findById(childSkill2.getSkillTypeId());
                            Optional<List<Skill>> childSkills3 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,true,childSkill2.getId());
                            SkillResponse childSkillResponse2 = new SkillResponse(childSkill2.getId(), childSkill2.getCreatedAt(),
                                    childSkill2.getUpdatedAt(), childSkill2.getName(), childSkill2.getTimeTaken(), childSkillType2.get().getName(),
                                    childSkill2.getCompleted(), childSkill2.getDescription());
                            if (childSkills3.isPresent()){
                                List<SkillResponse> childSkillResponses3 = new ArrayList<>();
                                for(Skill childSkill3 : childSkills3.get()){
                                    Optional<SkillType>  childSkillType3 = skillTypeRepository.findById(childSkill2.getSkillTypeId());
                                    Optional<List<Skill>> childSkills4 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,true,childSkill3.getId());
                                    SkillResponse childSkillResponse3 = new SkillResponse(childSkill3.getId(), childSkill3.getCreatedAt(),
                                            childSkill3.getUpdatedAt(), childSkill3.getName(), childSkill3.getTimeTaken(), childSkillType3.get().getName(),
                                            childSkill3.getCompleted(), childSkill3.getDescription());
                                    if (childSkills4.isPresent()){
                                        List<SkillResponse> childSkillResponses4 = new ArrayList<>();
                                        for(Skill childSkill4 : childSkills4.get()){
                                            Optional<SkillType>  childSkillType4 = skillTypeRepository.findById(childSkill3.getSkillTypeId());
                                            Optional<List<Skill>> childSkills5 =  skillRepository.findSkillsByUserIdAndActiveAndParentSkillId(userId,true,childSkill4.getId());
                                            SkillResponse childSkillResponse4 = new SkillResponse(childSkill4.getId(), childSkill4.getCreatedAt(),
                                                    childSkill4.getUpdatedAt(), childSkill4.getName(), childSkill4.getTimeTaken(), childSkillType4.get().getName(),
                                                    childSkill4.getCompleted(), childSkill4.getDescription());
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
    public Skill createRootSkill(Long userId, String name, String skillTypeName, Long timeTaken){
        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        Skill skill = skillRepository.save(new Skill(name,timeTaken,skillType.getId(),true,userId,false));
        return skill;
    }
    @Override
    public Skill createChildSkill(Long userId, String name, String skillTypeName, Long timeTaken,String parentName){
        SkillType skillType = skillTypeRepository.findByNameAndUserId(skillTypeName,userId);
        Skill skill = skillRepository.findByUserIdAndName(userId,parentName);
        Skill newSkill = skillRepository.save(new Skill(name,timeTaken,skillType.getId(),true,userId,false,skill.getId()));
        return newSkill;
    }

}
