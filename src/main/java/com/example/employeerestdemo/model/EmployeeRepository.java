package com.example.employeerestdemo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by guang on 9:04 PM 10/27/18.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
