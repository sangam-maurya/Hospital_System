package com.demo.service;

import com.demo.entity.Visit;
import com.demo.repositery.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;

    public Visit recordVisit(Visit visit) {
        return visitRepository.save(visit);
    }
}