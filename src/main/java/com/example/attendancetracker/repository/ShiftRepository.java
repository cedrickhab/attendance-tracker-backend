package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShiftRepository extends JpaRepository<Shift, UUID> {

}