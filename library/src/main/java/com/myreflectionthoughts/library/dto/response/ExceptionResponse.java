package com.myreflectionthoughts.library.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExceptionResponse {
    private String error;
    private String errorMessage;
}
