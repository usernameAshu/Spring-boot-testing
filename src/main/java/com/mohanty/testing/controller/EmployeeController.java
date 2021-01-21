package com.mohanty.testing.controller;

import com.mohanty.testing.dao.EmployeeRepository;
import com.mohanty.testing.model.Employee;
import com.mohanty.testing.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response addEmployee(@RequestBody Employee employee) {
        Employee emp = repository.save(employee);
        return new Response(emp.getId()+" :inserted",Boolean.TRUE);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getEmployee() {
        List<Employee> employeeList = repository.findAll();
        return new Response("records count: "+employeeList.size(),Boolean.TRUE);
    }
}
