package com.example;

import java.io.IOException;
import java.util.Optional;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            HttpRequest req = HttpRequest.newBuilder().uri(new URI("https://" + input.getBody())).GET().build();
            HttpClient cl = HttpClient.newBuilder().build();
            // ruleid: java-http-concat-taint-sls
            HttpResponse<String> res = cl.send(req, HttpResponse.BodyHandlers.ofString());

            response.setStatusCode(200);
            response.setBody(res);
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
            HttpRequest req = HttpRequest.newBuilder()
              .uri(new URI("https://www.example.com"))
              .headers("Foobar", "https://" + input.getBody())
              .GET()
              .build();
            // ok: java-http-concat-taint-sls
            String result = HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
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

    @Override
    public void accept(PubSubMessage message, Context context) {
      String url = String.format("https://%s", message.uri);

      // ruleid: java-http-concat-taint-sls
      new URL(url).openConnection();
      // ruleid: java-http-concat-taint-sls
      new URL(url).openStream();

      URL url = new URL(url);
      // ruleid: java-http-concat-taint-sls
      url.openConnection();
    }
}

public class OkFunction extends GoogleFunctionInitializer
        implements BackgroundFunction<PubSubMessage> {

    @Inject LoggingService loggingService;

    @Override
    public void accept(PubSubMessage message, Context context) {

      String url = String.format("https://www.example.com/%s", message.param);
      // ok: java-http-concat-taint-sls
      URL url = new URL(url);
      URLConnection connection = url.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String body = reader.lines().collect(Collectors.joining());

      loggingService.logMessage(body);
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
      HttpRequest req = HttpRequest.newBuilder().uri(new URI(String.format("https://%s", request.getBody()))).GET().build();
      // ruleid: java-http-concat-taint-sls
      return HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
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
      HttpRequest req = HttpRequest.newBuilder().uri(new URI(String.format("https://www.example.com/%s", request.getBody()))).GET().build();
      // ok: java-http-concat-taint-sls
      return HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
    }
}
