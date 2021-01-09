package com.solr.sas.controller;

import com.solr.sas.service.Greeting;
import com.solr.sas.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping("/")
    String home() {
        System.out.println(searchService.getRiskAssessor().name);
        return "Hellogrgr h!";
    }

    @GetMapping("/greeting")
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(1, name);
    }
}
