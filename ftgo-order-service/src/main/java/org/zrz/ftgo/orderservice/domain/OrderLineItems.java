package org.zrz.ftgo.orderservice.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
public class OrderLineItems {
    @ElementCollection
    @CollectionTable(name="order_line_items")
    private List<OrderLineItem> lineItems;

    private OrderLineItems(){ }
    public OrderLineItems(List<OrderLineItem> lineItems){
        this.lineItems = lineItems;
    }

    public List<OrderLineItem> getLineItems(){
        return this.lineItems;
    }

    public void setLineItems(List<OrderLineItem> lineItems){
        this.lineItems = lineItems;
    }
}
