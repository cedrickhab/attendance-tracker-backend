package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Branch;
import com.example.attendancetracker.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public String save(Branch branch) {
        branchRepository.save(branch);
        return "Branch saved successfully";
    }

    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getById(UUID id) {
        return branchRepository.findById(id);
    }

    public String update(UUID id, Branch branch) {
        Optional<Branch> existing = branchRepository.findById(id);
        if (existing.isEmpty()) {
            return "The branch with that id does not exist";
        }
        Branch oldBranch = existing.get();
        oldBranch.setBranchNumber(branch.getBranchNumber());
        oldBranch.setName(branch.getName());
        branchRepository.save(oldBranch);
        return "Branch updated successfully";
    }

    public String delete(UUID id) {
        if (!branchRepository.existsById(id)) {
            return "The branch with that id does not exist";
        }
        branchRepository.deleteById(id);
        return "Branch deleted successfully";
    }
}