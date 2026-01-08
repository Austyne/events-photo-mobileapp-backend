package com.example.controller;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(String message) {
        super(message);
    }
}
