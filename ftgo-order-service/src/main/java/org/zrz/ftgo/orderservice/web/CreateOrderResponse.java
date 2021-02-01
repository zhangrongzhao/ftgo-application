package org.zrz.ftgo.orderservice.web;

public class CreateOrderResponse {
    private long orderId;

    private CreateOrderResponse(){}
    public CreateOrderResponse(long orderId){
        this.orderId=orderId;
    }

    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
