package com.inflinx.book.ldap.paging;

import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

public class PagingJndi {
	
	// We will be getting the LDAP context
	private LdapContext getContext() {
		Properties environment = new Properties();
		environment.setProperty(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.setProperty(DirContext.PROVIDER_URL, "ldap://localhost:11389");
		environment.setProperty(DirContext.SECURITY_PRINCIPAL, "cn=Directory Manager");
		environment.setProperty(DirContext.SECURITY_CREDENTIALS, "opendj");
		
		try {
			// The second argument is the list of controls that we need to send 
			// as part of the connection request
			return new InitialLdapContext(environment, null);
		}
		catch(NamingException e) {
			return null;
		}
	}
		
	public void pageAllPatrons() {
		
		try {
			
			LdapContext context = getContext();
			PagedResultsControl prc = new PagedResultsControl(20, Control.CRITICAL);
			
			context.setRequestControls(new Control[]{prc});
			byte[] cookie = null;
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			do {
				NamingEnumeration results = context.search("dc=inflinx,dc=com", "(objectClass=inetOrgPerson)", searchControls);

				// Iterate over search results
				while (results != null && results.hasMore()) {
					// Display an entry
					SearchResult entry = (SearchResult)results.next();
					System.out.println(entry.getAttributes().get("sn") + " ( " + (entry.getName()) + " )");
				}
		     
				// Examine the paged results control response
				Control[] controls = context.getResponseControls();
				if (controls != null) {
					for (int i = 0; i < controls.length; i++) {
						if (controls[i] instanceof PagedResultsResponseControl) {
							PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
							int resultCount = prrc.getResultSize();
							cookie = prrc.getCookie();
						}
					}
				}
	         
				// Re-activate paged results
				context.setRequestControls(new Control[]{ new PagedResultsControl(20, cookie, Control.CRITICAL) });
	
			} while(cookie != null);
		     
			context.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new PagingJndi().pageAllPatrons();
	}
}