package org.zrz.ftgo.orderservice.events;

import org.zrz.ftgo.orderservice.domain.Money;
import org.zrz.ftgo.orderservice.domain.OrderRevision;

public class OrderRevisionProposed implements OrderDomainEvent {
    private final OrderRevision orderRevision;
    private final Money currentOrderTotal;
    private final Money newOrderTotal;

    public OrderRevisionProposed(OrderRevision orderRevision,Money currentOrderTotal,Money newOrderTotal){
        this.orderRevision=orderRevision;
        this.currentOrderTotal=currentOrderTotal;
        this.newOrderTotal=newOrderTotal;
    }

    public OrderRevision getOrderRevision() {
        return orderRevision;
    }
    public Money getCurrentOrderTotal() {
        return currentOrderTotal;
    }
    public Money getNewOrderTotal() {
        return newOrderTotal;
    }
}
