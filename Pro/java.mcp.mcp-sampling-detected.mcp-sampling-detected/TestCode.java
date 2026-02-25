import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

// MCP Sampling Request Examples

class McpSamplingServer {

    // Example 1: Basic sampling request with Map.put
    public CompletableFuture<Map<String, Object>> requestSampling1(
        String context, String uri, int maxTokens, RequestSender sendRequest
    ) {
        Map<String, Object> request = new HashMap<>();
        // ruleid: mcp-sampling-detected
        request.put("method", "sampling/createMessage");

        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");

        Map<String, Object> content = new HashMap<>();
        content.put("type", "text");
        content.put("text", String.format("Resource %s context: %s", uri, context));
        message.put("content", content);

        messages.add(message);
        params.put("messages", messages);
        params.put("systemPrompt", "You are a helpful test server.");
        params.put("maxTokens", maxTokens);
        params.put("temperature", 0.7);
        params.put("includeContext", "thisServer");

        request.put("params", params);
        return sendRequest.send(request);
    }

    // Example 2: Sampling with builder pattern
    public CompletableFuture<Map<String, Object>> requestSampling2(
        String prompt, RequestSender sendRequest
    ) {
        Map<String, Object> request = new HashMap<>();
        // ruleid: mcp-sampling-detected
        request.put("method", "sampling/createMessage");

        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", Map.of("type", "text", "text", prompt));
        messages.add(message);

        params.put("messages", messages);
        params.put("maxTokens", 100);
        request.put("params", params);

        return sendRequest.send(request);
    }

    // Example 3: Inline sampling request with Map.of
    public CompletableFuture<Map<String, Object>> requestSampling3(
        RequestSender sendRequest, String userPrompt
    ) {
        return sendRequest.send(Map.of(
            // ruleid: mcp-sampling-detected
            "method", "sampling/createMessage",
            "params", Map.of(
                "messages", List.of(
                    Map.of(
                        "role", "user",
                        "content", Map.of("type", "text", "text", userPrompt)
                    )
                ),
                "maxTokens", 150
            )
        ));
    }

    // Example 4: Using constant
    public CompletableFuture<Map<String, Object>> requestSampling4(
        RequestSender sendRequest
    ) {
        // ruleid: mcp-sampling-detected
        String methodName = "sampling/createMessage";

        Map<String, Object> request = new HashMap<>();
        // ruleid: mcp-sampling-detected
        request.put("method", methodName);

        Map<String, Object> params = new HashMap<>();
        params.put("messages", List.of(
            Map.of(
                "role", "user",
                "content", Map.of("type", "text", "text", "Generate a summary")
            )
        ));
        params.put("maxTokens", 200);

        request.put("params", params);
        return sendRequest.send(request);
    }

    // Example 5: Sampling in tool handler
    public Map<String, Object> handleTool(
        String name, Map<String, Object> arguments, RequestSender sendRequest
    ) throws Exception {
        if ("SAMPLE_LLM".equals(name)) {
            String prompt = (String) arguments.get("prompt");
            Integer maxTokens = (Integer) arguments.getOrDefault("maxTokens", 100);

            Map<String, Object> samplingRequest = new HashMap<>();
            // ruleid: mcp-sampling-detected
            samplingRequest.put("method", "sampling/createMessage");

            Map<String, Object> params = new HashMap<>();
            params.put("messages", List.of(
                Map.of(
                    "role", "user",
                    "content", Map.of("type", "text", "text", prompt)
                )
            ));
            params.put("systemPrompt", "You are a helpful assistant.");
            params.put("maxTokens", maxTokens);
            params.put("temperature", 0.7);

            samplingRequest.put("params", params);

            Map<String, Object> result = sendRequest.send(samplingRequest).get();
            Map<String, Object> content = (Map<String, Object>) result.get("content");
            String text = (String) content.getOrDefault("text", "No response");

            return Map.of("content", List.of(Map.of("type", "text", "text", text)));
        }

        throw new IllegalArgumentException("Unknown tool");
    }

    // Example 6: Sampling with conversation history
    public CompletableFuture<Map<String, Object>> requestSamplingWithHistory(
        List<Map<String, Object>> conversationHistory,
        String newMessage,
        RequestSender sendRequest
    ) {
        List<Map<String, Object>> messages = new ArrayList<>(conversationHistory);
        messages.add(Map.of(
            "role", "user",
            "content", Map.of("type", "text", "text", newMessage)
        ));

        Map<String, Object> request = new HashMap<>();
        // ruleid: mcp-sampling-detected
        request.put("method", "sampling/createMessage");

        Map<String, Object> params = new HashMap<>();
        params.put("messages", messages);
        params.put("systemPrompt", "You are a helpful assistant with memory.");
        params.put("maxTokens", 500);
        params.put("temperature", 0.8);
        params.put("includeContext", "allServers");

        request.put("params", params);
        return sendRequest.send(request);
    }

    // Example 7: Sampling with dynamic system prompt
    public CompletableFuture<Map<String, Object>> requestSamplingDynamic(
        String userQuery, String context, RequestSender sendRequest
    ) {
        String systemPrompt = String.format("You are an expert assistant. Context: %s", context);

        return sendRequest.send(Map.of(
            // ruleid: mcp-sampling-detected
            "method", "sampling/createMessage",
            "params", Map.of(
                "messages", List.of(
                    Map.of("role", "user", "content", Map.of("type", "text", "text", userQuery))
                ),
                "systemPrompt", systemPrompt,
                "maxTokens", 300,
                "temperature", 0.5
            )
        ));
    }

    // Example 8: Batch sampling requests
    public List<CompletableFuture<Map<String, Object>>> batchSampling(
        List<String> prompts, RequestSender sendRequest
    ) {
        List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();

        for (String prompt : prompts) {
            Map<String, Object> request = Map.of(
                // ruleid: mcp-sampling-detected
                "method", "sampling/createMessage",
                "params", Map.of(
                    "messages", List.of(
                        Map.of("role", "user", "content", Map.of("type", "text", "text", prompt))
                    ),
                    "maxTokens", 100
                )
            );
            futures.add(sendRequest.send(request));
        }

        return futures;
    }

    // Example 9: Conditional sampling
    public CompletableFuture<Map<String, Object>> conditionalSampling(
        boolean useAI, String query, RequestSender sendRequest
    ) {
        if (useAI) {
            Map<String, Object> request = new HashMap<>();
            // ruleid: mcp-sampling-detected
            request.put("method", "sampling/createMessage");

            Map<String, Object> params = new HashMap<>();
            params.put("messages", List.of(
                Map.of("role", "user", "content", Map.of("type", "text", "text", query))
            ));
            params.put("maxTokens", 100);

            request.put("params", params);
            return sendRequest.send(request);
        }
        return CompletableFuture.completedFuture(Map.of("content", "AI disabled"));
    }

    // Non-sampling examples (should not match)

    // ok: mcp-sampling-detected
    public CompletableFuture<Map<String, Object>> regularRequest(RequestSender sendRequest) {
        Map<String, Object> request = new HashMap<>();
        request.put("method", "tools/list");
        request.put("params", new HashMap<>());
        return sendRequest.send(request);
    }

    // ok: mcp-sampling-detected
    public CompletableFuture<Map<String, Object>> anotherRegularRequest(RequestSender sendRequest) {
        return sendRequest.send(Map.of(
            "method", "resources/list",
            "params", Map.of()
        ));
    }

    // ok: mcp-sampling-detected
    String commentExample = "This function uses sampling/createMessage for AI";

    // Helper interface
    interface RequestSender {
        CompletableFuture<Map<String, Object>> send(Map<String, Object> request);
    }
}
