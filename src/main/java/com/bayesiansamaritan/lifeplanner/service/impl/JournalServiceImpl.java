package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Journal.Journal;
import com.bayesiansamaritan.lifeplanner.model.Journal.JournalType;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Journal.JournalTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.JournalResponse;
import com.bayesiansamaritan.lifeplanner.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    JournalTypeRepository journalTypeRepository;

    @Override
    public List<JournalResponse> getAllJournals(Long userId, String journalTypeName){

        JournalType journalType = journalTypeRepository.findByNameAndUserId(journalTypeName,userId);
        List<Journal> journals= journalRepository.findByUserIdAndJournalTypeId(userId,journalType.getId());
        List<JournalResponse> journalResponses = new ArrayList<>();
        for (Journal journal: journals){
            JournalResponse journalResponse = new JournalResponse(journal.getId(),journal.getCreatedAt(),journal.getUpdatedAt(),journal.getName(),journalTypeName,
                    journal.getText(),journal.getActive(),journal.getHidden(),journal.getCompleted());
            journalResponses.add(journalResponse);
        }
        return journalResponses;
    };

    @Override
    public Journal createJournal(Long userId, String name, String journalTypeName, String text, Boolean hidden){
        JournalType journalType = journalTypeRepository.findByNameAndUserId(journalTypeName,userId);
        Journal journal = journalRepository.save(new Journal(name,journalType.getId(),hidden,userId,text));
        return journal;
    };
}
