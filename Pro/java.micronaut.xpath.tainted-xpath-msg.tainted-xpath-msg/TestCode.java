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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

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
        FileInputStream fileIS = new FileInputStream(this.getFile());
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Employees/Employee[@emplid='" + foobar.name + "']";
        // ruleid: tainted-xpath-msg
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        doSmth(nodeList);
        clientUpdatesService.sendMotionUpdatesToSubscribedClients(foobar);
    }
}

@Slf4j
@KafkaClient()
public class OkListener {

    @NotATopic("my-topic")
    void myTopic(SomeData foobar) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Employees/Employee[@emplid='123']";
        // ok: tainted-xpath-msg
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getFileFrom(name), XPathConstants.NODESET);
        doSmth(nodeList);
    }
}


@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
        String name = new String(data, StandardCharsets.UTF_8);
        FileInputStream fileIS = new FileInputStream(this.getFile());
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Employees/Employee[@emplid='" + name + "']";
        // ruleid: tainted-xpath-msg
        String result = xPath.evaluate(expression, xmlDocument);
        doSmth(result);
    }
}

@RabbitListener
public class ProductListener {

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());

    @Queue("product")
    public void receive(byte[] data) {
        FileInputStream fileIS = new FileInputStream(this.getFile(cmd.filePath));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Employees/Employee[@emplid='hardcode']";
        // ok: tainted-xpath-msg
        String result = xPath.evaluate(expression, xmlDocument);
        doSmth(data);
    }
}
