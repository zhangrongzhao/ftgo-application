package org.zrz.ftgo.orderservice.service;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.zrz.ftgo.orderservice.domain.OrderService;
import org.zrz.ftgo.orderservice.sagaparticipants.ApproveOrderCommand;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class OrderCommandHandlers {

    private OrderService orderService;

    public CommandHandlers commandHandlers(){
        return SagaCommandHandlersBuilder
                .fromChannel("orderService")
                .onMessage(ApproveOrderCommand.class,this::approveOrder)
                //.onMessage(RejectedOrderCommand.class,this::rejectOrder)
                .build();
    }

    public Message approveOrder(CommandMessage<ApproveOrderCommand> cm){
        long orderId = cm.getCommand().getOrderId();
        orderService.approveOrder(orderId);
        return withSuccess();
    }

}
