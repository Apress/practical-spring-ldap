package com.inflinx.book.ldap;

import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.inflinx.book.ldap.domain.Patron;

public class JndiObjectFactoryLookupExample {

	private LdapContext getContext() throws NamingException {
		Properties environment = new Properties();
		environment.setProperty(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.setProperty(DirContext.PROVIDER_URL, "ldap://localhost:11389");
		environment.setProperty(DirContext.SECURITY_PRINCIPAL, "cn=Directory Manager");
		environment.setProperty(DirContext.SECURITY_CREDENTIALS, "opendj");
		environment.setProperty(DirContext.OBJECT_FACTORIES, "com.inflinx.book.ldap.PatronObjectFactory");
		
		return new InitialLdapContext(environment, null);
	}

	public Patron lookupPatron(String dn) {	
		Patron patron = null;
		
		try {
			LdapContext context = getContext();
			patron = (Patron) context.lookup(dn);
		}
		catch(NamingException e) {
			e.printStackTrace();
		}
		return patron;
	}
	
	public static void main(String[] args) {
		JndiObjectFactoryLookupExample jle = new JndiObjectFactoryLookupExample();
		Patron p = jle.lookupPatron("uid=patron99,ou=patrons,dc=inflinx,dc=com");
		System.out.println(p);
	}
}
