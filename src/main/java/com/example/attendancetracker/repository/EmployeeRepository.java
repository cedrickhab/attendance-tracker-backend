package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}