package org.zrz.ftgo.orderservice.domain;

import io.eventuate.tram.events.aggregates.AbstractAggregateDomainEventPublisher;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import org.zrz.ftgo.orderservice.events.OrderDomainEvent;

public class OrderDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Order, OrderDomainEvent> {
    public OrderDomainEventPublisher(DomainEventPublisher eventPublisher){
        super(eventPublisher,Order.class,Order::getId);
    }
}
