package com.inflinx.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.simple.AbstractParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.Filter;
import org.springframework.stereotype.Component;

@Component("searchFilterDemo")
public class SearchFilterDemo {
	
	@Autowired
	@Qualifier("ldapTemplate")
	private SimpleLdapTemplate ldapTemplate;
	
	public void searchAndPrintResults(Filter filter) {
		List<String> results = ldapTemplate.search("ou=patrons,dc=inflinx,dc=com", filter.encode(), 
							new AbstractParameterizedContextMapper<String>() {
								@Override
								protected String doMapFromContext(DirContextOperations context) {
									return context.getStringAttribute("cn");
								}
							});
		System.out.println("Results found in search: " + results.size()); 
		for(String commonName: results) {
			System.out.println(commonName);
		}
	}
}