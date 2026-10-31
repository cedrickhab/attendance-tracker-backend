package com.example.attendancetracker.repository;

import com.example.attendancetracker.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {

    @Query("SELECT l FROM Branch l WHERE NOT EXISTS (SELECT p FROM Supervisor p WHERE p.branch = l) ORDER BY l.branchNumber")
    List<Branch> findFreeBranches();
}