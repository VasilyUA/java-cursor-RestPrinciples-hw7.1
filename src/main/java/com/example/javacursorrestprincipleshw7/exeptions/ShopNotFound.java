package com.example.javacursorrestprincipleshw7.exeptions;

public class ShopNotFound extends RuntimeException{
    public ShopNotFound(String message) {
        super(message);
    }
}
