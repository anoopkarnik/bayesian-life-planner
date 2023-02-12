package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.model.Journal;
import com.bayesiansamaritan.lifeplanner.repository.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.JournalRepository;
import com.bayesiansamaritan.lifeplanner.request.HabitCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.HabitDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.request.JournalCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.JournalDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.response.JournalResponse;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
import com.bayesiansamaritan.lifeplanner.service.JournalService;
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
@RequestMapping("/api/journal")
public class JournalController {
    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalService journalService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<JournalResponse>> getAllJournals(@RequestParam("userId") Long userId, @RequestParam("journalTypeName") String journalTypeName) {
        try {
            List<JournalResponse> journals = journalService.getAllJournals(userId,journalTypeName);
            if (journals.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(journals , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Journal> createJournal(@RequestBody JournalCreateRequest journalCreateRequest)
    {
        try {
            Journal journal = journalService.createJournal(journalCreateRequest.getUserId(),journalCreateRequest.getName(),journalCreateRequest.getJournalTypeName(),
                    journalCreateRequest.getText(),journalCreateRequest.getHidden());
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody JournalDescriptionRequest journalDescriptionRequest)
    {
        journalRepository.addText(journalDescriptionRequest.getId(),journalDescriptionRequest.getText());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        journalRepository.deleteById(id);
    }
}
