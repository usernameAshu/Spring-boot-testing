package com.mohanty.testing.controller;

import com.mohanty.testing.dao.EmployeeRepository;
import com.mohanty.testing.model.Employee;
import com.mohanty.testing.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @PostConstruct
    public void postConstruct() {
        Employee employee = new Employee(UUID
                .randomUUID()
                .toString(), "Ashutosh", "Technology");
        Employee employee1 = new Employee(UUID
                .randomUUID()
                .toString(), "Anil", "Development");
        Employee employee2 = new Employee(UUID
                .randomUUID()
                .toString(), "Midhun", "Design");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);employeeList.add(employee1);employeeList.add(employee2);

        repository.saveAll(employeeList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response addEmployee(@RequestBody Employee employee) {
        Employee emp = repository.save(employee);
        return new Response(emp.getId() + " :inserted", Boolean.TRUE);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployee() {
        List<Employee> employeeList = repository.findAll();
       // return new Response("records count: " + employeeList.size(), Boolean.TRUE);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PutMapping
    public Response updateEmployee(@RequestBody Employee employee) {
        if (repository.existsById(employee.getId())) {
            Employee save = repository.save(employee);
            return new Response("Record Updated: " + save.toString(), Boolean.TRUE);
        } else {
            Employee save = repository.save(employee);
            return new Response("Record Inserted: " + save.toString(), Boolean.TRUE);
        }
    }
}
