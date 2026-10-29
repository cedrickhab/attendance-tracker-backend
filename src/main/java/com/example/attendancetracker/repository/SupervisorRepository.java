package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {

}