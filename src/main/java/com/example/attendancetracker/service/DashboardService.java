package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.ShiftStatus;
import com.example.attendancetracker.repository.BranchRepository;
import com.example.attendancetracker.repository.EmployeeRepository;
import com.example.attendancetracker.repository.ShiftRepository;
import com.example.attendancetracker.repository.SupervisorRepository;
import com.example.attendancetracker.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("employees", employeeRepository.count());
        summary.put("supervisors", supervisorRepository.count());
        summary.put("freeBranches", (long) branchRepository.findFreeBranches().size());
        summary.put("shiftsToday", shiftRepository.countByShiftDate(LocalDate.now()));
        summary.put("rostered", shiftRepository.countByStatus(ShiftStatus.ROSTERED));
        summary.put("workLogs", workLogRepository.count());
        return summary;
    }
}