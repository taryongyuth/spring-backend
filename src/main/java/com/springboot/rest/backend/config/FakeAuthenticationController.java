package com.springboot.rest.backend.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This is a fake controller written merely for showing the login and logout apis in the
 * swagger documentation allowing users to get the authorisation token.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Auth")
public class FakeAuthenticationController {

    @ApiOperation("Generate Token API")
    @PostMapping("/auth")
    public void fakeLogin(@RequestBody @Valid LoginRequest loginRequest) {
        throw new IllegalStateException("This method shouldn't be called.");
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class LoginRequest {
        private String username;
        private String password;
    }
}