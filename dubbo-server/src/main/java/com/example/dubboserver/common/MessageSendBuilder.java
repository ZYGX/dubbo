package com.example.dubboserver.common;

import com.example.dubboserver.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MessageSendBuilder {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitAdmin rabbitAdmin;

    //1 构造template, exchange, routingkey等
    //2 设置message序列化方法
    //3 设置发送确认
    //4 构造sender方法
    public MessageSender buildMessageSender(String exchange, String routingKey,String queue) {

        //1
//        rabbitAdmin.declareQueue(new Queue(queue));
//        rabbitAdmin.declareExchange(new TopicExchange(exchange));
//        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue(queue)).to(new TopicExchange(exchange)).with(routingKey));

        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setQueue(queue);

        //3
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("comfirm...");
            if(ack){
                log.info("send message success" );
            }
            if (!ack) {
                log.info("send message failed: " + cause); //+ correlationData.toString());
                throw new RuntimeException("send error " + cause);
            }
        });

        //4
        return message -> {
            try {
                rabbitTemplate.convertAndSend(message);
            } catch (RuntimeException e) {
                System.out.println("retry...");
                e.printStackTrace();
                log.info("send failed " + e);
                try {
                    //retry
                    rabbitTemplate.convertAndSend(message);
                } catch (RuntimeException error) {
                    error.printStackTrace();
                    log.info("send failed again " + error);
                    return new BaseResponse(BaseResponse.BaseResponseCode.SYS_ERROR);
                }
            }
            return new BaseResponse(BaseResponse.BaseResponseCode.SUCCESS);
        };
    }
}
