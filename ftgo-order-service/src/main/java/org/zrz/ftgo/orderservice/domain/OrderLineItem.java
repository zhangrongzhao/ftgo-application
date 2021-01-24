package org.zrz.ftgo.orderservice.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Embeddable
public class OrderLineItem {
    private int quantity;
    private String menuItemId;
    private String name;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name="amount",column=@Column(name="price")))
    private Money price;

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

    public OrderLineItem(){}
    public OrderLineItem(String menuItemId,String name,Money price,int quantity){
        this.menuItemId=menuItemId;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }

    public Money deltaForChangedQuantity(int newQuantity){
        return price.multiply(newQuantity-quantity);
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }
    public void setPrice(Money price) {
        this.price = price;
    }
}
