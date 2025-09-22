package com.example.Usuarios.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class ErroMessage {

    private String path;

    private String method;

    private int status;

    private String statusText;

    private String message;

    private Map<String, String> errors;

    public ErroMessage(String path) {

    }

    public ErroMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

}
