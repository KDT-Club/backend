package com.ac.su.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse<T> {
    private String message;
    private T data;
}
