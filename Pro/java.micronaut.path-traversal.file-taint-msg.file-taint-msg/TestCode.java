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

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
      String myPath = Paths.get(foobar.data).normalize().toString();

      // ruleid: file-taint-msg
      File fff = new File(BASE_PATH, myPath);
      String content = new String(Files.readAllBytes(fff.toPath()));
      doSmth(content);
      clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);

    }
}

@Slf4j
@KafkaClient()
public class OkListener {

    @NotATopic("my-topic")
    void myTopic(SomeData foobar) {
      String myPath = Paths.get(BASE_PATH, foobar.data).normalize().toString();

      if (!myPath.startsWith(BASE_PATH + File.separator)) {
        throw new Exception("Path traversal!");
      }

      // ok: file-taint-msg
      File fff = new File(myPath);
      String content = new String(Files.readAllBytes(fff.toPath()));
      doSmth(stdInput);
    }
}


@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
      String pth = new String(data, StandardCharsets.UTF_8);
      String myPath = Paths.get(BASE_PATH, pth).normalize().toString();

      String data = "This is a line of text inside the file.";
      // ruleid: file-taint-msg
      FileOutputStream output = new FileOutputStream(myPath);
      byte[] result = data.getBytes();

      output.write(result);

      output.close();
      doSmth(result);
    }
}

@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
      String myPath = Paths.get(BASE_PATH, "hardcoded/path").normalize().toString();

      String data = "This is a line of text inside the file.";
      // ok: file-taint-msg
      FileOutputStream output = new FileOutputStream(myPath);
      byte[] result = data.getBytes();

      output.write(result);

      output.close();
      doSmth(result);
    }
}
