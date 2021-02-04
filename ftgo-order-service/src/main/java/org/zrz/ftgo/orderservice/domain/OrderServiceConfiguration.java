package org.zrz.ftgo.orderservice.domain;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.zrz.ftgo.orderservice.sagaparticipants.OrderServiceProxy;
import org.zrz.ftgo.orderservice.sagas.createorder.CreateOrderSaga;

import java.util.Optional;

@Configuration
@Import({TramEventsPublisherConfiguration.class, SagaOrchestratorConfiguration.class,CommonConfiguration.class})
public class OrderServiceConfiguration {

    @Bean
    public OrderService orderService(SagaInstanceFactory sagaInstanceFactory,
                                     RestaurantRepository restaurantRepository,
                                     OrderRepository orderRepository,
                                     DomainEventPublisher eventPublisher,
                                     CreateOrderSaga createOrderSaga,
                                     OrderDomainEventPublisher orderAggregateEventPublisher,
                                     Optional<MeterRegistry> meterRegistry){
        return new OrderService(sagaInstanceFactory,orderRepository,restaurantRepository,createOrderSaga,orderAggregateEventPublisher,meterRegistry);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderServiceProxy orderService){
        return new CreateOrderSaga(orderService);
    }

    @Bean
    public OrderServiceProxy orderServiceProxy(){
        return  new OrderServiceProxy();
    }

    @Bean
    public OrderDomainEventPublisher orderAggregateEventPublisher(DomainEventPublisher eventPublisher){
        return new OrderDomainEventPublisher(eventPublisher);
    }

}
