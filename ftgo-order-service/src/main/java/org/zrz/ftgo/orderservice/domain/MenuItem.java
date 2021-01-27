package org.zrz.ftgo.orderservice.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class MenuItem {
    private String id;
    private String name;
    private Money price;

    public MenuItem(){}
    public MenuItem(String id,String name,Money price){
        this.id=id;
        this.name=name;
        this.price=price;
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

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
