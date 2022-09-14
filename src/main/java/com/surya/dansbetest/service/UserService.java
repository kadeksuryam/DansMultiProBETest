package com.surya.dansbetest.service;

import com.surya.dansbetest.dto.ErrorResponse;
import com.surya.dansbetest.dto.SuccessResponse;
import com.surya.dansbetest.dto.request.LoginUserRequestDTO;
import com.surya.dansbetest.dto.request.RegisterUserRequestDTO;
import io.vavr.control.Either;

public interface UserService {
    Either<ErrorResponse, SuccessResponse> registerUser(RegisterUserRequestDTO reqDTO);
    Either<ErrorResponse, SuccessResponse> loginUser(LoginUserRequestDTO reqDTO);
}
