package com.surya.dansbetest.service;

import com.surya.dansbetest.dto.ErrorResponse;
import com.surya.dansbetest.dto.SuccessResponse;
import com.surya.dansbetest.dto.request.LoginUserRequestDTO;
import com.surya.dansbetest.dto.request.RegisterUserRequestDTO;
import com.surya.dansbetest.dto.response.LoginUserResponseDTO;
import com.surya.dansbetest.model.User;
import com.surya.dansbetest.repository.UserRepository;
import com.surya.dansbetest.util.JwtUtil;
import com.surya.dansbetest.util.PasswordUtil;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordUtil passwordUtil;
    @Mock
    private JwtUtil jwtUtil;

    private String username;
    private String password;
    private String email;
    private User user;
    private String accessToken;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @BeforeEach
    public void setUp() {
        this.username = "usernameTest";
        this.password = "passwordTest";
        this.email = "test@test.com";
        this.accessToken = "JWT_ACCESS_TOKEN";
        user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
    }

    @Test
    public void whenRegisterUser_givenExistingUsername_shouldReturnError() {
        Optional<User> dbRet = Optional.of(user);
        RegisterUserRequestDTO reqDto = new RegisterUserRequestDTO();
        reqDto.setUsername(username);


        when(userRepository.findByUsername(username)).thenReturn(dbRet);

        Either<ErrorResponse, SuccessResponse> actual =  userService.registerUser(reqDto);

        verify(userRepository).findByUsername(username);
        Assertions.assertFalse(Objects.isNull(actual.getLeft()));
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, actual.getLeft().getStatus());
    }

    @Test
    public void whenLoginUser_givenNonExistingUsername_shouldReturnError() {
        LoginUserRequestDTO reqDto = new LoginUserRequestDTO();
        reqDto.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(null));

        Either<ErrorResponse, SuccessResponse> actual =  userService.loginUser(reqDto);

        verify(userRepository).findByUsername(username);
        Assertions.assertFalse(Objects.isNull(actual.getLeft()));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actual.getLeft().getStatus());
    }

    @Test
    public void whenLoginUser_givenWrongPassword_shouldReturnError() {
        Optional<User> dbRet = Optional.of(user);
        LoginUserRequestDTO reqDto = new LoginUserRequestDTO();
        reqDto.setUsername(username);
        reqDto.setPassword(password + "test");

        when(userRepository.findByUsername(username)).thenReturn(dbRet);
        when(passwordUtil.isPasswordSame(password + "test", password)).thenReturn(false);

        Either<ErrorResponse, SuccessResponse> actual =  userService.loginUser(reqDto);

        verify(userRepository).findByUsername(username);
        verify(passwordUtil).isPasswordSame(password + "test", password);
        Assertions.assertFalse(Objects.isNull(actual.getLeft()));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actual.getLeft().getStatus());
    }

    @Test
    public void whenLoginUser_givenCorrectCredentials_shouldSuccess() {
        Optional<User> dbRet = Optional.of(user);
        LoginUserRequestDTO reqDto = new LoginUserRequestDTO();
        reqDto.setUsername(username);
        reqDto.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(dbRet);
        when(passwordUtil.isPasswordSame(password, password)).thenReturn(true);
        when(jwtUtil.generateJwtAccessToken(user)).thenReturn(accessToken);

        Either<ErrorResponse, SuccessResponse> actual =  userService.loginUser(reqDto);
        LoginUserResponseDTO resDto = (LoginUserResponseDTO) actual.get().getData();

        verify(userRepository).findByUsername(username);
        verify(passwordUtil).isPasswordSame(password, password);
        verify(jwtUtil).generateJwtAccessToken(user);
        Assertions.assertFalse(Objects.isNull(actual.get()));
        Assertions.assertAll("resDto",
                () -> Assertions.assertEquals(user.getId(), resDto.getUserId()),
                () -> Assertions.assertEquals(accessToken, resDto.getAccessToken())
        );
    }
}
