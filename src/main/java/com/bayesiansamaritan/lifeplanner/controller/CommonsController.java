package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.User.UserProfile;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Journal.JournalType;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsType;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.CommonsService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/commons")
public class CommonsController {
	@Autowired
	private JournalTypeRepository journalTypeRepository;

	@Autowired
	TaskTypeRepository taskTypeRepository;

	@Autowired
	HabitTypeRepository habitTypeRepository;

	@Autowired
	BadHabitTypeRepository badHabitTypeRepository;

	@Autowired
	StatsTypeRepository statsTypeRepository;

	@Autowired
	SkillTypeRepository skillTypeRepository;

	@Autowired
	GoalTypeRepository goalTypeRepository;

	@Autowired
	CommonsService commonsService;
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	JwtUtils jwtUtils;
	static final String HEADER_STRING = "Authorization";
	static final String TOKEN_PREFIX = "Bearer";


	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Optional<UserProfile> getUser(HttpServletRequest request) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Optional<UserProfile> userProfile = userProfileRepository.findByName(username);
		return userProfile;
	};


	@GetMapping("/task")
	public ResponseEntity<List<TaskType>> getAllTasks(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<TaskType> taskTypes = commonsService.findTaskTypeByUserId(userId);
			if (taskTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(taskTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/task")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<TaskType> createTaskType(HttpServletRequest request, @RequestBody TaskType taskType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			TaskType _taskType = taskTypeRepository.save(new TaskType(taskType.getName(),userId));
			return new ResponseEntity<>(_taskType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/task")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteTaskType(@RequestParam Long id) {
		try {
			taskTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/task")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyTaskType(@RequestParam Long id,@RequestParam String name) {
		try {
			taskTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/habit")
	public ResponseEntity<List<HabitType>> getAllHabits(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<HabitType> habitTypes = commonsService.findHabitTypeByUserId(userId);
			if (habitTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(habitTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/habit")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HabitType> createHabitType(HttpServletRequest request,@RequestBody HabitType habitType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			HabitType _habitType = habitTypeRepository.save(new HabitType(habitType.getName(),userId));
			return new ResponseEntity<>(_habitType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/habit")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteHabitType(@RequestParam Long id) {
		try {
			habitTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/habit")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyHabitType(@RequestParam Long id,@RequestParam String name) {
		try {
			habitTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/journal")
	public ResponseEntity<List<JournalType>> getAllJournals(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<JournalType> journalTypes = commonsService.findJournalTypeByUserId(userId);
			if (journalTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(journalTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/journal")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<JournalType> createJournalType(HttpServletRequest request,@RequestBody JournalType journalType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			JournalType _journalType= journalTypeRepository.save(new JournalType(journalType.getName(),userId));
			return new ResponseEntity<>(_journalType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/journal")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteJournalType(@RequestParam Long id) {
		try {
			journalTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/journal")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyJournalType(@RequestParam Long id,@RequestParam String name) {
		try {
			journalTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/stats")
	public ResponseEntity<List<StatsType>> getAllStats(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<StatsType> statsTypes = commonsService.findStatsTypeByUserId(userId);
			if (statsTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(statsTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/stats")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<StatsType> createStatsType(HttpServletRequest request,@RequestBody StatsType statsType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			StatsType _statsType= statsTypeRepository.save(new StatsType(statsType.getName(),userId));
			return new ResponseEntity<>(_statsType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/stats")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteStatsType(@RequestParam Long id) {
		try {
			statsTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/stats")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyStatsType(@RequestParam Long id,@RequestParam String name) {
		try {
			statsTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/skill")
	public ResponseEntity<List<SkillType>> getAllSkill(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<SkillType> skillTypes = commonsService.findSkillTypeByUserId(userId);
			if (skillTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(skillTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/skill")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<SkillType> createSkillType(HttpServletRequest request,@RequestBody SkillType skillType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			SkillType _skillType= skillTypeRepository.save(new SkillType(skillType.getName(),userId));
			return new ResponseEntity<>(_skillType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/skill")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteSkillType(@RequestParam Long id) {
		try {
			skillTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/skill")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifySkillType(@RequestParam Long id,@RequestParam String name) {
		try {
			skillTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/goal")
	public ResponseEntity<List<GoalType>> getAllGoal(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<GoalType> goalTypes = commonsService.findGoalTypeByUserId(userId);
			if (goalTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(goalTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/goal")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<GoalType> createGoalType(HttpServletRequest request,@RequestBody GoalType goalType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			GoalType _goalType= goalTypeRepository.save(new GoalType(goalType.getName(),userId));
			return new ResponseEntity<>(_goalType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/goal")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteGoalType(@RequestParam Long id) {
		try {
			goalTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/goal")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyGoalType(@RequestParam Long id,@RequestParam String name) {
		try {
			goalTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/badHabit")
	public ResponseEntity<List<BadHabitType>> getAllBadHabits(HttpServletRequest request) {
		try {
			String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
			Long userId = userProfileRepository.findByName(username).get().getId();
			List<BadHabitType> habitTypes = commonsService.findBadHabitTypeByUserId(userId);
			if (habitTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(habitTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/badHabit")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<BadHabitType> createBadHabitType(HttpServletRequest request,@RequestBody BadHabitType habitType) {
		String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
		Long userId = userProfileRepository.findByName(username).get().getId();
		try {
			BadHabitType _habitType = badHabitTypeRepository.save(new BadHabitType(habitType.getName(),userId));
			return new ResponseEntity<>(_habitType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/badHabit")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteBadHabitType(@RequestParam Long id) {
		try {
			badHabitTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/badHabit")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyBadHabitType(@RequestParam Long id,@RequestParam String name) {
		try {
			badHabitTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}
}