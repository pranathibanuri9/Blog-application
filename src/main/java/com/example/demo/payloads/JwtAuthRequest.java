package com.example.demo.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@Builder
public class JwtAuthRequest {
    @NotEmpty
    @Size(min=4,message = "user name must be min of 4 characters")

    private String email;
    @NotEmpty
    @Size(min=4,message = "password should be valid")
    private String password;
}
