package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {

    @Query("SELECT p FROM Supervisor p JOIN p.departments c WHERE LOWER(c.name) = LOWER(:name)")
    List<Supervisor> findSupervisorsByDepartmentName(@Param("name") String name);
}