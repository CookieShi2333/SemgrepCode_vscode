package org.owasp.webgoat.lessons.xss;

import java.util.function.Predicate;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.jsoup.Jsoup;
import org.owasp.encoder.Encode;

@RestController
public class CrossSiteScriptingLesson5a extends AssignmentEndpoint {
  @GetMapping("/CrossSiteScripting/vulnerable1")
  @ResponseBody
  public AttackResult vulnerable1(@RequestParam String field1) {
    String html = "<p>We have charged credit card:" + field1;
    AttackResult result = new AttackResult(html);
    // ruleid: tainted-html-string-responsebody
    return result;
  }

  @GetMapping("/CrossSiteScripting/vulnerable2")
  @ResponseBody
  public AttackResult vulnerable2(@RequestParam String field1) {
    String html = "We have charged credit card:" + field1 + "<BR />";
    AttackResult result = new AttackResultBuilder().build(new String[]{ html });
    // ruleid: tainted-html-string-responsebody
    return result;
  }

  @GetMapping("/CrossSiteScripting/vulnerable3")
  @ResponseBody
  public AttackResult vulnerable3(@RequestParam String field1) {
    String formatted = String.format(" %s ", field1);
    String html = "We have charged credit card:" + formatted + "<br />";
    // ruleid: tainted-html-string-responsebody
    return attackResultBuilder.build(html);
  }

  @GetMapping("/CrossSiteScripting/vulnerable3")
  @ResponseBody
  public AttackResult vulnerable3(String field1) {
    String formatted = String.format(" %s ", field1);
    String html = "We have charged credit card:" + formatted + "<br />";
    // ruleid: tainted-html-string-responsebody
    return new AttackResult(html);
  }

  @GetMapping("/CrossSiteScripting/vulnerable4")
  @ResponseBody
  public AttackResult vulnerable4(String field1) {
    String formatted = "<p>We have charged credit card:".concat(field1);
    // ruleid: tainted-html-string-responsebody
    return new AttackResult(formatted);
  }

  @GetMapping("/CrossSiteScripting/vulnerable7")
  @ResponseBody
  public AttackResult vulnerable7(String field1) {
    String formatted = "<p>We have charged credit card: ";
    formatted += field1;
    // ruleid: tainted-html-string-responsebody
    return new AttackResult(formatted);
 }

  @GetMapping("/CrossSiteScripting/vulnerable8")
  @ResponseBody
  public AttackResult vulnerable8(String field1) {
    String format = "<p>We have charged credit card: %s";
    String formatted = String.format(format, field1);
    // ruleid: tainted-html-string-responsebody
    return new AttackResult(formatted);
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_no_html(@RequestParam String field1) {
    // ok: tainted-html-string-responsebody
    return new AttackResult("We have charged credit card:" + field1 + "!");
  }

  @GetMapping("/CrossSiteScripting/false-positive")
  @ResponseBody
  public AttackResult safe_field_is_checked(@RequestParam String field1) {
    if (field1 != "foo" && field1 != "bar") {
        return new AttackResult("Not foo or bar");
    }

    // TODO: false positive, field1 is validated above
    // ruleid: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + field1 + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_integer(@RequestParam String field1) {
    int n = Integer.parseInt(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + n.toString() + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_int(@RequestParam String field1) {
    Integer n = Integer.valueOf(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + n.toString() + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_boolean(@RequestParam String field1) {
    Boolean b = Boolean.parseBoolean(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + b.toString() + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_builtin_sanitizer(@RequestParam String field1) {
    PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    String sanitized = policy.sanitize(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + sanitized + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_custom_policy_factory(@RequestParam String field1) {
    PolicyFactory policy = new HtmlPolicyBuilder()
      .allowElements("p")
      .toFactory();
    String sanitized = policy.sanitize(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + sanitized + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_anti_samy(@RequestParam String field1) {
    Policy policy = Policy.getInstance("foo");
    AntiSamy as = new AntiSamy();
    String sanitized = as.scan(field1, policy);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + sanitized + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_jsoup(@RequestParam String field1) {
    String sanitized = Jsoup.clean(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + sanitized + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_forhtml(@RequestParam String field1) {
    String sanitized = Encode.forHtml(field1);
    // ok: tainted-html-string-responsebody
    return new AttackResult("<p>We have charged credit card:" + sanitized + "!");
  }

  @GetMapping("/CrossSiteScripting/safe")
  @ResponseBody
  public AttackResult safe_not_html_tag(@RequestParam String field1) {
    // ok: tainted-html-string-responsebody
    return new AttackResult("We have charged credit card:" + field1 + "<spanner!");
  }

  static {
    questions = new HashMap<>();
    questions.put("Foo?", "Bar!");
  }

  @PostMapping("/PasswordReset/SecurityQuestions")
  @ResponseBody
  public AttackResult completed(@RequestParam String question) {
    var answer = questions.get(question);
    if (answer != null) {
      // todook: tainted-html-string-responsebody
      return success(this).output("<b>" + answer + "</b>").build();
    }
    return informationMessage(this)
        .feedback("password-questions-one-successful")
        .output(answer.orElse("Unknown question, please try again..."))
        .build();
  }

  @PostMapping("SecurePasswords/assignment")
  @ResponseBody
  public AttackResult safe_format_decimal(@RequestParam String password) {
    Zxcvbn zxcvbn = new Zxcvbn();
    StringBuilder output = new StringBuilder();
    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    df.setMaximumFractionDigits(340);
    Strength strength = zxcvbn.measure(password);

    output.append(
        "<b>Estimated guesses needed to crack your password: </b>"
            + df.format(strength.getGuesses())
            + "</br>");

    // ok: tainted-html-string-responsebody
    return success(this).feedback("securepassword-success").output(output.toString()).build();
  }
}
