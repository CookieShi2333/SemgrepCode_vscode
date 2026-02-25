package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
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

    @Topic("my-topic")
    void myTopic(SomeData foobar) {
      HttpRequest req = HttpRequest.newBuilder().uri(new URI("https://" + foobar.data)).GET().build();
      HttpClient cl = HttpClient.newBuilder().build();
      // ruleid: java-http-concat-taint-msg
      HttpResponse<String> res = cl.send(req, HttpResponse.BodyHandlers.ofString());
      doSmth(res);
      clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);
    }
}

@Slf4j
@KafkaClient()
public class OkListener {

    @NotATopic("my-topic")
    void myTopic(SomeData foobar) {
      HttpRequest req = HttpRequest.newBuilder()
        .uri(new URI("https://www.example.com"))
        .headers("Foobar", "https://" + foobar.data)
        .GET()
        .build();
      // ok: java-http-concat-taint-msg
      HttpResponse<String> res = HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
      doSmth(res);
      clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);
    }
}


@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
      String param = new String(data, StandardCharsets.UTF_8);
      String url = String.format("https://%s", param);

      // ruleid: java-http-concat-taint-msg
      new URL(url).openConnection();
      // ruleid: java-http-concat-taint-msg
      new URL(url).openStream();

      URL url = new URL(url);
      // ruleid: java-http-concat-taint-msg
      URLConnection connection = url.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String body = reader.lines().collect(Collectors.joining());
      doSmth(body);
    }
}

@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
      // ok: java-http-concat-taint-msg
      URL url = new URL("https://www.example.com/");
      URLConnection connection = url.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String body = reader.lines().collect(Collectors.joining());
      doSmth(body);
    }
}
