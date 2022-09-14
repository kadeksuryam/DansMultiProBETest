package com.surya.dansbetest.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginUserRequestDTO {
    @NotBlank(message = "username cannot be null or blank")
    @Size(min = 5, message = "username length minimum 5")
    private String username;

    @NotBlank(message = "password cannot be null or blank")
    @Size(min = 8, message = "password length minimum 8")
    private String password;
}
