package com.solr.sas.controller;

import java.util.List;

import com.solr.sas.exception.EmployeeNotFoundException;
import com.solr.sas.repository.EmployeeRepository;
import com.solr.sas.service.Employee;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class EmployeeController {

    private final EmployeeRepository repository;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item
    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return repository.findById(id)
                         .map(employee -> {
                             employee.setName(newEmployee.getName());
                             employee.setRole(newEmployee.getRole());
                             return repository.save(employee);
                         })
                         .orElseGet(() -> {
                             newEmployee.setId(id);
                             return repository.save(newEmployee);
                         });
    }

    @PatchMapping("/employees/{id}")
    Employee replacePartialEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                         .map(employee -> {
                             if(StringUtils.isNotBlank(newEmployee.getName()))
                                 employee.setName(newEmployee.getName());
                             if(StringUtils.isNotBlank(newEmployee.getRole()))
                                 employee.setRole(newEmployee.getRole());
                             return repository.save(employee);
                         })
                         .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}