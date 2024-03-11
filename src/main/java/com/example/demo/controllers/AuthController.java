package com.example.demo.controllers;


import com.example.demo.Security.JwtHelper;
import com.example.demo.entities.User;
import com.example.demo.execeptions.ApiException;
import com.example.demo.payloads.JwtAuthRequest;
import com.example.demo.payloads.JwtAuthResponse;
import com.example.demo.payloads.UserDto;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/auth/")

public class AuthController {
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login( @Valid @RequestBody JwtAuthRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtAuthResponse response = JwtAuthResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        response.setUser(this.modelMapper.map((User)userDetails,UserDto.class));






        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new ApiException(" Invalid Username or Password  !!");
        }

    }

//    register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
        UserDto registereduser=this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registereduser,HttpStatus.CREATED);

    }



    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }




}