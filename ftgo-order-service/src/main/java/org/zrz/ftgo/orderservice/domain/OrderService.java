package org.zrz.ftgo.orderservice.domain;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zrz.ftgo.orderservice.events.OrderDomainEvent;
import org.zrz.ftgo.orderservice.sagas.createorder.CreateOrderSaga;
import org.zrz.ftgo.orderservice.sagas.createorder.CreateOrderSagaState;
import org.zrz.ftgo.orderservice.web.MenuItemIdAndQuantity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class OrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SagaInstanceFactory sagaInstanceFactory;
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;
    private CreateOrderSaga createOrderSaga;
    //private CancelOrderSaga cancelOrderSaga;
    //private ReviseOrderSaga reviseOrderSaga;
    private OrderDomainEventPublisher orderAggregateEventPublisher;
    private Optional<MeterRegistry> meterRegistry;

    public OrderService(SagaInstanceFactory sagaInstanceFactory,
                        OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository,
                        CreateOrderSaga createOrderSaga,
                        //CancelOrderSaga cancelOrderSaga,
                        //ReviseOrderSaga reviseOrderSaga,
                        OrderDomainEventPublisher orderAggregateEventPublisher//,
                        //Optional<MeterRegistry> meterRegistry
    ){
         this.sagaInstanceFactory = sagaInstanceFactory;
         this.orderRepository = orderRepository;
         //this.restaurantRepository=restaurantRepository;
         this.createOrderSaga = createOrderSaga;
         //this.cancelOrderSaga=cancelOrderSaga;
         //this.reviseOrderSaga=reviseOrderSaga;
         this.orderAggregateEventPublisher = orderAggregateEventPublisher;
         this.meterRegistry = meterRegistry;
    }

    @Transactional
    public Order createOrder(long consumerId,long restaurantId,DeliveryInformation deliveryInformation,List<MenuItemIdAndQuantity> lineItems){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException(restaurantId));
        List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems,restaurant);
        ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = Order.createOrder(consumerId,restaurant,deliveryInformation,orderLineItems);
        Order order = orderAndEvents.result;
        orderRepository.save(order);
        orderAggregateEventPublisher.publish(order,orderAndEvents.events);
        OrderDetails orderDetails = new OrderDetails(consumerId,restaurantId,orderLineItems,order.getOrderTotal());
        CreateOrderSagaState data=new CreateOrderSagaState(order.getId(),orderDetails);
        sagaInstanceFactory.create(createOrderSaga,data);
        meterRegistry.ifPresent(mr->mr.counter("placed_orders").increment());
        return order;
    }

    private List<OrderLineItem> makeOrderLineItems(List<MenuItemIdAndQuantity> lineItems, Restaurant restaurant){
        return lineItems.stream().map(li->{
            MenuItem om = restaurant.findMenuItem(li.getMenuItemId()).orElseThrow(()-> new InvalidMenuItemIdException(li.getMenuItemId()));
            return new OrderLineItem(li.getMenuItemId(),om.getName(),om.getPrice(),li.getQuantity());
        }).collect(toList());
    }

//    public Optional<Order> confirmChangeLineItemQuantity(Long orderId, OrderRevision orderRevision) {
//        return orderRepository.findById(orderId).map(order->{
//            List<OrderDomainEvent> events=order.confirmRevision(orderRevision);
//            orderAggregateEventPublisher.publish(order,events);
//            return order;
//        });
//    }
//
//    public void noteReversingAuthorization(Long orderId){
//        throw new UnsupportedOperationException();
//    }

//    @Transactional
//    public Order cancel(Long orderId){
//        Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException (orderId));
//        CancelOrderSagaData sagaData = new CancelOrderSagaData(order.getConsumerId(),orderId,order.getOrderTotal());
//        sagaInstanceFactory.create(cancelOrderSaga,sagaData);
//        return order;
//    }

//    private Order updateOrder(long orderId, Function<Order,List<OrderDomainEvent>> updater){
//        return orderRepository.findById(orderId).map(order->{
//            orderAggregateEventPublisher.publish(order,updater.apply(order));
//            return order;
//        }).orElseThrow(()->new OrderNotFoundException(orderId));
//    }
//
//    public void rejectOrder(long orderId){
//        updateOrder(orderId,Order::noteRejected);
//        meterRegistry.ifPresent(mr->mr.counter("rejected_orders").increment());
//    }
//
//    public void beginCancel(long orderId){
//        updateOrder(orderId,Order::cancel);
//    }
//
//    public void undoCancel(long orderId){
//        updateOrder(orderId,Order::undoPendingCancel);
//    }
//
//    public void confirmCancelled(long orderId){
//        updateOrder(orderId,Order::noteCancelled);
//    }

//    @Transactional
//    public Order reviseOrder(long orderId,OrderRevision orderRevision){
//        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException(orderId));
//        RevisedOrderSagaData sagaData=new RevisedOrderSagaData(order.getConsumerId(),orderId,null,orderRevision);
//        sagaInstanceFactory.create(reviseOrderSaga,sagaData);
//        return order;
//    }
//
//    public Optional<RevisedOrder> beginReviseOrder(long orderId,OrderRevision revision){
//        return OrderRepository.findById(orderId).map(order->{
//            ResultWithDomainEvents<LineItemQuantityChange,OrderDomainEvent> result=order.revise(revision);
//            orderAggregateEventPublisher.publish(order,result.events);
//            return new RevisedOrder(order,result.events);
//        });
//    }
//
//    public void undoPendingRevision(long orderId){
//        updateOrder(orderId,Order::rejectRevision);
//    }
//
//    public void confirmRevision(long orderId,OrderRevision revision){
//        updateOrder(orderId,order->order.confirmRevision(revision));
//    }
//
//    public void createMenu(long id,String name,List<MenuItem> menuItems){
//        Restaurant restaurant=new Restaurant(id,name,menuItems);
//        restaurantRepository.save(restaurant);
//    }
//
//    public void revisedMenu(long id,List<MenuItem> menuItems){
//        restaurantRepository.findById(id).map(restaurant->{
//            List<OrderDomainEvent> events=restaurant.reviseMenu(menuItems);
//            return restaurant;
//        }).orElseThrow(RuntimeException::new);
//    }

}
