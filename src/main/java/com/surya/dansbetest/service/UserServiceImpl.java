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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public Either<ErrorResponse, SuccessResponse> registerUser(RegisterUserRequestDTO reqDTO) {
        // check username uniqueness
        Optional<User> dbUser = userRepository.findByUsername(reqDTO.getUsername());
        if(dbUser.isPresent()) {
            ErrorResponse.ErrorBody errBody1 = ErrorResponse.ErrorBody.builder()
                    .message("Username already registered")
                    .moreInfo(String.format("Please choose another username other than: %s", reqDTO.getEmail()))
                    .build();

            ErrorResponse error = ErrorResponse.builder()
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .message("Username conflict")
                    .errors(Arrays.asList(errBody1))
                    .build();

            return Either.left(error);
        }

        User toDb = modelMapper.map(reqDTO, User.class);
        toDb.setPassword(passwordUtil.hashPassword(toDb.getPassword()));
        userRepository.save(toDb);

        return Either.right(new SuccessResponse("Successfully register user"));
    }

    @Override
    public Either<ErrorResponse, SuccessResponse> loginUser(LoginUserRequestDTO reqDTO) {
        Optional<User> dbUser = userRepository.findByUsername(reqDTO.getUsername());
        if (!dbUser.isPresent()) {
            ErrorResponse.ErrorBody errBody1 = ErrorResponse.ErrorBody.builder()
                    .message("Username not found")
                    .moreInfo(String.format("User with username %s not found", reqDTO.getUsername()))
                    .build();

            ErrorResponse error = ErrorResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Invalid Credentials")
                    .errors(Arrays.asList(errBody1))
                    .build();

            return Either.left(error);
        }

        if (passwordUtil.isPasswordSame(reqDTO.getPassword(), dbUser.get().getPassword())) {
            LoginUserResponseDTO resDto = new LoginUserResponseDTO();
            resDto.setUserId(dbUser.get().getId());
            resDto.setAccessToken(jwtUtil.generateJwtAccessToken(dbUser.get()));

            return Either.right(new SuccessResponse<>("Successfully login", resDto));
        } else {
            ErrorResponse.ErrorBody errBody1 = ErrorResponse.ErrorBody.builder()
                    .message("Wrong password")
                    .build();

            ErrorResponse error = ErrorResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Invalid Credentials")
                    .errors(Arrays.asList(errBody1))
                    .build();

            return Either.left(error);
        }
    }
}
