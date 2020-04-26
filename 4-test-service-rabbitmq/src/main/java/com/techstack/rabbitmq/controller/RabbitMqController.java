package com.techstack.rabbitmq.controller;

import com.techstack.rabbitmq.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import static com.techstack.rabbitmq.MessageConfigInfo.DIRECT_EXCHANGE_NAME;
import static com.techstack.rabbitmq.MessageConfigInfo.FANOUT_EXCHANGE_NAME;
import static com.techstack.rabbitmq.MessageConfigInfo.HEADERS_EXCHANGE_NAME;
import static com.techstack.rabbitmq.MessageConfigInfo.MOBILE_QUEUE;
import static com.techstack.rabbitmq.MessageConfigInfo.ROUTING_KEY_FOR_MOBILE;
import static com.techstack.rabbitmq.MessageConfigInfo.TOPIC_EXCHANGE_NAME;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RabbitMqController {

    // Option1: use
    private final RabbitTemplate messageTemplate;

    // Option2: use
    //private final AmqpTemplate messageTemplate;

    @GetMapping("/test/{name}")
    public String  processName(@PathVariable String name) {

        /**
         * Inorder to send a complex type (object) as message payload, you have to
         * implement Serializable interface
         */
        Person person = new Person(1L, name);

        /**
         * Here convertAndSend method uses {@Code SimpleMessageConverter} only supports
         * the following TYPES
         * <li>String</li>
         * <li>ByteArray</li>
         * <li>Serializable Objects</li>
         */

        /**
         * By using below method, we can directly send a message to Queue
         */
        messageTemplate.convertAndSend(MOBILE_QUEUE, person);

        /**
         * By using below method, we can send a message to "Direct Exchange" using "Routing Key"
         */
        messageTemplate.convertAndSend(DIRECT_EXCHANGE_NAME, ROUTING_KEY_FOR_MOBILE, person);

        /**
         * By using below method, we can send a message to "Fanout Exchange"
         */
        messageTemplate.convertAndSend(FANOUT_EXCHANGE_NAME, "", person);

        /**
         * By using below method, we can send a message to "Topic Exchange" using "Routing Key"
         */
        messageTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, "tv.mobile.ac", person);

        return "success";
    }

    @GetMapping("/test/headers-exchange/{name}")
    public String testHeadersExchange(@PathVariable String name) throws IOException {

        Person person = new Person(1L, name);

        /**
         * Java way of converting Object to Bytes
         */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(baos);
        objectOutput.writeObject(person);
        objectOutput.flush();
        objectOutput.close();

        byte[] byteMessage = baos.toByteArray();
        baos.close();

        /**
         * Prepare a message
         */
        Message message = MessageBuilder
                            .withBody(byteMessage)
                            .setHeader("item1", "mobile")
                            .setHeader("item2", "television")
                            .build();

        messageTemplate.send(HEADERS_EXCHANGE_NAME, "", message);

        return "success";
    }
}
