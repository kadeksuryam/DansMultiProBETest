package com.surya.dansbetest.outbound;

import com.surya.dansbetest.dto.ErrorResponse;
import com.surya.dansbetest.dto.SuccessResponse;
import io.vavr.control.Either;

public interface JobOutboundService {
    Either<ErrorResponse, SuccessResponse> getJobs();
    Either<ErrorResponse, SuccessResponse>  getJob(String id);
}
