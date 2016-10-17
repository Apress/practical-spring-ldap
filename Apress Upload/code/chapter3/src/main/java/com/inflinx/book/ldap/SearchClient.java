package com.inflinx.book.ldap;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

@Component
public class SearchClient {

	@SuppressWarnings("unchecked")
	public List<String> search() {
		LdapTemplate ldapTemplate = getLdapTemplate();
		
		List<String> nameList = ldapTemplate.search("dc=inflinx,dc=com", "(objectclass=person)", 
				new AttributesMapper() {
					@Override
					public Object mapFromAttributes(Attributes attributes) throws NamingException {
						return (String)attributes.get("cn").get();
					} });		
		return nameList;
	}
	
	private LdapTemplate getLdapTemplate() { 
		
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl("ldap://localhost:11389");
		contextSource.setUserDn("cn=Directory Manager");
		contextSource.setPassword("opendj");
		try {
			contextSource.afterPropertiesSet();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		LdapTemplate ldapTemplate = new LdapTemplate();
		ldapTemplate.setContextSource(contextSource);
		
		return ldapTemplate; 
	}

	public static void main(String[] args) {
		SearchClient client = new SearchClient();
		List<String> names = client.search();
		
		for(String name: names) {
			System.out.println(name);
		}
	}
}
