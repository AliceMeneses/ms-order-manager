package com.majella.ordermanager.core.exception;

public class OrderNotFoundException extends DocumentNotFoundException {
    public OrderNotFoundException(String id) {
        super(String.format("There isn't order for id %s", id));
    }

}
