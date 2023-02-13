package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Skill;
import com.bayesiansamaritan.lifeplanner.model.Stats;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.response.StatsResponse;

import java.util.List;

public interface SkillService {

    public List<SkillResponse> getAllRootSkills(Long userId, String skillTypeName);
    public List<SkillResponse> getSkillsByParentSkillId(Long userId, String parentSkillTypeName);
    public Skill createSkill(Long userId, String name, String statsTypeName, Float value, String description);
}
