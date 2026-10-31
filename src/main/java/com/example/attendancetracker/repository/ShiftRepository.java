package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.attendancetracker.entity.ShiftStatus;
import java.util.List;

import java.util.UUID;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {
    List<Shift> findByStatusOrderByShiftDateAsc(ShiftStatus status);
}