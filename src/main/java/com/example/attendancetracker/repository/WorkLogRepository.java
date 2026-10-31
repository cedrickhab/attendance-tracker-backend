package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkLogRepository extends JpaRepository<WorkLog, UUID> {

}