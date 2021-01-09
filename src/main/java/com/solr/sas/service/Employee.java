package com.solr.sas.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor()
public class Employee {

    private @Id @GeneratedValue Long id;

    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    private String role;


}
