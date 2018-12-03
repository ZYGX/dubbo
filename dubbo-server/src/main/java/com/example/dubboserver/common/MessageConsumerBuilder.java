package com.example.dubboserver.common;

import com.alibaba.fastjson.JSON;
import com.example.dubboserver.response.BaseResponse;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class MessageConsumerBuilder {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ConnectionFactory connectionFactory;

    //1 创建连接和channel
    //2 设置message序列化方法
    //3 构造consumer
    public MessageConsumer buildMessageConsumer(String exchange, String routingKey,
                                                    final String queue, final MessageProcess messageProcess) throws IOException {

        //1 通过delivery获取原始数据
        //2 将原始数据转换为特定类型的包
        //3 处理数据
        //4 手动发送ack确认
        return () -> {
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);
            //1
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        System.out.println(new String(body, "UTF-8"));
                        Object messageBean = JSON.parse(new String(body, "UTF-8"));
                        BaseResponse detailRes = messageProcess.process(messageBean);
                        if (detailRes.isSuccess()) {
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        } else {
                            log.info("send message failed: " + detailRes.getErrMsg());
                            channel.basicNack(envelope.getDeliveryTag(), false, true);
                        }
                        //模拟异常
                        int i = 1 / 0;
                        //手动ack
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    } catch (ShutdownSignalException e) {
                        e.printStackTrace();
                        try {
                            channel.close();
                        } catch (IOException io) {
                            io.printStackTrace();
                        } catch (TimeoutException timeout) {
                            timeout.printStackTrace();
                        }
                        //断开重连
                           buildMessageConsumer(exchange, routingKey,
                                   queue, messageProcess);
                    } catch (Exception e) {
                        //重新放入队列
                        channel.basicNack(envelope.getDeliveryTag(), false, true);
                        //抛弃此条消息
                        //channel.basicNack(envelope.getDeliveryTag(), false, false);
                        e.printStackTrace();
                    }

                }
            });
            return  new BaseResponse(BaseResponse.BaseResponseCode.SUCCESS);
        };
    }

}
