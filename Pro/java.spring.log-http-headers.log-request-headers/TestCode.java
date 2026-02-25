package com.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

@Component("com.example.ApiClient")
public class ApiClient {

  private final Log log = LogFactory.getLog(ApiClientHttpRequestInterceptor.class);

  private String headersToString(HttpHeaders headers) {
    StringBuilder builder = new StringBuilder();
    for (Entry<String, List<String>> entry : headers.entrySet()) {
      builder.append(entry.getKey()).append("=[");
      for (String value : entry.getValue()) {
        builder.append(value).append(",");
      }
      builder.setLength(builder.length() - 1); // Get rid of trailing comma
      builder.append("],");
    }
    builder.setLength(builder.length() - 1); // Get rid of trailing comma
    return builder.toString();
  }

  private void logRequest(HttpRequest request) {
    log.info("URI: " + request.getURI());
    // ok: log-request-headers
    log.info("HTTP Method: " + request.getMethod());
    // ruleid: log-request-headers
    log.info("HTTP Headers: " + headersToString(request.getHeaders()));
  }
}
