package com.example.CompProductSystem.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    String message;
    T data;


}
