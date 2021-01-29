package org.zrz.ftgo.orderservice.web;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MenuItemIdAndQuantity {
    private String menuItemId;
    private int quantity;

    public MenuItemIdAndQuantity(String menuItemId,int quantity){
       this.menuItemId = menuItemId;
       this.quantity = quantity;
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

    /*****************************************************************************/
    @Override
    public boolean equals(Object o){
        return EqualsBuilder.reflectionEquals(this,o);
    }

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
