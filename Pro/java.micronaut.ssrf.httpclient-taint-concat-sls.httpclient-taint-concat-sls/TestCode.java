package com.example;

import java.io.IOException;
import java.util.Optional;

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
import io.micronaut.gcp.function.GoogleFunctionInitializer;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.json.JsonMapper;
import jakarta.inject.*;
import jakarta.inject.Inject;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    JsonMapper objectMapper;

    @Inject
    @Client("/")
    private HttpClient client;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {

            String uri = "https://" + input.getBody();
            // ruleid: httpclient-taint-concat-sls
            String result = client.toBlocking().retrieve(uri);
            response.setStatusCode(200);
            response.setBody(result);
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}

public class OkFunctionRequestHandler extends NOTaMicronautRequest {

    @Inject
    @Client("/")
    private HttpClient client;

    public APIGatewayProxyResponseEvent execute(Event input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {

            String uri = "https://www.google.com?x=" + input.getBody();
            // ok: httpclient-taint-concat-sls
            String result = client.toBlocking().retrieve(uri);
            response.setStatusCode(200);
            response.setBody(result);
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}

public class Function extends GoogleFunctionInitializer
        implements BackgroundFunction<PubSubMessage> {

    @Inject LoggingService loggingService;

    @Inject
    @Client("/")
    private HttpClient client;

    @Override
    public void accept(PubSubMessage message, Context context) {
      // ruleid: httpclient-taint-concat-sls
      String result = client.toBlocking().retrieve("http://" + message.uri);
      loggingService.logMessage(result);
    }
}

public class OkFunction extends GoogleFunctionInitializer
        implements BackgroundFunction<PubSubMessage> {

    @Inject LoggingService loggingService;

    @Inject
    @Client("/")
    private HttpClient client;

    @Override
    public void accept(PubSubMessage message, Context context) {
      // ok: httpclient-taint-concat-sls
      String result = client.toBlocking().retrieve("https://www.example.com/");
      loggingService.logMessage(result);
    }
}

public class Function2 extends AzureFunction {
    @Inject
    @Client("/")
    private HttpClient client;

    @FunctionName("HttpTrigger")
    public HttpResponseMessage hello(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    route = "{*route}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
      HttpRequest<?> req = HttpRequest.GET(String.format("https://%s", request.getBody()))
              .header(USER_AGENT, "Micronaut HTTP Client")
              .header(ACCEPT, "application/vnd.github.v3+json, application/json");
      // ruleid: httpclient-taint-concat-sls
      String result =  client.toBlocking().retrieve(req, Argument.listOf(GithubRelease.class));
      return request.createResponseBuilder(HttpStatus.OK).body(result).build();
    }
}

public class OkFunction2 extends AzureFunction {

    @Inject
    @Client("/")
    private HttpClient client;

    @FunctionName("HttpTrigger")
    public HttpResponseMessage hello(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    route = "{*route}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
      HttpRequest<?> req = HttpRequest.GET(String.format("https://www.example.com/%s", request.getBody()))
              .header(USER_AGENT, "Micronaut HTTP Client")
              .header(ACCEPT, "application/vnd.github.v3+json, application/json");
      // ok: httpclient-taint-concat-sls
      String result =  client.toBlocking().retrieve(req, Argument.listOf(GithubRelease.class));
      return request.createResponseBuilder(HttpStatus.OK).body(result).build();
    }
}
