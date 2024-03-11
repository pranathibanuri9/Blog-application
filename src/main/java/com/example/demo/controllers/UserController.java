package com.example.demo.controllers;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.UserDto;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
//@SecurityRequirement(name = "authBearer")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    private ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto){
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> upDateUser( @Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer user_id){
      UserDto updateUser=this.userService.updateUser(userDto,user_id);
      return ResponseEntity.ok(updateUser);

    }

//    @PreAuthorize("hasRole('Role_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer user_id){
        this.userService.deleteUser(user_id);
        return new ResponseEntity<ApiResponse>( new ApiResponse("user deleted successfully",true),HttpStatus.OK);

    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }



}
