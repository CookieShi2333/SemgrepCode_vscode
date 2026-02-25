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
import java.io.*;
import org.apache.commons.io.input.ClassLoaderObjectInputStream;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    JsonMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        String b64token;
        long before;
        long after;
        int delay;

        b64token = input.getBody().token.replace('-', '+').replace('_', '/');

        try (ObjectInputStream ois =
            // ruleid: objectinputstream-deserialization-sls
            new ClassLoaderObjectInputStream(Thread.currentThread().getContextClassLoader(), new ByteArrayInputStream(Base64.getDecoder().decode(b64token)))) {
          before = System.currentTimeMillis();
          Object o = ois.readObject();
          if (!(o instanceof VulnerableTaskHolder)) {
            if (o instanceof String) {
              response.setStatusCode(500);
            }
            response.setStatusCode(500);
          }
          after = System.currentTimeMillis();
          response.setStatusCode(200);
          response.setBody(json);
        } catch (Exception e) {
          response.setStatusCode(500);
        }
        return response;
    }
}

public class OkFunctionRequestHandler extends NOTaMicronautRequest {

    public APIGatewayProxyResponseEvent execute(Event input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
          FileInputStream fis = new FileInputStream("t.tmp");
          // ok: objectinputstream-deserialization-sls
          ObjectInputStream ois = new ObjectInputStream(fis);

          int i = ois.readInt();
          String today = (String) ois.readObject();
          Date date = (Date) ois.readObject();

          ois.close();

          response.setStatusCode(200);
          response.setBody(date);
        } catch (IOException e) {
          response.setStatusCode(500);
        }
        return response;
    }
}
