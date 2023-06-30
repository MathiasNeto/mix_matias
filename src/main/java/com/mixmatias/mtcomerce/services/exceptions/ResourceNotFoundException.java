package com.mixmatias.mtcomerce.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg){
        super(msg); //Chama a o metodo da superclasse que é a da RuntimeException
    }
}
