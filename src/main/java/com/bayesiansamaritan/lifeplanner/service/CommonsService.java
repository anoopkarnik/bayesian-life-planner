package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Financial.AccountType;
import com.bayesiansamaritan.lifeplanner.model.Financial.CategoryType;
import com.bayesiansamaritan.lifeplanner.model.Financial.ExpenseType;
import com.bayesiansamaritan.lifeplanner.model.Financial.SubCategoryType;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Journal.JournalType;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsType;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;

import java.util.List;
import java.util.Optional;

public interface CommonsService {

    public List<HabitType> findHabitTypeByUserId(Long userId);
    public List<BadHabitType> findBadHabitTypeByUserId(Long userId);
    public List<TaskType> findTaskTypeByUserId(Long userId);

    public List<JournalType> findJournalTypeByUserId(Long userId);

    public List<StatsType> findStatsTypeByUserId(Long userId);

    public List<SkillType> findSkillTypeByUserId(Long userId);
    public List<GoalType> findGoalTypeByUserId(Long userId);

    public List<AccountType>  findAccountTypeByUserId(Long userId);
    public List<CategoryType> findCategoryTypeByUserId(Long userId);
    public List<ExpenseType> findExpenseTypeByUserId(Long userId);
    public List<SubCategoryType> findSubCategoryTypeByUserId(Long userId);

}
