package org.zrz.ftgo.orderservice.sagas.createorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zrz.ftgo.orderservice.domain.OrderDetails;


public class CreateOrderSagaState {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private Long orderId;
    private long ticketId;
    private OrderDetails orderDetails;

    private CreateOrderSagaState (){}
    public CreateOrderSagaState(Long orderId,OrderDetails orderDetails){
        this.orderId=orderId;
        this.orderDetails=orderDetails;
    }





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
}
