package com.solr.sas.service;

import com.solr.sas.controller.RiskAssessor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Data
@Service
public class SearchService {

    private final RiskAssessor riskAssessor;
    private final RiskAssessor riskAssessor2;


}
