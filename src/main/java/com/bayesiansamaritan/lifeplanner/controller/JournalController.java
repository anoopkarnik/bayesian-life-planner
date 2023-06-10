package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Journal.Journal;
import com.bayesiansamaritan.lifeplanner.model.Journal.JournalType;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Journal.JournalCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.Journal.JournalModifyRequest;
import com.bayesiansamaritan.lifeplanner.response.JournalResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.JournalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private JournalTypeRepository journalTypeRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public JournalResponse getJournal(@PathVariable("id") Long id) {
        Journal journal = journalRepository.findById(id).get();
        JournalType journalType = journalTypeRepository.findById(journal.getJournalTypeId()).get();
        JournalResponse journalResponse = new JournalResponse(journal.getId(),journal.getCreatedAt(),
                journal.getUpdatedAt(),journal.getName(),journalType.getName(),journal.getText(),journal.getActive(),
                journal.getHidden(),journal.getCompleted());
        return journalResponse;
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<JournalResponse>> getAllJournals(HttpServletRequest request, @RequestParam("journalTypeName") String journalTypeName) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
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
    public ResponseEntity<Journal> createJournal(HttpServletRequest request, @RequestBody JournalCreateRequest journalCreateRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Journal journal = journalService.createJournal(userId,journalCreateRequest.getName(),journalCreateRequest.getJournalTypeName(),
                    journalCreateRequest.getText(),journalCreateRequest.getHidden());
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody JournalModifyRequest journalModifyRequest)
    {
        journalRepository.modifyParams(journalModifyRequest.getId(),journalModifyRequest.getName(),journalModifyRequest.getStartDate(),
                journalModifyRequest.getDescription(),journalModifyRequest.getActive(),journalModifyRequest.getHidden(),
                journalModifyRequest.getCompleted(),journalModifyRequest.getText());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        journalRepository.deleteById(id);
    }
}
