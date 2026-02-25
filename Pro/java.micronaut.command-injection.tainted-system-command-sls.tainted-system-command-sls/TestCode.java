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

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    JsonMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {

            String comd = "ls -lah " + input.getBody();
            // ruleid: tainted-system-command-sls
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            doSmth(stdInput);

            String json = new String(objectMapper.writeValueAsBytes(Collections.singletonMap("message", "Hello World")));
            response.setStatusCode(200);
            response.setBody(json);
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

            String comd = "ls -lah " + input.getBody();
            // ok: tainted-system-command-sls
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            doSmth(stdInput);

            String json = new String(objectMapper.writeValueAsBytes(Collections.singletonMap("message", "Hello World")));
            response.setStatusCode(200);
            response.setBody(json);
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

        String comd = "ls -lah " + message.name;
        // ruleid: tainted-system-command-sls
        Process p =  new ProcessBuilder(new String[]{"bash", "-c", comd}).start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        doSmth(stdInput);

        loggingService.logMessage(message);
    }
}

public class OkFunction extends GoogleFunctionInitializer
        implements BackgroundFunction<PubSubMessage> {

    @Inject LoggingService loggingService;

    @Override
    public void accept(PubSubMessage message, Context context) {

        String comd = "ls -lah " + context.eventType();
        // ok: tainted-system-command-sls
        Process p =  new ProcessBuilder(new String[]{"bash", "-c", comd}).start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        doSmth(stdInput);

        loggingService.logMessage(message);
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

        // ruleid: tainted-system-command-sls
        ProcessBuilder processBuilder = new ProcessBuilder("ls -lah " + request.getBody());
        Process process = processBuilder.start();
        processBuilder.redirectErrorStream(true);

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
          doSmth(stdInput);
        }

        if (context != null) {
            context.getLogger().info("Executing Function: " + getClass().getName());
        }
        return request.createResponseBuilder(HttpStatus.OK).body("Hello World").build();
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

        // ok: tainted-system-command-sls
        ProcessBuilder processBuilder = new ProcessBuilder("ls -lah /tmp");
        Process process = processBuilder.start();
        processBuilder.redirectErrorStream(true);

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
          doSmth(stdInput);
        }

        if (context != null) {
            context.getLogger().info("Executing Function: " + getClass().getName());
        }
        return request.createResponseBuilder(HttpStatus.OK).body("Hello World").build();
    }
}
