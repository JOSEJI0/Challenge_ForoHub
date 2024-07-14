package com.example.alura.ForoHub.handlers;

import com.example.alura.ForoHub.exceptions.PermissionException;
import com.example.alura.ForoHub.exceptions.RoleException;
import com.example.alura.ForoHub.exceptions.TopicException;
import com.example.alura.ForoHub.exceptions.UsuarioException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ModelHandler {
    @ExceptionHandler(PermissionException.class)
    public String handlePermissionException(PermissionException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(TopicException.class)
    public String handleTopicException(TopicException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UsuarioException.class)
    public String handleUserException(UsuarioException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(RoleException.class)
    public String handleRoleException(RoleException exception) {
        return exception.getMessage();
    }

}