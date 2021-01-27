package org.zrz.ftgo.orderservice.domain;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zrz.ftgo.orderservice.exceptions.UnsupportedStateTransitionException;

import javax.transaction.Transactional;
import java.util.List;

public class OrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SagaInstanceFactory sagaInstanceFactory;
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;
    private CreateOrderSaga createOrderSaga;
    private CancelOrderSaga cancelOrderSaga;
    private ReviseOrderSaga reviseOrderSaga;
    private OrderDomainEventPublisher orderAggregateEventPublisher;
    private Optional<MeterRegistry> meterRegistry;

    public OrderService(SagaInstanceFactory sagaInstanceFactory,
                        OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository,
                        CreateOrderSaga createOrderSaga,
                        CancelOrderSaga cancelOrderSaga,
                        ReviseOrderSaga reviseOrderSaga,
                        OrderDomainEventPublisher orderAggregateEventPublisher,
                        Optional<MeterRegistry> meterRegistry){
         this.sagaInstanceFactory=sagaInstanceFactory;
         this.orderRepository=orderRepository;
         this.restaurantRepository=restaurantRepository;
         this.createOrderSaga=createOrderSaga;
         this.cancelOrderSaga=cancelOrderSaga;
         this.reviseOrderSaga=reviseOrderSaga;
         this.orderAggregateEventPublisher=orderAggregateEventPublisher;
         this.meterRegistry=meterRegistry;
    }

    @Transactional
    public Order createOrder(long consumerId,long restaurantId,DeliveryInformation deliveryInformation,List<MenuItemIdAndQuantity> lineItems){
        Restaurant restaurant = resaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException(restaurantId));
        List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems,restaurant);
    }




    public void cancelOrder(Order order){
        try{
              order.cancel();
        }catch(UnsupportedStateTransitionException exception){

        }
    }
}
