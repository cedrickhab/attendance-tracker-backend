package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Supervisor;
import com.example.attendancetracker.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    public String save(Supervisor supervisor) {
        supervisorRepository.save(supervisor);
        return "Supervisor saved successfully";
    }

    public List<Supervisor> getAll() {
        return supervisorRepository.findAll();
    }

    public Optional<Supervisor> getById(UUID id) {
        return supervisorRepository.findById(id);
    }

    public String update(UUID id, Supervisor supervisor) {
        Optional<Supervisor> existing = supervisorRepository.findById(id);
        if (existing.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        Supervisor oldSupervisor = existing.get();
        oldSupervisor.setFirstName(supervisor.getFirstName());
        oldSupervisor.setLastName(supervisor.getLastName());
        oldSupervisor.setEmail(supervisor.getEmail());
        oldSupervisor.setBranch(supervisor.getBranch());
        oldSupervisor.setDepartments(supervisor.getDepartments());
        supervisorRepository.save(oldSupervisor);
        return "Supervisor updated successfully";
    }

    public String delete(UUID id) {
        if (!supervisorRepository.existsById(id)) {
            return "The supervisor with that id does not exist";
        }
        supervisorRepository.deleteById(id);
        return "Supervisor deleted successfully";
    }
    public List<Supervisor> getByDepartment(String name) {
        return supervisorRepository.findSupervisorsByDepartmentName(name);
    }
}