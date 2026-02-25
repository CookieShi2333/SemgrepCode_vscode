package my.lambda.test;

import io.micronaut.function.aws.MicronautRequestHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import io.micronaut.json.JsonMapper;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.inject.Inject;
import java.util.Collections;
import com.google.cloud.functions.*;
import io.micronaut.gcp.function.GoogleFunctionInitializer;

import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.micronaut.azure.function.AzureFunction;
import java.util.Optional;

import jakarta.inject.*;
import java.util.*;


import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
      APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

      String myPath = Paths.get(input.getBody()).normalize().toString();

      // ruleid: file-taint-sls
      File fff = new File(BASE_PATH, myPath);

      String content = new String(Files.readAllBytes(fff.toPath()));
      response.setStatusCode(200);
      response.setBody(content);
      return response;
    }
}

public class OkFunctionRequestHandler extends NOTaMicronautRequest {

    public APIGatewayProxyResponseEvent execute(Event input) {
      APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
      String myPath = Paths.get(BASE_PATH, input.getBody()).normalize().toString();

      if (!myPath.startsWith(BASE_PATH + File.separator)) {
        throw new Exception("Path traversal!");
      }

      // ok: file-taint-sls
      File fff = new File(myPath);

      String content = new String(Files.readAllBytes(fff.toPath()));
      response.setStatusCode(200);
      response.setBody(content);
      return response;

    }
}

public class Function extends GoogleFunctionInitializer
        implements BackgroundFunction<PubSubMessage> {

    @Inject LoggingService loggingService;

    @Override
    public void accept(PubSubMessage message, Context context) {
      String myPath = Paths.get(BASE_PATH, message.name).normalize().toString();

      char[] result = new char[100];

      // ruleid: file-taint-sls
      FileReader input = new FileReader(myPath);
      input.read(result);

      loggingService.logMessage(result);
    }
}

public class OkFunction extends GoogleFunctionInitializer
        implements BackgroundFunction<PubSubMessage> {

    @Inject LoggingService loggingService;

    @Override
    public void accept(PubSubMessage message, Context context) {
      String fname = context.eventType();

      if (fname.contains("..") || fname.contains("/") || fname.contains("\\")) {
        throw new Exception("Path traversal");
      }

      String myPath = Paths.get(BASE_PATH, fname).normalize().toString();

      char[] result = new char[100];

      // ok: file-taint-sls
      FileReader input = new FileReader(myPath);
      input.read(result);

      loggingService.logMessage(result);
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
      String myPath = Paths.get(BASE_PATH, request.getBody()).normalize().toString();

      byte[] result = new byte[100];

      // ruleid: file-taint-sls
      FileInputStream input = new FileInputStream(myPath);
      input.read(result);

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
      byte[] result = new byte[100];

      // ok: file-taint-sls
      FileInputStream input = new FileInputStream("hardcoded/path/foobar.txt");
      input.read(result);

      if (context != null) {
          context.getLogger().info("Executing Function: " + getClass().getName());
      }
      return request.createResponseBuilder(HttpStatus.OK).body("Hello World").build();
    }
}
