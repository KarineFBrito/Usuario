package com.example.Usuarios.web.exception;

import com.example.Usuarios.exception.EntityNotFoundException;
import com.example.Usuarios.exception.PasswordInvalidException;
import com.example.Usuarios.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {

    private  final MessageSource messageSource;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroMessage> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = "Dados de entrada inválidos. Verifique os campos.";

        ErroMessage erro = new ErroMessage(
                request,
                HttpStatus.BAD_REQUEST,
                errorMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErroMessage> handlePasswordInvalidException(PasswordInvalidException ex, HttpServletRequest request){
        log.error("Api error", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroMessage> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        Object[] params = new Object[]{ex.getRecurso(), ex.getCodigo()};
        String message = messageSource.getMessage("exception.entityNotFoundException", params , request.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.NOT_FOUND, message));
    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErroMessage> handleUniqueViolationException(UsernameUniqueViolationException ex, HttpServletRequest request){
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.CONFLICT, "Campo(s) invalido(s)"));
    }

}
