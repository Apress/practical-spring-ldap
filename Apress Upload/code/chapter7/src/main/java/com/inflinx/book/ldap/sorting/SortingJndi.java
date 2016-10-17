package com.inflinx.book.ldap.sorting;

import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortResponseControl;

public class SortingJndi {
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
	
	public void sortByLastName() {
		
		try {
			LdapContext context = getContext();
			Control lastNameSort = new SortControl("sn", Control.CRITICAL);
			
			context.setRequestControls(new Control[]{lastNameSort});
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration results = context.search("dc=inflinx,dc=com", "(objectClass=inetOrgPerson)", searchControls);
			
			// Iterate over search results and display patron entries
		    while (results != null && results.hasMore()) {
		         SearchResult entry = (SearchResult)results.next();
		         System.out.println(entry.getAttributes().get("sn") + " ( " + (entry.getName()) + " )");
		    }    
			
		    // Now that we have looped, we need to look at the response controls
		    Control[] responseControls = context.getResponseControls();
		    if(null != responseControls) {
		    	for(Control control : responseControls) {
		    		if(control instanceof SortResponseControl) {
		    			 SortResponseControl sortResponseControl = (SortResponseControl) control;
		    			 if(!sortResponseControl.isSorted()) {
		    				 // Sort did not happen. Indicate this with an exception
		    				 throw sortResponseControl.getException();
		    			 }
		    		}
		    	}
		    }
		    
		    context.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		SortingJndi sortingJndi = new SortingJndi();
		sortingJndi.sortByLastName();
	}
}