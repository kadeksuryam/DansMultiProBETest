package com.surya.dansbetest.controller;

import com.surya.dansbetest.constant.ApiConstant;
import com.surya.dansbetest.dto.request.LoginUserRequestDTO;
import com.surya.dansbetest.dto.request.RegisterUserRequestDTO;
import com.surya.dansbetest.interceptor.AllowAnonymous;
import com.surya.dansbetest.outbound.JobOutboundService;
import com.surya.dansbetest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(ApiConstant.API_VERSION_1 + "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @AllowAnonymous
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody @Valid RegisterUserRequestDTO reqDTO) {
        return userService.registerUser(reqDTO).fold(
                errorResponse -> new ResponseEntity(errorResponse, errorResponse.getStatus()),
                successResponse -> new ResponseEntity(successResponse, HttpStatus.OK)
        );
    }

    @AllowAnonymous
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody @Valid LoginUserRequestDTO reqDTO) {
        return userService.loginUser(reqDTO).fold(
                errorResponse -> new ResponseEntity(errorResponse, errorResponse.getStatus()),
                successResponse -> new ResponseEntity(successResponse, HttpStatus.OK)
        );
    }
}
