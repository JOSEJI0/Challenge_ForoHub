package com.example.alura.ForoHub.exceptions;

import lombok.Getter;

@Getter
public class RoleException extends Exception{
    public RoleException(String message) {super(message);}
}
