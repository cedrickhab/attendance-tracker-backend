package com.example.attendancetracker.controller;

import com.example.attendancetracker.entity.Supervisor;
import com.example.attendancetracker.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;
@RestController
@RequestMapping("/api/supervisors")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Supervisor supervisor) {
        String message = supervisorService.save(supervisor);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<Supervisor> supervisors = supervisorService.getAll();
        return new ResponseEntity<>(supervisors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Supervisor> supervisor = supervisorService.getById(id);
        if (supervisor.isPresent()) {
            return new ResponseEntity<>(supervisor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The supervisor with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Supervisor supervisor) {
        String message = supervisorService.update(id, supervisor);
        if (message.equals("Supervisor updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = supervisorService.delete(id);
        if (message.equals("Supervisor deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/by-department")
    public ResponseEntity<?> getByDepartment(@RequestParam String name) {
        List<Supervisor> supervisors = supervisorService.getByDepartment(name);
        return new ResponseEntity<>(supervisors, HttpStatus.OK);
    }

    @PutMapping("/{supervisorId}/departments/{departmentId}")
    public ResponseEntity<?> addDepartment(@PathVariable UUID supervisorId, @PathVariable UUID departmentId) {
        String message = supervisorService.addDepartment(supervisorId, departmentId);
        if (message.equals("Department added to the supervisor")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("The supervisor already has that department")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{supervisorId}/branch")
    public ResponseEntity<?> changeBranch(@PathVariable UUID supervisorId, @RequestParam int branchNumber) {
        String message = supervisorService.changeBranch(supervisorId, branchNumber);

        if (message.equals("Branch changed successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else if (message.equals("That branch is taken by another supervisor")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}