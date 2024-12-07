package com.demo.controller;

import com.demo.entity.Visit;
import com.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visits")
public class VisitController {
    @Autowired
    private VisitService visitService;

    @PostMapping("/record")
    public ResponseEntity<Visit> recordVisit(@RequestBody Visit visit) {
        Visit recordedVisit = visitService.recordVisit(visit);
        return ResponseEntity.ok(recordedVisit);
    }
}
