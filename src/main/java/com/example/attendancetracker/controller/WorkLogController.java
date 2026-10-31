package com.example.attendancetracker.controller;

import com.example.attendancetracker.entity.WorkLog;
import com.example.attendancetracker.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/work-logs")
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam UUID shiftId, @RequestBody WorkLog workLog) {
        String message = workLogService.save(shiftId, workLog);
        if (message.equals("Work log saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("The shift with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employee/{staffNumber}")
    public ResponseEntity<?> getByEmployee(@PathVariable String staffNumber) {
        List<WorkLog> workLogs = workLogService.getByEmployee(staffNumber);
        return new ResponseEntity<>(workLogs, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = workLogService.delete(id);
        if (message.equals("Work log deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}