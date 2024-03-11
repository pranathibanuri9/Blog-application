package com.example.demo.services.impl;

import com.example.demo.config.AppConstants;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.execeptions.ResourceNotFoundExeception;
import com.example.demo.payloads.UserDto;
import com.example.demo.repositories.RoleRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user=this.modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       Role role=roleRepo.findById(AppConstants.NORMAL_USER).get();
       user.getRoles().add(role);
       User newuser=this.userRepo.save(user);


        return this.modelMapper.map(newuser,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser=this.userRepo.save(user);

        return this.usereToDto(savedUser) ;
    }

    @Override
    public UserDto updateUser(UserDto userdto,Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundExeception("User", "id", userId));

         user.setName(userdto.getName());
         user.setEmail(userdto.getEmail());
         user.setPassword(userdto.getPassword());
         user.setAbout(userdto.getAbout());
         User updateduser=this.userRepo.save(user);

         UserDto userDto1=this.usereToDto(updateduser);


        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExeception("user","Id",userId));
        return this.usereToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users=this.userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user->this.usereToDto(user)).collect(Collectors.toList());
        return userDtos;


    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundExeception("user","Id",userId));
        this.userRepo.delete(user);


    }

    public User dtoToUser(UserDto userdto){
        User user=this.modelMapper.map(userdto,User.class);
//        User user=new User();
//        user.setId(userdto.getId());
//        user.setEmail(userdto.getEmail());
//        user.setName(userdto.getName());
//        user.setPassword(userdto.getPassword());
//        user.setAbout(userdto.getAbout());
        return user;
    }
    public UserDto usereToDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setEmail(user.getEmail());
//        userDto.setName(user.getName());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;

    }
}
