package com.example.employeerestdemo.controller;


import com.example.employeerestdemo.model.Cache;
import com.example.employeerestdemo.model.Employee;
import com.example.employeerestdemo.model.EmployeeDao;
import com.example.employeerestdemo.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1")
public class EmployeeRestControllerBackup {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private Cache cache;

    // 1. @RequestMapping(path = "/employee", method = RequestMethod.POST)
    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee input) { //2.
        Long id = employeeDao.save(input);
        Employee res = employeeDao.getEmployeeById(id); // 3. for default/auto gen value from DB
        cache.put(id, res); // 4. firstly no show cache, add later
        return res;  // 5. Mention when change return value of Dao to Employee
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

    @PutMapping("/employees/{empId}")
    public Employee updateEmployee(@PathVariable Long empId, @RequestBody Employee newInput) {
        Employee exist = employeeDao.getEmployeeById(empId);
        if (exist == null) {
            throw new IllegalArgumentException("Id not exist!");
        }
        exist.setFirstName(newInput.getFirstName());
        exist.setLastName(newInput.getLastName());
        exist.setAge(newInput.getAge());
        employeeDao.save(exist);
        cache.put(empId, exist);
        return exist;
    }
//
//
//
//
//
    // bench mark // automation
    // batch processing // optimize I/O

    @GetMapping("/employees/{empId}")
    public Object getEmployeeById(@PathVariable Long empId) {
        Employee cacheEmp = (Employee) cache.get(empId);
        if (cacheEmp != null) {
            return cacheEmp;
        }
        Object res = employeeDao.getEmployeeById(empId);
        if (res != null) {
            cache.put(empId, res);
        }
        return res;
    }
//
//
//

}
