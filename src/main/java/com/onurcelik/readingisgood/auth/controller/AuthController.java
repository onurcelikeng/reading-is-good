package com.onurcelik.readingisgood.auth.controller;

import com.onurcelik.readingisgood.auth.dto.AuthInput;
import com.onurcelik.readingisgood.auth.dto.AuthOutput;
import com.onurcelik.readingisgood.auth.service.impl.UserDetailServiceImpl;
import com.onurcelik.readingisgood.auth.util.JwtUtil;
import com.onurcelik.readingisgood.core.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseDTO<AuthOutput> login(@RequestBody @Valid AuthInput input) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(input.getUsername()));
        return new ResponseDTO<>(HttpStatus.OK, new AuthOutput(token));
    }
}
