##规则包名
java.mcp.mcp-sampling-detected.mcp-sampling-detected
##规则ID
mcp-sampling-detected

# java.mcp.mcp-sampling-detected.mcp-sampling-detected

## 规则名称
java.mcp.mcp-sampling-detected.mcp-sampling-detected

## 规则描述
Detected MCP sampling request using 'sampling/createMessage'. MCP sampling allows
servers to request the client to generate LLM responses on their behalf. This capability
should be carefully reviewed for security implications:

- Sampling requests can expose sensitive data in prompts to the LLM
- The server is asking the client to make LLM API calls, which may have cost implications
- Prompts may contain data from untrusted sources (tool parameters, resources)
- The LLM response is returned to the server and may influence subsequent actions

Best practices:
1. Sanitize and validate all data before including it in sampling prompts
2. Use system prompts to constrain LLM behavior and output format
3. Avoid including sensitive user data, credentials, or PII in prompts
4. Document that your server uses sampling and its purpose
5. Consider rate limiting or requiring user consent for sampling operations
6. Review the maxTokens parameter to avoid excessive costs



### 规则描述中文版
检测到使用 'sampling/createMessage' 的 MCP 采样请求。MCP 采样可能带来相关风险，请根据实际使用场景评估。

### 关联CWE
- CWE-200: Exposure of Sensitive Information to an Unauthorized Actor
