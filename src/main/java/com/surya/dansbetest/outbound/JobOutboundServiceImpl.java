package com.surya.dansbetest.outbound;

import com.surya.dansbetest.constant.ApiConstant;
import com.surya.dansbetest.dto.ErrorResponse;
import com.surya.dansbetest.dto.SuccessResponse;
import com.surya.dansbetest.dto.outbound.response.JobResponseDTO;
import com.surya.dansbetest.endpoint.JobAPI;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class JobOutboundServiceImpl implements JobOutboundService{
    private JobAPI jobAPI;

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstant.OUTBOUND_DANS_API_HOST)
                .addConverterFactory(JacksonConverterFactory.create()).build();

        jobAPI = retrofit.create(JobAPI.class);
    }


    public Either<ErrorResponse, SuccessResponse> getJobs() {
        Call<List<JobResponseDTO>> call = jobAPI.getJobs();
        Response<List<JobResponseDTO>> resCall;
        try {
            resCall = call.execute();
        } catch (Exception ex) {
            ErrorResponse err = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Outbound API Call Failed")
                    .build();

            return Either.left(err);
        }

        List<JobResponseDTO> resDto = resCall.body();

        return Either.right(new SuccessResponse("Outbound API Call Success", resDto));
    }

    public Either<ErrorResponse, SuccessResponse> getJob(String id) {
        Call<JobResponseDTO> call = jobAPI.getJob(id);
        Response<JobResponseDTO> resCall;
        try {
            resCall = call.execute();
        } catch (Exception ex) {
            ErrorResponse err = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Outbound API Call Failed")
                    .build();

            return Either.left(err);
        }

        JobResponseDTO resDto = resCall.body();

        return Either.right(new SuccessResponse("Outbound API Call Success", resDto));
    }
}
