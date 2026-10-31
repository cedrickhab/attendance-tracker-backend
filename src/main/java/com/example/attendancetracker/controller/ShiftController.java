package com.example.attendancetracker.controller;

import com.example.attendancetracker.entity.Shift;
import com.example.attendancetracker.service.ShiftService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.PatchMapping;
import com.example.attendancetracker.entity.ShiftStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Shift shift) {
        String message = shiftService.save(shift);
        if (message.equals("Shift saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("Shift date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<Shift> shifts = shiftService.getAll();
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Shift> shift = shiftService.getById(id);
        if (shift.isPresent()) {
            return new ResponseEntity<>(shift.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The shift with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Shift shift) {
        String message = shiftService.update(id, shift);
        if (message.equals("Shift updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = shiftService.delete(id);
        if (message.equals("Shift deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("That shift has work logs, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<?> getByStatus(@RequestParam ShiftStatus status) {
        List<Shift> shifts = shiftService.getByStatus(status);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }
    @GetMapping("/between")
    public ResponseEntity<?> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<Shift> shifts = shiftService.getBetween(startDate, endDate);
        return new ResponseEntity<>(shifts, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam ShiftStatus status) {
        String message = shiftService.updateStatus(id, status);
        if (message.equals("Shift status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("The shift with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<?> countPerStatus() {
        List<Object[]> rows = shiftService.countPerStatus();
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }

}