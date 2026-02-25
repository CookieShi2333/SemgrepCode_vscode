@Controller
public class CookieController {

    @RequestMapping(value = "/cookie1", method = "GET")
    public void setCookie(@RequestParam String value, HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie", value);
        // ok: cookie-secure-flag-false
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/cookie2", method = "GET")
    public void setSecureCookie(@RequestParam String value, HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie", value);
        // ok: cookie-secure-flag-false
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/cookie3", method = "GET")
    public void setSecureHttponlyCookie(@RequestParam String value, HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie", value);
        // ok: cookie-secure-flag-false
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @RequestMapping(value = "/cookie4", method = "GET")
    public void explicitDisable(@RequestParam String value, HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie", value);
        // ruleid: cookie-secure-flag-false
        cookie.setSecure(false);
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
    }

}
