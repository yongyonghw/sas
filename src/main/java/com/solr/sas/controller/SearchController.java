package com.solr.sas.controller;

import com.solr.sas.service.Employee;
import com.solr.sas.service.Greeting;
import com.solr.sas.service.SearchService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchController {

    final SearchService searchService;

    @RequestMapping("/")
    String home() {
        System.out.println(searchService.getRiskAssessor().name);
        return "Hellogrgr h!";
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(1, name);
    }
}
