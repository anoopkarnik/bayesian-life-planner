package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Journal;
import com.bayesiansamaritan.lifeplanner.response.JournalResponse;

import java.util.List;

public interface JournalService {

    public List<JournalResponse> getAllJournals(Long userId, String journalTypeName);
    public Journal createJournal(Long userId, String name, String journalTypeName, String text, Boolean hidden);
}
