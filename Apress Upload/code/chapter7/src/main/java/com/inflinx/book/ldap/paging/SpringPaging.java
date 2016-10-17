package com.inflinx.book.ldap.paging;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.simple.AbstractParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

@Component("springPaging")
public class SpringPaging {

	@Autowired
	@Qualifier("ldapTemplate")
	private SimpleLdapTemplate ldapTemplate;

	
	public void pagedResults() {
	
		PagedResultsCookie cookie = null;
		
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		int page = 1;
		do {		
			 System.out.println("Starting Page: " + page);
			 PagedResultsDirContextProcessor processor = new PagedResultsDirContextProcessor(20, cookie);
			 EqualsFilter equalsFilter = new EqualsFilter("objectClass", "inetOrgPerson");
				
			 List<String> lastNames = ldapTemplate.search("", equalsFilter.encode(), 
										searchControls, new LastNameMapper(), processor);
			 
			 for(String l : lastNames) {
				 System.out.println(l);
			 }
			
			cookie = processor.getCookie();
			page = page + 1;
			
		} while(null != cookie.getCookie());
	}
	
	private class LastNameMapper extends AbstractParameterizedContextMapper<String> {
		@Override
		protected String doMapFromContext(DirContextOperations context) {
			return context.getStringAttribute("sn");
		}
	}
	
}
