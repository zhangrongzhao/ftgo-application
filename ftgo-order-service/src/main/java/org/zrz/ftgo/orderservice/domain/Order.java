package org.zrz.ftgo.orderservice.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private Long consumerId;
    private Long restaurantId;

    @Embedded
    private OrderLineItems orderLineItems;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    private PaymentInformation paymentInformation;

    @Embedded
    private Money orderMinimum = new Money(Integer.MAX_VALUE);

    private Order(){ }
    public Order(long consumerId, long restaurantId, DeliveryInformation deliveryInformation, List<OrderLineItem> orderLineItems){
        this.consumerId=consumerId;
        this.restaurantId=restaurantId;
        this.orderLineItems=new OrderLineItems(orderLineItems);
        this.deliveryInformation=deliveryInformation;
        this.state = OrderState.APPROVAL_PENDING;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }
    public OrderState getState() {
        return state;
    }
    public Long getConsumerId() {
        return consumerId;
    }
    public Long getRestaurantId() {
        return restaurantId;
    }
    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }
    public DeliveryInformation getDeliveryInformation() {
        return deliveryInformation;
    }

    public Money getOrderTotal(){ return orderLineItems.orderTotal();}

    /******领域业务******begin*****/
    public List<OrderDomainEvent> cancel(){
        switch(state){
            case APPROVED:
                this.state = OrderState.CANCEL_PENDING;
                return Collections.emptyList();
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    public List<OrderDomainEvent> undoPendingCancel(){
        switch(state){
            case CANCEL_PENDING:
                this.state = OrderState.APPROVED;
                return Collections.emptyList();
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    public List<OrderDomainEvent> noteCancelled(){
        switch(state){
            case CANCEL_PENDING:
                this.state = OrderState.CANCELLED;
                return Collections.singletonList(new OrderCancelled());
            default:
                throw new UnsupportedStateTransitionException(state);
        }

    }
    public List<OrderDomainEvent> noteApproved(){
        switch(state){
            case APPROVAL_PENDING:
                this.state=OrderState.APPROVED;
                return Collections.singletonList(new OrderAuthorized());
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    public List<OrderDomainEvent> noteRejected(){
        switch(state){
            case APPROVAL_PENDING:
                this.state=OrderState.REJECTED;
                return Collections.singletonList(new OrderRejected());
            default:
                throw new UnsupportedStateTransitionException(state);
        }

    }
    public List<OrderDomainEvent> noteReversingAuthorization(){return null;}
    public ResultWithDomainEvents<LineItemQuantityChange,OrderDomainEvent> revise(OrderRevision orderRevision){
        switch(state){
            case OrderState.APPROVED:
                LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);
        }

    }
    public List<OrderDomainEvent> rejectRevision(){ return null; }
    public List<OrderDomainEvent> confirmRevision(OrderRevision orderRevision){ return null;}
    /******领域业务******end*****/
}
