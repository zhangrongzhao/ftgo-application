package org.zrz.ftgo.orderservice.events;

import org.zrz.ftgo.orderservice.domain.Address;
import org.zrz.ftgo.orderservice.domain.OrderDetails;

public class OrderCreated implements OrderDomainEvent {
    private OrderDetails orderDetails;
    private Address deliveryAddress;
    private String restaurantName;

    private OrderCreated(){ }
    public OrderCreated(OrderDetails orderDetails, Address deliveryAddress, String restaurantName){
        this.orderDetails = orderDetails;
        this.deliveryAddress = deliveryAddress;
        this.restaurantName=restaurantName;
    }
}
