package pl.napierala.cinemacodechallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginRequest;
import pl.napierala.cinemacodechallenge.extmodel.UserLoginResponse;
import pl.napierala.cinemacodechallenge.extmodel.UserRegisterRequest;
import pl.napierala.cinemacodechallenge.extmodel.UserRegisterResponse;
import pl.napierala.cinemacodechallenge.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a user.",
            description = "Registers a user if the userName is not already used.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserRegisterResponse.class)
                            )
                    )
            }
    )
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UserRegisterResponse register(@RequestBody @Valid final UserRegisterRequest request) {
        return userService.register(request);
    }

    @Operation(
            summary = "Login a user.",
            description = "Checks if the credentials given are correct.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserLoginResponse.class)
                            )
                    )
            }
    )
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UserLoginResponse login(@RequestBody @Valid final UserLoginRequest request) {
        return userService.login(request);
    }
}