package com.springsecuritywithcircuitbreaker.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
