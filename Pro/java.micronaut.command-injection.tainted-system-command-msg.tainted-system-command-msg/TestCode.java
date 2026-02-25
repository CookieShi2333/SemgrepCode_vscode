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
        String comd = "ls -lah " + foobar.data;
        // ruleid: tainted-system-command-msg
        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        doSmth(stdInput);
        clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);
    }
}

@Slf4j
@KafkaClient()
public class OkListener {

    @NotATopic("my-topic")
    void myTopic(SomeData foobar) {
        String comd = "ls -lah " + foobar.data;
        // ok: tainted-system-command-msg
        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        doSmth(stdInput);
    }
}


@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
        String cmd = new String(data, StandardCharsets.UTF_8);
        String comd = "ls -lah " + cmd;
        // ruleid: tainted-system-command-msg
        Process p =  new ProcessBuilder(new String[]{"bash", "-c", comd}).start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        doSmth(stdInput);
    }
}

@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
        // ok: tainted-system-command-msg
        Process p =  new ProcessBuilder(new String[]{"bash", "-c", foobar()}).start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        doSmth(data);
    }
}
