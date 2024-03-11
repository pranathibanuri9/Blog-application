package com.example.demo.services;

import com.example.demo.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerNewUser(UserDto user);

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUser();
    void deleteUser(Integer userId);





}
