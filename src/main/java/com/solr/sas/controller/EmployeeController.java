package com.solr.sas.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.solr.sas.exception.EmployeeNotFoundException;
import com.solr.sas.repository.EmployeeRepository;
import com.solr.sas.service.Employee;
import com.solr.sas.service.EmployeeModelAssembler;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler employeeModelAssembler;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {

//        List<EntityModel<Employee>> employees = repository.findAll().stream()
//                                                          .map(employee -> EntityModel.of(employee,
//                                                                                          linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
//                                                                                          linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
//                                                          .collect(Collectors.toList());
//
//        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
        List<EntityModel<Employee>> employees = repository.findAll().stream() //
                                                          .map(employeeModelAssembler::toModel) //
                                                          .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    // end::get-aggregate-root[]

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item
    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = repository.findById(id) //
                                      .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<EntityModel<Employee>> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        Employee rt = repository.findById(id)
                         .map(employee -> {
                             employee.setName(newEmployee.getName());
                             employee.setRole(newEmployee.getRole());
                             return repository.save(employee);
                         })
                         .orElseGet(() -> {
                             newEmployee.setId(id);
                             return repository.save(newEmployee);
                         });
        EntityModel<Employee> entityModel = employeeModelAssembler.toModel(rt);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
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