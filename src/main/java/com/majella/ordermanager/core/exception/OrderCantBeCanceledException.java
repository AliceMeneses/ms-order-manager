package com.majella.ordermanager.core.exception;

public class OrderCantBeCanceledException extends BusinessException{
    public OrderCantBeCanceledException(String id) {
        super(String.format("The order with id %s cannot be cancelled because it is ready", id));
    }

}
