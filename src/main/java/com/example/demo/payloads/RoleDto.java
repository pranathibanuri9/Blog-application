package com.example.demo.payloads;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RoleDto {
    private int id;

    private String name;
}
