package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import com.example.attendancetracker.entity.Shift;

public interface WorkLogRepository extends JpaRepository<WorkLog, UUID> {
    @Query("SELECT d FROM WorkLog d WHERE d.shift.employee.staffNumber = :staffNumber ORDER BY d.logDate DESC")
    List<WorkLog> findWorkLogsOfEmployee(@Param("staffNumber") String staffNumber);

    boolean existsByShift(Shift shift);
}