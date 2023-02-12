package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.HabitType;
import com.bayesiansamaritan.lifeplanner.model.JournalType;
import com.bayesiansamaritan.lifeplanner.model.StatsType;
import com.bayesiansamaritan.lifeplanner.model.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.JournalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.TaskTypeRepository;
import com.bayesiansamaritan.lifeplanner.service.CommonsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
	StatsTypeRepository statsTypeRepository;

	@Autowired
	CommonsService commonsService;

	@GetMapping("/task")
	public ResponseEntity<List<TaskType>> getAllTasks(@RequestParam Long userId) {
		try {
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
	public ResponseEntity<TaskType> createTaskType(@RequestBody TaskType taskType) {
		try {
			TaskType _taskType = taskTypeRepository.save(new TaskType(taskType.getName(),taskType.getUserId()));
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
	public ResponseEntity<List<HabitType>> getAllHabits(@RequestParam Long userId) {
		try {
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
	public ResponseEntity<HabitType> createHabitType(@RequestBody HabitType habitType) {
		try {
			HabitType _habitType = habitTypeRepository.save(new HabitType(habitType.getName(),habitType.getUserId()));
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
	public ResponseEntity<List<JournalType>> getAllJournals(@RequestParam Long userId) {
		try {
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
	public ResponseEntity<JournalType> createJournalType(@RequestBody JournalType journalType) {
		try {
			JournalType _journalType= journalTypeRepository.save(new JournalType(journalType.getName(),journalType.getUserId()));
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
	public ResponseEntity<List<StatsType>> getAllStats(@RequestParam Long userId) {
		try {
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
	public ResponseEntity<StatsType> createStatsType(@RequestBody StatsType statsType) {
		try {
			StatsType _statsType= statsTypeRepository.save(new StatsType(statsType.getName(),statsType.getUserId()));
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
}