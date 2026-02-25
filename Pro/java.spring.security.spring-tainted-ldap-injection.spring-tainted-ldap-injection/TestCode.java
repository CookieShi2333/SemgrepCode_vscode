package com.test;

import javax.naming.NamingException;
import javax.naming.directory.*;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.RDN;
import com.unboundid.ldap.sdk.Filter;
import org.apache.directory.ldap.client.api.search.FilterBuilder.equal;

class Test {
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @GetMapping("/api/bad1")
    @ResponseBody
    public List getAllPersonNames(@RequestParam String personname) {
        query = "objectclass=" + personname;
        return ldapTemplate.search(
        // ruleid: spring-tainted-ldap-injection
            "", query,
            new AttributesMapper() {
                public Object mapFromAttributes(Attributes attrs)
                throws NamingException {
                return attrs.get("cn").get();
                }
            });
    }

    @GetMapping("/api/bad2")
    @ResponseBody
    public boolean bad2(@RequestParam String username, String password) throws NamingException {
        DirContext ctx = new InitialDirContext(ENV);

        String base      = "dc=example,dc=com";
        String ldapQuery = "(&(uid=" + username + ")(userPassword=" + password + "))";

        // ruleid: spring-tainted-ldap-injection
        NamingEnumeration<?> results = ctx.search(base, ldapQuery, new SearchControls());

        try {
            return results.hasMore();
        }
        finally {
            ctx.close();
        }
    }

    @GetMapping("/api/bad3")
    @ResponseBody
    public void bad3(@RequestParam String organizationName, @RequestParam String username, LDAPConnection c) {

        // GOOD: Organization name is encoded before being used in DN
        DN safeDn = new DN(new RDN("OU", "People"), new RDN("O", organizationName));

        // GOOD: User input is encoded before being used in search filter
        Filter safeFilter = Filter.createEqualityFilter("username", username);

        // ruleid: spring-tainted-ldap-injection
        c.search(organizationName, SearchScope.ONE, safeFilter);
    }

    @GetMapping("/api/bad4")
    @ResponseBody
    public void bad4(@RequestParam String organizationName, @RequestParam String username, LdapConnection c) {
        Dn safeDn = new Dn(new Rdn("OU", "People"), new Rdn("O", organizationName));

        SearchRequest searchRequest = new SearchRequestImpl();
        searchRequest.setBase(safeDn);
        searchRequest.setFilter(username);
        // ruleid: spring-tainted-ldap-injection
        c.search(searchRequest);
    }

    @GetMapping("/api/bad5")
    @ResponseBody
    public void good2(@RequestParam String organizationName, @RequestParam String username, LDAPConnection c) {
        LdapQuery query = query()
        .base(organizationName)
        .where("username").is(username);

        // ruleid: spring-tainted-ldap-injection
        ldapTemplate.search(query, new AttributeCheckAttributesMapper());
    }

    @GetMapping("/api/good1")
    @ResponseBody
    public void good1(@RequestParam String organizationName, @RequestParam String username, LDAPConnection c) {

        // GOOD: Organization name is encoded before being used in DN
        DN safeDn = new DN(new RDN("OU", "People"), new RDN("O", organizationName));

        // GOOD: User input is encoded before being used in search filter
        Filter safeFilter = Filter.createEqualityFilter("username", username);

        // ok: spring-tainted-ldap-injection
        c.search(safeDn.toString(), SearchScope.ONE, safeFilter);
      }


    @GetMapping("/api/good2")
    @ResponseBody
    public void good2(@RequestParam String organizationName, @RequestParam String username, LDAPConnection c) {
        // GOOD: Organization name is encoded before being used in DN
        String safeDn = LdapNameBuilder.newInstance()
        .add("O", organizationName)
        .add("OU=People")
        .build().toString();

        String safeFilter = equal("username", username);

        // GOOD: User input is encoded before being used in search filter
        LdapQuery query = query()
        .base(safeDn)
        .where("username").is(safeFilter);

        // ok: spring-tainted-ldap-injection
        ldapTemplate.search(query, new AttributeCheckAttributesMapper());
    }

    @GetMapping("/api/good3")
    @ResponseBody
    public void good3(@RequestParam String organizationName, @RequestParam String username, LdapConnection c) {
        // GOOD: Organization name is encoded before being used in DN
        Dn safeDn = new Dn(new Rdn("OU", "People"), new Rdn("O", organizationName));

        // GOOD: User input is encoded before being used in search filter
        String safeFilter = equal("username", username);

        SearchRequest searchRequest = new SearchRequestImpl();
        searchRequest.setBase(safeDn);
        searchRequest.setFilter(safeFilter);
        // ok: spring-tainted-ldap-injection
        c.search(searchRequest);
    }

    @GetMapping("/api/good4")
    @ResponseBody
    public void good4(@RequestParam String organizationName, @RequestParam String username, DirContext ctx) throws NamingException {
        // ESAPI encoder
        Encoder encoder = DefaultEncoder.getInstance();

        // GOOD: Organization name is encoded before being used in DN
        String safeOrganizationName = encoder.encodeForDN(organizationName);
        String safeDn = "OU=People,O=" + safeOrganizationName;

        // GOOD: User input is encoded before being used in search filter
        String safeUsername = encoder.encodeForLDAP(username);
        String safeFilter = "username=" + safeUsername;

        // ok: spring-tainted-ldap-injection
        ctx.search(safeDn, safeFilter, new SearchControls());
      }
}
