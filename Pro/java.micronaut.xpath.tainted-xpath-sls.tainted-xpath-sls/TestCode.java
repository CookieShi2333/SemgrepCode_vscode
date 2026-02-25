package my.lambda.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.cloud.functions.*;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import io.micronaut.azure.function.AzureFunction;
import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.json.JsonMapper;
import jakarta.inject.*;
import jakarta.inject.Inject;


public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    JsonMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            FileInputStream fileIS = new FileInputStream(this.getFile());
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/Employees/Employee[@emplid='" + input.getBody() + "']";
            // ruleid: tainted-xpath-sls
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            String result = doSmth(nodeList);

            response.setStatusCode(200);
            response.setBody(result);
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}

public class OkFunctionRequestHandler extends NOTaMicronautRequest {

    public APIGatewayProxyResponseEvent execute(Event input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/Employees/Employee[@emplid='123']";
            // ok: tainted-xpath-sls
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getFileFrom(name), XPathConstants.NODESET);
            String result = doSmth(nodeList);
            response.setStatusCode(200);
            response.setBody(result);
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}

public class Function2 extends AzureFunction {
    @FunctionName("HttpTrigger")
    public HttpResponseMessage hello(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    route = "{*route}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
        FileInputStream fileIS = new FileInputStream(this.getFile());
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Employees/Employee[@emplid='" + request.getBody() + "']";
        // ruleid: tainted-xpath-sls
        String result = xPath.evaluate(expression, xmlDocument);
        if (context != null) {
            context.getLogger().info("Executing Function: " + getClass().getName());
        }
        return request.createResponseBuilder(HttpStatus.OK).body(result).build();
    }
}

public class OkFunction2 extends AzureFunction {
    @FunctionName("HttpTrigger")
    public HttpResponseMessage hello(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    route = "{*route}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Employees/Employee[@emplid='123']";
        // ok: tainted-xpath-sls
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getFileFrom(name), XPathConstants.NODESET);
        doSmth(nodeList);
        if (context != null) {
            context.getLogger().info("Executing Function: " + getClass().getName());
        }
        return request.createResponseBuilder(HttpStatus.OK).body("Hello World").build();
    }
}
