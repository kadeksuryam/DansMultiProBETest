package com.surya.dansbetest.endpoint;

import com.surya.dansbetest.dto.outbound.response.JobResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface JobAPI {
    @GET("recruitment/positions.json")
    Call<List<JobResponseDTO>> getJobs();
    @GET("recruitment/positions/{id}")
    Call<JobResponseDTO> getJob(@Path("id") String id);
}
