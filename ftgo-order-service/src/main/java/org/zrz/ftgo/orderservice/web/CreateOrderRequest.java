package org.zrz.ftgo.orderservice.web;

import org.zrz.ftgo.orderservice.domain.Address;
import org.zrz.ftgo.orderservice.domain.LineItemQuantityChange;

import java.time.LocalDateTime;
import java.util.List;

public class CreateOrderRequest {
    private long restaurantId;
    private long consumerId;
    private LocalDateTime deliveryTime;
    private List<LineItem> lineItems;
    private Address deliveryAddress;

    public CreateOrderRequest(long consumerId,
                              long restaurantId,
                              Address deliveryAddress,
                              LocalDateTime deliveryTime,
                              List<LineItem> lineItems){
        this.consumerId=consumerId;
        this.restaurantId=restaurantId;
        this.deliveryAddress=deliveryAddress;
        this.deliveryTime=deliveryTime;
        this.lineItems=lineItems;
    }

    private CreateOrderRequest(){}

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

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public static class LineItem{
        private String menuItemId;
        private int quantity;

        private LineItem(){}
        public LineItem(String menuItemId,int quantity){
            this.menuItemId=menuItemId;
            this.quantity=quantity;
        }

        public String getMenuItemId() {
            return menuItemId;
        }
        public void setMenuItemId(String menuItemId) {
            this.menuItemId = menuItemId;
        }

        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
