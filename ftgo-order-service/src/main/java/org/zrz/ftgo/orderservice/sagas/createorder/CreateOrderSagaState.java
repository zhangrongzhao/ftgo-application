package org.zrz.ftgo.orderservice.sagas.createorder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zrz.ftgo.orderservice.domain.OrderDetails;
import org.zrz.ftgo.orderservice.domain.OrderLineItem;
import org.zrz.ftgo.orderservice.sagaparticipants.ApproveOrderCommand;
import org.zrz.ftgo.orderservice.sagaparticipants.RejectOrderCommand;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class CreateOrderSagaState {
    /************************field******************************************/
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Long orderId;
    private long ticketId;
    private OrderDetails orderDetails;
    /************************constructor************************************/
    private CreateOrderSagaState (){}
    public CreateOrderSagaState(Long orderId,OrderDetails orderDetails){
        this.orderId=orderId;
        this.orderDetails=orderDetails;
    }
    /************************equals,hashcode,toString***********************/
    @Override
    public  boolean equals(Object o){return EqualsBuilder.reflectionEquals(this,o);}

    @Override
    public int hashCode(){return HashCodeBuilder.reflectionHashCode(this);}
    /************************getter,setter*********************************/
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public long getTicketId() {
        return ticketId;
    }
    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
    /**********************************************************************/
//    CreateTicket makeCreateTicketCommand(){
//        return new CreateTicket(getOrderDetails().getConsumerId(),getOrderId(),makeTicketDetails(getOrderDetails()));
//    }
//
//    private TicketDetails makeTicketDetails(OrderDetails orderDetails){
//        return new TicketDetails(makeTicketLineItems(orderDetails.getLineItems()));
//    }
//
//    private List<TicketLineItem> makeTicketLineItems(List<OrderLineItem> lineItems){
//        return lineItems.stream().map(this::makeTicketLineItem).collect(toList());
//    }
//
//    private TicketLineItem makeTicketItem(OrderLineItem orderLineItem){
//        return new TicketLineItem(orderLineItem.getMenuItemId(),orderLineItem.getName(),orderLineItem.getQuantity());
//    }
//
//    void handleCreateTicketReply(CreateTicketReply reply){
//        logger.debug("getTicketId{}",reply.getTicketId());
//        setTicketId(reply.getTicketId());
//    }
//
//    CancelCreateTicket makeCancelCreateTicketCommand(){
//        return new CancelCreateTicket(getOrderId());
//    }

    RejectOrderCommand makeRejectOrderCommand(){
        return new RejectOrderCommand(getOrderId());
    }

//    ValidateOrderByConsumer makeValidateOrderByConsumerCommand(){
//        ValidateOrderByConsumer x = new ValidateOrderByConsumer();
//        x.setConsumerId(getOrderDetails().getConsumerId());
//        x.setOrderId(getOrderId());
//        x.setOrderTotal(getOrderDetails().getOrderTotal().asString());
//        return x;
//    }
//
//    AuthorizeCommand makeAuthorizeCommand(){
//        return new AuthorizeCommand().withConsumerId(getOrderDetails().getConsumerId()).withOrderId(getOrderId()).withOrderTotal(getOrderDetails().getOrderTotal().asString());
//    }

    ApproveOrderCommand makeApproveOrderCommand(){
        return new ApproveOrderCommand(getOrderId());
    }

//    ConfirmCreateTicket makeConfirmCreateTicketCommand(){
//        return new ConfirmCreateTicket(getTicketId());
//    }
}
