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
public class ResponseDto {
	private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<UserDto> data;
    private SupportDto support;
    private String status;
}
