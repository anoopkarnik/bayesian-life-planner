package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.Financial.*;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Journal.Journal;
import com.bayesiansamaritan.lifeplanner.model.Journal.JournalType;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsType;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTypeRepository;
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
    BadHabitRepository badHabitRepository;
    @Autowired
    BadHabitTypeRepository badHabitTypeRepository;
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
    @Autowired
    private GoalTypeRepository goalTypeRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    AccountTypeRepository accountTypeRepository;
    @Autowired
    CategoryTypeRepository categoryTypeRepository;

    @Autowired
    ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    SubCategoryTypeRepository subCategoryTypeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<HabitType> findHabitTypeByUserId(Long userId){

        List<HabitType> habitTypes = habitTypeRepository.findByUserId(userId);
        return habitTypeRepository.findByUserId(userId);
    };

    public List<BadHabitType> findBadHabitTypeByUserId(Long userId){

        List<BadHabitType> habitTypes = badHabitTypeRepository.findByUserId(userId);
        for(BadHabitType habitType:habitTypes){
            List<BadHabit> habits = badHabitRepository.findRootBadHabitsByUserIdAndActiveAndHabitTypeId(userId,true,habitType.getId());
            habitTypeRepository.updateCount(habitType.getId(), (long) habits.size());
        }
        return badHabitTypeRepository.findByUserId(userId);
    };

    @Override
    public List<TaskType> findTaskTypeByUserId(Long userId){
        List<TaskType> taskTypes = taskTypeRepository.findByUserId(userId);
        for(TaskType taskType:taskTypes){
            List<Task> tasks = taskRepository.findRootTasksByUserIdAndActiveAndTaskTypeId(userId,true,taskType.getId());
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
            List<Stats> statsList = statsRepository.findRootStatsByUserIdAndStatsTypeId(userId,statsType.getId());
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
        for(GoalType goalType:goalTypes){
            List<Goal> goalList = goalRepository.findRootGoalsByUserIdAndActiveAndGoalTypeId(userId,true,goalType.getId());
            goalTypeRepository.updateCount(goalType.getId(), (long) goalList.size());
        }
        return goalTypeRepository.findByUserId(userId);
    };

    @Override
    public List<AccountType> findAccountTypeByUserId(Long userId){
        List<AccountType> accountTypes = accountTypeRepository.findByUserId(userId);
        for(AccountType accountType:accountTypes){
            List<Account> accountList = accountRepository.findByUserIdAndAccountTypeId(userId,accountType.getId());
            accountTypeRepository.updateCount(accountType.getId(),(long) accountList.size());
        }
        return accountTypes;
    }


    @Override
    public List<CategoryType> findCategoryTypeByUserId(Long userId){
        List<CategoryType> categoryTypes = categoryTypeRepository.findByUserId(userId);
        return categoryTypes;
    }

    @Override
    public List<ExpenseType> findExpenseTypeByUserId(Long userId){
        List<ExpenseType> expenseTypes = expenseTypeRepository.findByUserId(userId);
        return expenseTypes;
    }

    @Override
    public List<SubCategoryType> findSubCategoryTypeByUserId(Long userId){
        List<SubCategoryType> subCategoryTypes = subCategoryTypeRepository.findByUserId(userId);
        return subCategoryTypes;
    }

}
