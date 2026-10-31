package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Supervisor;
import com.example.attendancetracker.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.attendancetracker.entity.Department;
import com.example.attendancetracker.repository.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

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

    @Transactional
    public String addDepartment(UUID supervisorId, UUID departmentId) {
        Optional<Supervisor> foundSupervisor = supervisorRepository.findById(supervisorId);
        if (foundSupervisor.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        Optional<Department> foundDepartment = departmentRepository.findById(departmentId);
        if (foundDepartment.isEmpty()) {
            return "That department does not exist";
        }
        Supervisor supervisor = foundSupervisor.get();
        Department department = foundDepartment.get();
        if (supervisor.getDepartments().contains(department)) {
            return "The supervisor already has that department";
        }
        supervisor.getDepartments().add(department);
        supervisorRepository.save(supervisor);
        return "Department added to the supervisor";
    }
}