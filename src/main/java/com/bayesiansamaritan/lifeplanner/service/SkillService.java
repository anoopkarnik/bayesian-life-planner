package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;

import java.util.List;

public interface SkillService {

    public List<SkillResponse> getAllSkills(Long userId, Boolean active, String skillTypeName);
    public List<SkillResponse> getAllSkillsAndSubSkills(Long userId, Boolean active, String skillTypeName);
    public Skill createRootSkill(Long userId, String name, String skillTypeName, Long timeTaken, Boolean active);
    public Skill createChildSkill(Long userId, String name, String skillTypeName, Long timeTaken,String parentName, Boolean active);
}
