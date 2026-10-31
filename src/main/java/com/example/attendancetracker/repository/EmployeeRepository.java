package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    List<Employee> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT s.employee FROM Shift s WHERE s.supervisor.id = :supervisorId")
    List<Employee> findEmployeesOfSupervisor(@Param("supervisorId") UUID supervisorId);
}