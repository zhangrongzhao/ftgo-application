package org.zrz.ftgo.orderservice.domain;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import org.zrz.ftgo.orderservice.events.*;
import org.zrz.ftgo.orderservice.exceptions.OrderMinimumNotMetException;
import org.zrz.ftgo.orderservice.exceptions.UnsupportedStateTransitionException;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
public class Order {

    public static ResultWithDomainEvents<Order,OrderDomainEvent>
    createOrder(long consumerId,Restaurant restaurant,DeliveryInformation deliveryInformation,List<OrderLineItem> orderLineItems){
        Order order = new Order(consumerId,restaurant.getId(),deliveryInformation,orderLineItems);
        List<OrderDomainEvent> events = singletonList(new OrderCreated(
                new OrderDetails(consumerId,restaurant.getId(),orderLineItems,order.getOrderTotal()),
                null,
                //deliveryInformation.getDeliveryAddress(),
                restaurant.getName()));
        return new ResultWithDomainEvents<Order, OrderDomainEvent>(order,events);
    }

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private Long consumerId;
    private Long restaurantId;

//    @Embedded
//    private OrderLineItems orderLineItems;

    @Embedded
    private DeliveryInformation deliveryInformation;

    @Embedded
    private PaymentInformation paymentInformation;

    @Embedded
    private Money orderMinimum = new Money(Integer.MAX_VALUE);

    public Order(){ }
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
                return singletonList(new OrderCancelled());
            default:
                throw new UnsupportedStateTransitionException(state);
        }

    }
    public List<OrderDomainEvent> noteApproved() {
        switch(state){
            case APPROVAL_PENDING:
                this.state=OrderState.APPROVED;
                return singletonList(new OrderAuthorized());
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    public List<OrderDomainEvent> noteRejected() {
        switch(state){
            case APPROVAL_PENDING:
                this.state=OrderState.REJECTED;
                return singletonList(new OrderRejected());
            default:
                throw new UnsupportedStateTransitionException(state);
        }

    }
    public List<OrderDomainEvent> noteReversingAuthorization(){return null;}
    public ResultWithDomainEvents<LineItemQuantityChange,OrderDomainEvent> revise(OrderRevision orderRevision) {
        switch(state){
            case APPROVED:
                LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);
                if(change.newOrderTotal.isGreaterThanOrEqual(orderMinimum)){
                     throw new OrderMinimumNotMetException();
                }
                this.state = OrderState.REVISION_PENDING;
                return new ResultWithDomainEvents<>(change,singletonList(new OrderRevisionProposed(orderRevision,change.currentOrderTotal,change.newOrderTotal)));
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    public List<OrderDomainEvent> rejectRevision(){
        switch(state){
            case REVISION_PENDING:
                this.state = OrderState.APPROVED;
                return emptyList();
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    public List<OrderDomainEvent> confirmRevision(OrderRevision orderRevision){
        switch(state){
            case REVISION_PENDING:
                LineItemQuantityChange lineItemQuantityChange=orderLineItems.lineItemQuantityChange(orderRevision);
                orderRevision.getDeliveryInformation().ifPresent(newDi->this.deliveryInformation=newDi);
                if(orderRevision.getRevisedOrderLineItems()!=null && orderRevision.getRevisedOrderLineItems().size()>0){
                    orderLineItems.updateLineItems(orderRevision);
                }
                this.state=OrderState.APPROVED;
                return singletonList(new OrderRevised(orderRevision,lineItemQuantityChange.currentOrderTotal,lineItemQuantityChange.newOrderTotal));
            default:
                throw new UnsupportedStateTransitionException(state);
        }
    }
    /******领域业务******end*****/
}
