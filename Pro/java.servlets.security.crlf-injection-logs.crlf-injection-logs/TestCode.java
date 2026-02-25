package com.vogella.logger.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vogella.logger.MyLogger;


public class TestLog1 {
  private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void testLog1(String name, HttpServletRequest request) {
        String[] params = request.getParameterValues("logs");
        if (params == null) {
            return;
        }
        for (i = 0; i < params.length; i++) {
            String param = params[i];
            String toLog = "testing testing" + param + "testing";
            // ruleid: crlf-injection-logs
            log.info("foo"+toLog);

        }
    }
}

public class TestLog2 {
  private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void cookieD(String name, HttpServletRequest request) {
        javax.servlet.http.Cookie[] cb = request.getCookies();
        if (servletCookies == null) {
            return;
        }
        for (javax.servlet.http.Cookie c : cb) {
            // ruleid: crlf-injection-logs
            log.info("foo"+c.getValue());

        }
    }
}

public class OkTestLog1 {
  private final static NotLogger log = new NorLogger();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpServletReq = (HttpServletRequest) request;
      String param = httpServletReq.getParameter("param");
      // ok: crlf-injection-logs
      log.info(param);
  }
}


public class TestLog3 {
  private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void testLog1(String name, HttpServletRequest request) {
        String[] params = request.getParameterValues("logs");
        if (params == null) {
            return;
        }
        for (i = 0; i < params.length; i++) {
            String param = params[i];
            String toLog = "testing testing" + param + "testing";
            // ok: crlf-injection-logs
            log.info("foo"+Jsoup.clean(toLog, Whitelist.basic));

        }
    }
}


public class OkTestLog2 {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain) throws IOException, ServletException {
      Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
      HttpServletRequest httpServletReq = (HttpServletRequest) request;
      String param = "foobar";
      // ok: crlf-injection-logs
      log.log(log.getLevel(), param);
  }
}
