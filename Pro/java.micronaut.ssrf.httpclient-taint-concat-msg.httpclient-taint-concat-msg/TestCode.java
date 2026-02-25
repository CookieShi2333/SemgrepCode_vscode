package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.client.HttpClient;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@KafkaListener(
        groupId = "mmo-server",
        offsetReset = OffsetReset.EARLIEST,
        offsetStrategy = OffsetStrategy.SYNC,
        clientId = "socket_listener")
public class SocketUpdateListener {

    @Inject ClientUpdatesService clientUpdatesService;

    @Inject
    @Client("/")
    private HttpClient client;

    @Topic("my-topic")
    void myTopic(SomeData foobar) {
      String uri = "https://" + foobar.data;
      // ruleid: httpclient-taint-concat-msg
      String res = client.toBlocking().retrieve(uri);
      doSmth(stdInput);
      clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);
    }
}

@Slf4j
@KafkaClient()
public class OkListener {

    @Inject
    @Client("/")
    private HttpClient client;

    @NotATopic("my-topic")
    void myTopic(SomeData foobar) {
      // currently we do not support this type of SSRF injection
      // need more research on how many FPs we introduce
      String uri = "https://www.example.com" + foobar.data;
      // ok: httpclient-taint-concat-msg
      String res = client.toBlocking().retrieve(uri);
      doSmth(stdInput);
      clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);
    }
}


@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Inject
    @Client("/")
    private HttpClient client;

    @Queue("product")
    public void receive(byte[] data) {
      String uri = new String(data, StandardCharsets.UTF_8);
      // ruleid: httpclient-taint-concat-msg
      String res = client.toBlocking().retrieve("https://" + uri);
      doSmth(res);
    }
}

@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Inject
    @Client("/")
    private HttpClient client;

    @Queue("product")
    public void receive(byte[] data) {
      // ok: httpclient-taint-concat-msg
      String res = client.toBlocking().retrieve("https://www.example.com");
      doSmth(res);
    }
}
