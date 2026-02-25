package org.owasp.benchmark.testcode;

// THIS RULE HAS BEEN DEPRECATED.

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/cmdi-00/BenchmarkTest00006")
public class bad1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // some code
        response.setContentType("text/html;charset=UTF-8");

        String param = "";
        if (request.getHeader("BenchmarkTest00006") != null) {
            param = request.getHeader("BenchmarkTest00006");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String url = "https://" + param;

        // ok: tainted-ssrf-deepsemgrep
        new URL(url).openConnection();
        // ok: tainted-ssrf-deepsemgrep
        new URL(url).openStream();

        // ok: tainted-ssrf-deepsemgrep
        URL url = new URL(url);
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String body = reader.lines().collect(Collectors.joining());
        return body;
    }
}
