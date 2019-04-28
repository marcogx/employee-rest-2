package com.example.employeerestdemo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDao {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDao(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long empId) {
        return employeeRepository.findById(empId).get();
    }

    public Long save(Employee employee) {
        Employee res = employeeRepository.save(employee);
        return res.getId();
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
