package com.myreflectionthoughts.library.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private String error;
    private String errorMessage;
}
