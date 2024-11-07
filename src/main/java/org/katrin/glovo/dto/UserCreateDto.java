package org.katrin.glovo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private int id;
    //private String email;
    private String phoneNumber;
    private String name;
    private String password;
    private Set<String> roles;
}
