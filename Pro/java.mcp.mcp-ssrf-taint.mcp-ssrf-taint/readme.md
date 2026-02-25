##规则包名
java.mcp.mcp-ssrf-taint.mcp-ssrf-taint
##规则ID
mcp-ssrf-taint

# java.mcp.mcp-ssrf-taint.mcp-ssrf-taint

## 规则名称
java.mcp.mcp-ssrf-taint.mcp-ssrf-taint

## 规则描述
Detected MCP tool parameter data flowing into an HTTP request function.
This could result in a Server-Side Request Forgery (SSRF) vulnerability if an attacker
can control the URL. MCP tool parameters come from external clients and should be
treated as untrusted input. An attacker could use this to:
- Access internal services and APIs (e.g., http://localhost, http://169.254.169.254)
- Scan internal networks and port scan
- Exfiltrate sensitive data to external servers
- Bypass firewall restrictions
- Access cloud metadata endpoints (AWS, GCP, Azure)

To fix this:
1. Avoid making HTTP requests to user-controlled URLs
2. Use an allowlist of permitted domains or URL prefixes
3. Validate and parse URLs to ensure they match expected patterns
4. Block requests to private IP ranges (RFC 1918) and localhost
5. Use a dedicated service or proxy for external requests



### 规则描述中文版
检测到 MCP 工具参数数据流入 HTTP 请求函数，存在 SSRF 等风险。

### 关联CWE
- CWE-918: Server-Side Request Forgery (SSRF)
