package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Skill;
import com.bayesiansamaritan.lifeplanner.model.Stats;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;

import java.util.List;

public interface SkillService {

    public List<SkillResponse> getAllSkills(Long userId, String skillTypeName);
    public Skill createRootSkill(Long userId, String name, String skillTypeName, Long timeTaken);
    public Skill createChildSkill(Long userId, String name, String skillTypeName, Long timeTaken,String parentName);
}
