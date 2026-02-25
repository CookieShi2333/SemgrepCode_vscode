package server.socket.listener;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.OffsetStrategy;
import io.micronaut.configuration.kafka.annotation.Topic;
import lombok.extern.slf4j.Slf4j;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

import jakarta.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import org.apache.commons.io.input.ClassLoaderObjectInputStream;

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
      FileInputStream fis = new FileInputStream(foobar.data);
      // ruleid: objectinputstream-deserialization-msg
      ObjectInputStream ois = new ObjectInputStream(fis);

      int i = ois.readInt();
      String today = (String) ois.readObject();
      Date date = (Date) ois.readObject();

      ois.close();
      String msg = messageHandler.createMessage(date, stdInput);
    }
}

@Slf4j
@KafkaClient()
public class OkListener {

    @NotATopic("my-topic")
    void myTopic(SomeData foobar) {
      FileInputStream fis = new FileInputStream("t.tmp");
      // ok: objectinputstream-deserialization-msg
      ObjectInputStream ois = new ObjectInputStream(fis);

      int i = ois.readInt();
      String today = (String) ois.readObject();
      Date date = (Date) ois.readObject();

      ois.close();
    }
}
