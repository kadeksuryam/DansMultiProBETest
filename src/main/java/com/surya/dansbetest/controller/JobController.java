package com.surya.dansbetest.controller;

import com.surya.dansbetest.constant.ApiConstant;
import com.surya.dansbetest.outbound.JobOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(ApiConstant.API_VERSION_1 + "/jobs")
public class JobController {
    @Autowired
    private JobOutboundService jobOutboundService;

    @GetMapping
    public ResponseEntity getJobs() {
        return jobOutboundService.getJobs().fold(
                errorResponse -> new ResponseEntity(errorResponse, errorResponse.getStatus()),
                successResponse -> new ResponseEntity(successResponse, HttpStatus.OK)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getJob(@PathVariable String id) {
        return jobOutboundService.getJob(id).fold(
                errorResponse -> new ResponseEntity(errorResponse, errorResponse.getStatus()),
                successResponse -> new ResponseEntity(successResponse, HttpStatus.OK)
        );
    }
}
