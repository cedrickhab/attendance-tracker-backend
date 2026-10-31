package com.example.attendancetracker.service;

import com.example.attendancetracker.entity.Employee;
import com.example.attendancetracker.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String save(Employee employee) {
        employeeRepository.save(employee);
        return "Employee saved successfully";
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getById(UUID id) {
        return employeeRepository.findById(id);
    }

    public String update(UUID id, Employee employee) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isEmpty()) {
            return "The employee with that id does not exist";
        }
        Employee oldEmployee = existing.get();
        oldEmployee.setFirstName(employee.getFirstName());
        oldEmployee.setLastName(employee.getLastName());
        oldEmployee.setStaffNumber(employee.getStaffNumber());
        oldEmployee.setEmail(employee.getEmail());
        oldEmployee.setPhone(employee.getPhone());
        employeeRepository.save(oldEmployee);
        return "Employee updated successfully";
    }

    public String delete(UUID id) {
        if (!employeeRepository.existsById(id)) {
            return "The employee with that id does not exist";
        }
        employeeRepository.deleteById(id);
        return "Employee deleted successfully";
    }

    public List<Employee> getByLastName(String lastName) {
        return employeeRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
    public List<Employee> getBySupervisor(UUID supervisorId) {
        return employeeRepository.findEmployeesOfSupervisor(supervisorId);
    }
}