package com.techstack.rabbitmq;

public class MessageConfigInfo {

    public static final String QUEUE_NAME = "Queue-1";
    public static final String MOBILE_QUEUE = "Mobile";
    public static final String TV_QUEUE = "TV";
    public static final String AC_QUEUE = "AC";

    public static final String DIRECT_EXCHANGE_NAME = "direct-exchange";
    public static final String FANOUT_EXCHANGE_NAME = "fanout-exchange";
    public static final String TOPIC_EXCHANGE_NAME = "topic-exchange";
    public static final String HEADERS_EXCHANGE_NAME = "headers-exchange";

    public static final String ROUTING_KEY_FOR_MOBILE = "mobile";
    public static final String ROUTING_KEY_FOR_TV = "tv";
    public static final String ROUTING_KEY_FOR_AC = "ac";
}
