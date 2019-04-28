package com.example.employeerestdemo.controller;

import com.example.employeerestdemo.model.Cache;
import com.example.employeerestdemo.model.Employee;
import com.example.employeerestdemo.model.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

//        POST /employee
//        GET /employee/{empId}
//        PUT api/v1/employee/{empId}
//
//        Cache.put(key, value)
//        Object Cache.get(key); -- if it doesnt exist, returns null
//
//        Employee EmployeeDao.getEmployeeById(empId);
//        Integer EmployeeDao.save(Employee);

@RestController
@RequestMapping("api/v1")
public class EmployeeRestController {

    @Autowired
    private Cache cache;

    @Autowired
    private EmployeeDao employeeDao;

    @PostMapping("employees")
    public Employee createEmployee(@Valid @RequestBody Employee input) {
        Long id = employeeDao.save(input);
        Employee entry = employeeDao.getEmployeeById(id);  // input.setId(id);
        cache.put(id, entry);
        return entry;
    }

    @GetMapping("employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) { // 1
        Object cachedEmp = cache.get(id);  // 2   // add break point here
        if (cachedEmp != null) {
            return (Employee) cachedEmp;
        }
        Employee res = employeeDao.getEmployeeById(id);
        if (res != null) {  //3
            cache.put(id, res);
        }
        return res;
    }

    @PutMapping("/employees/{empId}")
    public Employee updateEmployee(@PathVariable Long empId, @Valid @RequestBody Employee newInput) { //1
        Employee exist = employeeDao.getEmployeeById(empId);
        if (exist == null) {  // 2
            throw new IllegalArgumentException("Employee id not exist!");
        }
        exist.setFirstName(newInput.getFirstName());
        exist.setLastName(newInput.getLastName());
        exist.setAge(newInput.getAge());  // 3
        employeeDao.save(exist); //4
        cache.put(empId, exist);  // 5
        return exist;
    }

    // GET ALL employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(@RequestParam(name = "id" ,required = false) String id) {
        if (id == null || id.isEmpty()) {
            return employeeDao.findAll();
        } else {
            Long empId = Long.parseLong(id);
            List<Employee> res = new ArrayList<>();
            Employee employee = employeeDao.getEmployeeById(empId);
            res.add(employee);
            return res;
        }
    }
}
