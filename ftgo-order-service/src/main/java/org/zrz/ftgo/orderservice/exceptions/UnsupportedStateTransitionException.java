package org.zrz.ftgo.orderservice.exceptions;

import org.zrz.ftgo.orderservice.domain.OrderState;

public class UnsupportedStateTransitionException extends RuntimeException {
    public UnsupportedStateTransitionException(OrderState state){

    }
}
