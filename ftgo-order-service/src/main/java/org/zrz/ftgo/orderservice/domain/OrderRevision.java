package org.zrz.ftgo.orderservice.domain;

import java.util.List;
import java.util.Optional;

public class OrderRevision {
    private Optional<DeliveryInformation> deliveryInformation=Optional.empty();
    private List<RevisedOrderLineItem> revisedOrderLineItems;

    private OrderRevision(){}
    public OrderRevision(Optional<DeliveryInformation> deliveryInformation,List<RevisedOrderLineItem> revisedOrderLineItems){
        this.deliveryInformation=deliveryInformation;
        this.revisedOrderLineItems=revisedOrderLineItems;
    }

    public Optional<DeliveryInformation> getDeliveryInformation() {
        return deliveryInformation;
    }
    public void setDeliveryInformation(Optional<DeliveryInformation> deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }

    public List<RevisedOrderLineItem> getRevisedOrderLineItems() {
        return revisedOrderLineItems;
    }
    public void setRevisedOrderLineItems(List<RevisedOrderLineItem> revisedOrderLineItems) {
        this.revisedOrderLineItems = revisedOrderLineItems;
    }
}
