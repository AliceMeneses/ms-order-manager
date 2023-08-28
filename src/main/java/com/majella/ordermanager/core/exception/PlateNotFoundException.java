package com.majella.ordermanager.core.exception;

public class PlateNotFoundException extends DocumentNotFoundException {

    public PlateNotFoundException(String id) {
        super(String.format("There isn't plate for id %s", id));
    }

}
