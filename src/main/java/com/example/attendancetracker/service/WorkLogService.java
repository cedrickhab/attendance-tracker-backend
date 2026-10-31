package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Shift;
import com.example.attendancetracker.entity.ShiftStatus;
import com.example.attendancetracker.entity.WorkLog;
import com.example.attendancetracker.repository.ShiftRepository;
import com.example.attendancetracker.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    public String save(UUID shiftId, WorkLog workLog) {
        Optional<Shift> foundShift = shiftRepository.findById(shiftId);
        if (foundShift.isEmpty()) {
            return "The shift with that id does not exist";
        }
        Shift shift = foundShift.get();
        if (shift.getStatus() != ShiftStatus.CLOCKED_OUT) {
            return "Work logs are only for CLOCKED_OUT shifts";
        }
        workLog.setLogDate(LocalDate.now());
        workLog.setShift(shift);
        workLogRepository.save(workLog);
        return "Work log saved successfully";
    }


    public List<WorkLog> getByEmployee(String staffNumber) {
        return workLogRepository.findWorkLogsOfEmployee(staffNumber);
    }
}