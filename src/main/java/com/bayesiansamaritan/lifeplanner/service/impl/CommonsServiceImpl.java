package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.*;
import com.bayesiansamaritan.lifeplanner.repository.*;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.response.SkillResponse;
import com.bayesiansamaritan.lifeplanner.service.CommonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CommonsServiceImpl implements CommonsService {

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    HabitTypeRepository habitTypeRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private JournalTypeRepository journalTypeRepository;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private StatsTypeRepository statsTypeRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private SkillTypeRepository skillTypeRepository;
    @Override
    public List<HabitType> findHabitTypeByUserId(Long userId){

        List<HabitType> habitTypes = habitTypeRepository.findByUserId(userId);
        for(HabitType habitType:habitTypes){
            List<Habit> habits = habitRepository.findByUserIdAndActiveAndHabitTypeId(userId,true,habitType.getId());
            habitTypeRepository.updateCount(habitType.getId(), (long) habits.size());
        }
        return habitTypeRepository.findByUserId(userId);
    };

    @Override
    public List<TaskType> findTaskTypeByUserId(Long userId){
        List<TaskType> taskTypes = taskTypeRepository.findByUserId(userId);
        for(TaskType taskType:taskTypes){
            List<Task> tasks = taskRepository.findByUserIdAndActiveAndTaskTypeId(userId,true,taskType.getId());
            taskTypeRepository.updateCount(taskType.getId(), (long) tasks.size());
        }
        return taskTypeRepository.findByUserId(userId);
    };

    @Override
    public List<JournalType> findJournalTypeByUserId(Long userId){
        List<JournalType> journalTypes = journalTypeRepository.findByUserId(userId);
        for(JournalType journalType:journalTypes){
            List<Journal> journals = journalRepository.findByUserIdAndJournalTypeId(userId,journalType.getId());
            journalTypeRepository.updateCount(journalType.getId(), (long) journals.size());
        }
        return journalTypeRepository.findByUserId(userId);
    };

    @Override
    public List<StatsType> findStatsTypeByUserId(Long userId){
        List<StatsType> statsTypes = statsTypeRepository.findByUserId(userId);
        for(StatsType statsType:statsTypes){
            List<Stats> statsList = statsRepository.findByUserIdAndStatsTypeId(userId,statsType.getId());
            statsTypeRepository.updateCount(statsType.getId(), (long) statsList.size());
        }
        return statsTypeRepository.findByUserId(userId);
    };

    @Override
    public List<SkillType> findSkillTypeByUserId(Long userId){
        List<SkillType> skillTypes = skillTypeRepository.findByUserId(userId);
        for(SkillType skillType:skillTypes){
            List<Skill> skillList = skillRepository.findRootSkillsByUserIdAndActiveAndSkillTypeId(userId,true,skillType.getId());
            skillTypeRepository.updateCount(skillType.getId(), (long) skillList.size());
        }
        return skillTypeRepository.findByUserId(userId);
    };

    @Override
    public List<GoalType> findGoalTypeByUserId(Long userId){
        List<GoalType> goalTypes = goalTypeRepository.findByUserId(userId);
        for(GoalType goalType:skillTypes){
            List<Goal> skillList = skillRepository.findRootGoalsByUserIdAndActiveAndGoalTypeId(userId,true,goalType.getId());
            goalTypeRepository.updateCount(goalType.getId(), (long) goalList.size());
        }
        return goalTypeRepository.findByUserId(userId);
    };
}
