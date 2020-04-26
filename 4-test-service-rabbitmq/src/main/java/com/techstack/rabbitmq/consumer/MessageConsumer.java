package com.techstack.rabbitmq.consumer;

import com.techstack.rabbitmq.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.techstack.rabbitmq.MessageConfigInfo.DIRECT_EXCHANGE_NAME;
import static com.techstack.rabbitmq.MessageConfigInfo.MOBILE_QUEUE;
import static com.techstack.rabbitmq.MessageConfigInfo.ROUTING_KEY_FOR_MOBILE;
import static com.techstack.rabbitmq.MessageConfigInfo.TV_QUEUE;

@Slf4j
@Service
public class MessageConsumer {

    /**
     * If you are sure about the Message Type (Data), use the same
     * like "Person". Else, it would throw an Exception
     *
     * @param person
     */
    @RabbitListener(queues = MOBILE_QUEUE)
    public void getMessageFromMobileQueue(Person person) {

        log.info("Message Received from Mobile Queue: {}", person);

    }

    @RabbitListener(queues = TV_QUEUE)
    public void getMessageFromTvQueue(Person person) {

        log.info("Message Received : {}", person);

    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(DIRECT_EXCHANGE_NAME),
            key = ROUTING_KEY_FOR_MOBILE,
            value = @Queue(MOBILE_QUEUE)))
    public void getMessageFromTvQueue1(Person person) {

        log.info("Message Received from DirectExchange with MobileQueue: {}", person);

    }
}
