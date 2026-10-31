package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Shift;
import com.example.attendancetracker.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.attendancetracker.entity.ShiftStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import com.example.attendancetracker.repository.WorkLogRepository;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    public String save(Shift shift) {
        if (shift.getShiftDate().isBefore(LocalDate.now())) {
            return "Shift date cannot be in the past";
        }
        boolean busy = shiftRepository.existsBySupervisorIdAndShiftDateAndStatusNot(
                shift.getSupervisor().getId(), shift.getShiftDate(), ShiftStatus.MISSED);
        if (busy) {
            return "Supervisor already has a shift on that date";
        }
        shift.setStatus(ShiftStatus.ROSTERED);
        shiftRepository.save(shift);
        return "Shift saved successfully";
    }

    public List<Shift> getAll() {
        return shiftRepository.findAll();
    }

    public Optional<Shift> getById(UUID id) {
        return shiftRepository.findById(id);
    }

    public String update(UUID id, Shift shift) {
        Optional<Shift> existing = shiftRepository.findById(id);
        if (existing.isEmpty()) {
            return "The shift with that id does not exist";
        }
        Shift oldShift = existing.get();
        oldShift.setShiftDate(shift.getShiftDate());
        oldShift.setStartTime(shift.getStartTime());

        oldShift.setEmployee(shift.getEmployee());
        oldShift.setSupervisor(shift.getSupervisor());
        shiftRepository.save(oldShift);
        return "Shift updated successfully";
    }

    public String delete(UUID id) {
        Optional<Shift> existing = shiftRepository.findById(id);
        if (existing.isEmpty()) {
            return "The shift with that id does not exist";
        }
        if (workLogRepository.existsByShift(existing.get())) {
            return "That shift has work logs, delete them first";
        }
        shiftRepository.deleteById(id);
        return "Shift deleted successfully";
    }
    public List<Shift> getByStatus(ShiftStatus status) {
        return shiftRepository.findByStatusOrderByShiftDateAsc(status);
    }
    public List<Shift> getBetween(LocalDate start, LocalDate end) {
        return shiftRepository.findByShiftDateBetweenOrderByShiftDateAsc(start, end);
    }

    public String updateStatus(UUID id, ShiftStatus newStatus) {
        Optional<Shift> existing = shiftRepository.findById(id);
        if (existing.isEmpty()) {
            return "The shift with that id does not exist";
        }
        Shift shift = existing.get();
        ShiftStatus currentStatus = shift.getStatus();
        boolean allowed = false;
        if (currentStatus == ShiftStatus.ROSTERED) {
            if (newStatus == ShiftStatus.CLOCKED_IN || newStatus == ShiftStatus.MISSED) {
                allowed = true;
            }
        } else if (currentStatus == ShiftStatus.CLOCKED_IN) {
            if (newStatus == ShiftStatus.CLOCKED_OUT || newStatus == ShiftStatus.MISSED) {
                allowed = true;
            }
        }
        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
        shift.setStatus(newStatus);
        shiftRepository.save(shift);
        return "Shift status updated successfully";
    }

}