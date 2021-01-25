package org.zrz.ftgo.orderservice.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class RevisedOrderLineItem {
    private int quantity;
    private String menuItemId;

    public RevisedOrderLineItem(){}
    public RevisedOrderLineItem(int quantity,String menuItemId){
        this.quantity=quantity;
        this.menuItemId=menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMenuItemId() {
        return menuItemId;
    }
    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    @Override
    public int hashCode(){
        return Objects.hash(quantity,menuItemId);
    }

    @Override
    public boolean equals(Object o){
        return EqualsBuilder.reflectionEquals(this,o);
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

}
