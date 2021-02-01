package org.zrz.ftgo.orderservice.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zrz.ftgo.orderservice.domain.DeliveryInformation;
import org.zrz.ftgo.orderservice.domain.Order;
import org.zrz.ftgo.orderservice.domain.OrderRepository;
import org.zrz.ftgo.orderservice.domain.OrderService;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path="/orders")
public class OrderController {
//    private OrderService  orderService;
//    private OrderRepository orderRepository;
//
//    public OrderController(OrderService orderService,OrderRepository orderRepository){
//           this.orderService = orderService;
//           this.orderRepository = orderRepository;
//    }

    @RequestMapping(path="/test",method = RequestMethod.GET)
    public boolean test(){
        return true;
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public CreateOrderResponse create(@RequestBody CreateOrderRequest request){
//         Order order = orderService.createOrder(request.getConsumerId(),
//                 request.getRestaurantId(),
//                 new DeliveryInformation(request.getDeliveryTime(),request.getDeliveryAddress()),
//                 request.getLineItems().stream().map(x->new MenuItemIdAndQuantity(x.getMenuItemId(),x.getQuantity())).collect(toList()));
//         return new CreateOrderResponse(order.getId());
//    }
}
