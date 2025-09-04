package com.github.ots.common.Response.model;

import com.github.ots.common.error.model.ErrorDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String code;
    private String message;
    private Object data;
    private ErrorDetails error;

}
