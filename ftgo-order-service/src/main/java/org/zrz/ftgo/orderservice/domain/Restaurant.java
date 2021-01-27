package org.zrz.ftgo.orderservice.domain;

import org.zrz.ftgo.orderservice.events.OrderDomainEvent;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="order_service_restaurants")
@Access(AccessType.FIELD)
public class Restaurant  {
    @Id
    private Long id;

//    @Embedded
//    @ElementCollection
//    @CollectionTable(name="order_service_restaurant_menu_items")
    //private List<MenuItem> menuItems;
    private String name;

    public Restaurant(){}
    public Restaurant(long id,String name,List<MenuItem> menuItems){
        this.id=id;
        this.name=name;
        //this.menuItems=menuItems;
    }

    public Long getId() {
        return id;
    }
    public List<MenuItem> getMenuItems() {
        //return menuItems;
        return null;
    }
    public String getName() {
        return name;
    }

    /*****************领域逻辑*******************************************/
    public Optional<MenuItem> findMenuItem(String menuItemId){
        //return menuItems.stream().filter(mi->mi.getId().equals(menuItemId)).findFirst();
        return null;
    }
    public List<OrderDomainEvent> reviseMenu(List<MenuItem> revisedMenu){
        throw new UnsupportedOperationException();
    }

//    public void verifyRestaurantDetails(TicketDetails ticketDetails){
//        //TODO-implment me
//    }

    /*****************领域逻辑*******************************************/
}
