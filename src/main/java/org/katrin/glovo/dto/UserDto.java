package org.katrin.glovo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private int id;
    private String email;
    private String phoneNumber;
    private String name;
    //private String password;
    private List<Integer> orders = new ArrayList<>();
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    //private LocalDateTime createdAt;
}

