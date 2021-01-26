package org.zrz.ftgo.orderservice.domain;

import org.zrz.ftgo.orderservice.exceptions.UnsupportedStateTransitionException;

public class OrderService {

    public void cancelOrder(Order order){
        try{
              order.cancel();
        }catch(UnsupportedStateTransitionException exception){

        }
    }
}
