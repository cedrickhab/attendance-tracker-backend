package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    @Query("SELECT d FROM Department d WHERE d.supervisors IS EMPTY")
    List<Department> findUnusedDepartments();
}