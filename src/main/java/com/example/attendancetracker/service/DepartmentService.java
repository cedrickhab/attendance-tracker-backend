package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Department;
import com.example.attendancetracker.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public String save(Department department) {
        departmentRepository.save(department);
        return "Department saved successfully";
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getById(UUID id) {
        return departmentRepository.findById(id);
    }

    public String update(UUID id, Department department) {
        Optional<Department> existing = departmentRepository.findById(id);
        if (existing.isEmpty()) {
            return "The department with that id does not exist";
        }
        Department oldDepartment = existing.get();
        oldDepartment.setName(department.getName());
        departmentRepository.save(oldDepartment);
        return "Department updated successfully";
    }

    public String delete(UUID id) {
        if (!departmentRepository.existsById(id)) {
            return "The department with that id does not exist";
        }
        departmentRepository.deleteById(id);
        return "Department deleted successfully";
    }
    public List<Department> getUnused() {
        return departmentRepository.findUnusedDepartments();
    }
}