package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.attendancetracker.entity.ShiftStatus;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {
    List<Shift> findByStatusOrderByShiftDateAsc(ShiftStatus status);
    List<Shift> findByShiftDateBetweenOrderByShiftDateAsc(LocalDate start, LocalDate end);
    boolean existsBySupervisorIdAndShiftDateAndStatusNot(UUID supervisorId, LocalDate shiftDate, ShiftStatus status);

    @Query("SELECT s.status, COUNT(s) FROM Shift s GROUP BY s.status")
    List<Object[]> countShiftsPerStatus();
}