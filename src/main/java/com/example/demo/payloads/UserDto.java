package com.example.demo.payloads;

import com.example.demo.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4,message = "user name must be min of 4 characters")
    private String name;

    @Email(message = "email address is not valid")
    @NotEmpty
    @Column(unique = true)
    private String email;

//    @NotEmpty
//    @Size(min=3,max=10,message = "password must be min of 3 chars and max of 10 chars!!")

    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDto> roles=new HashSet<>();

    @JsonIgnore
    public String getPassword(){
        return this.password;
    }
    @JsonProperty
    public void setPassword(String password){
        this.password=password;
    }


}
