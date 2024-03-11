package com.example.demo.payloads;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtAuthResponse {
    private String jwtToken;
    private String username;
    private UserDto user;


}
