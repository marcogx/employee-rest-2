package com.example.employeerestdemo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDao {

    @Autowired
    private EmployeeRepository employeeRepository;

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
