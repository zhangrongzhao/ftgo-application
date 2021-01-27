package org.zrz.ftgo.orderservice.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class OrderDetails {
    private List<OrderLineItem> lineItems;
    private Money orderTotal;
    private long restaurantId;
    private long consumerId;

    private OrderDetails(){}
    public OrderDetails(long consumerId,long restaurantId,List<OrderLineItem> lineItems,Money orderTotal){
        this.consumerId=consumerId;
        this.restaurantId=restaurantId;
        this.lineItems=lineItems;
        this.orderTotal=orderTotal;
    }

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }
    @Override
    public boolean equals(Object o){
        return EqualsBuilder.reflectionEquals(this,o);
    }
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

    public List<OrderLineItem> getLineItems() {
        return lineItems;
    }
    public void setLineItems(List<OrderLineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(Money orderTotal) {
        this.orderTotal = orderTotal;
    }

    public long getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getConsumerId() {
        return consumerId;
    }
    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }
}
