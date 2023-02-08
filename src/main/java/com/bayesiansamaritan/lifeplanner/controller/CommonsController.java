package com.bayesiansamaritan.lifeplanner.controller;

import com.bayesiansamaritan.lifeplanner.model.HabitType;
import com.bayesiansamaritan.lifeplanner.model.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.TaskTypeRepository;
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
	TaskTypeRepository taskTypeRepository;

	@Autowired
	HabitTypeRepository habitTypeRepository;

	@GetMapping("/task")
	public ResponseEntity<List<TaskType>> getAllTasks() {
		try {
			List<TaskType> taskTypes = taskTypeRepository.findAll();
			if (taskTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(taskTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/task")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TaskType> createTaskType(@RequestBody TaskType taskType) {
		try {
			TaskType _taskType = taskTypeRepository.save(new TaskType(taskType.getName()));
			return new ResponseEntity<>(_taskType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/task")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteTaskType(@RequestParam Long id) {
		try {
			taskTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/task")
	@PreAuthorize("hasRole('ADMIN')")
	public void modifyTaskType(@RequestParam Long id,@RequestParam String name) {
		try {
			taskTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}

	@GetMapping("/habit")
	public ResponseEntity<List<HabitType>> getAllHabits() {
		try {
			List<HabitType> habitTypes = habitTypeRepository.findAll();
			if (habitTypes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(habitTypes , HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/habit")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HabitType> createHabitType(@RequestBody HabitType habitType) {
		try {
			HabitType _habitType = habitTypeRepository.save(new HabitType(habitType.getName()));
			return new ResponseEntity<>(_habitType , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/habit")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteHabitType(@RequestParam Long id) {
		try {
			habitTypeRepository.deleteById(id);
		} catch (Exception e) {

		}
	}
	@PatchMapping("/habit")
	@PreAuthorize("hasRole('ADMIN')")
	public void modifyHabitType(@RequestParam Long id,@RequestParam String name) {
		try {
			habitTypeRepository.modifyName(id,name);
		} catch (Exception e) {

		}
	}
}