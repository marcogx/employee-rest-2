package com.example.employeerestdemo.controller;


import com.example.employeerestdemo.model.Cache;
import com.example.employeerestdemo.model.Employee;
import com.example.employeerestdemo.model.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//[API implementation]
//
//    POST /employee
//    GET /employee/{empId}
//    GET /employee  (all employees)
//    PUT /employee/{empId}
//
//
//
//    Cache.put(key, value)
//    Object Cache.get(key); -- if it doesnt exist, returns null
//
//    Employee EmployeeDao.findById(empId);

//    Employee EmployeeDao.save(Employee);


// server response status code
// 200 OK
// 404 NOT found
// 500 Internal Server Error

@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private Cache cache;


    @PutMapping("/employee/{empId}")
    public Employee updateEmployee(@PathVariable Long empId, @RequestBody Employee newInput) {
        Employee exist = employeeDao.findById(empId).get();
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


//    @RequestMapping(path = "/employee", method = RequestMethod.POST)
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee input) {
        Employee res = employeeDao.save(input);
        cache.put(res.getId(), res);
        return res;
    }
    // bench mark // automation

    // batch processing // optimize I/O


    @GetMapping("/employee/{empId}")
    public Object getEmployeeById(@PathVariable Long empId) {
        Employee cacheEmp = (Employee) cache.get(empId);
        if (cacheEmp != null) {
            return cacheEmp;
        }
        Object res = employeeDao.findById(empId);
        if (res != null) {
            cache.put(empId, res);
        }
        return res;

//        Employee cacheEmp = (Employee) cache.get(empId);
//        if (cacheEmp != null) {
//            return cacheEmp;
//        }
//        Employee res = employeeDao.getEmployeeById(empId);
//        if (res != null) {
//            cache.put(empId, res);
//        }
//        return res;
    }



    // GET ALL EMployees
    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }
}
