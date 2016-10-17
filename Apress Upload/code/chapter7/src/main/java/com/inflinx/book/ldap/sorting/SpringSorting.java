package com.inflinx.book.ldap.sorting;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.control.SortControlDirContextProcessor;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.simple.AbstractParameterizedContextMapper;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import com.inflinx.book.ldap.control.SortMultipleControlDirContextProcessor;

@Component("sorting")
public class SpringSorting {

	@Autowired
	@Qualifier("ldapTemplate")
	private SimpleLdapTemplate ldapTemplate;
	
	public void sortByLocation() {
		
		String[] locationAttributes = {"st", "l"};
		SortMultipleControlDirContextProcessor smcdcp = new SortMultipleControlDirContextProcessor(locationAttributes);
		
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		EqualsFilter equalsFilter = new EqualsFilter("objectClass", "inetOrgPerson");
		
		@SuppressWarnings("unchecked")
		ParameterizedContextMapper<String> locationMapper = new AbstractParameterizedContextMapper<String>() {
			@Override
			protected String doMapFromContext(DirContextOperations context) {
				return context.getStringAttribute("st") + "," + context.getStringAttribute("l");
			}
		};
		
		List<String> results = ldapTemplate.search("", equalsFilter.encode(), 
								searchControls, locationMapper, smcdcp);
		
		for(String r : results) {
			System.out.println(r);
		}
	}
	
	public List<String> sortByLastName() {
		
		DirContextProcessor scdcp = new SortControlDirContextProcessor("sn");
		
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		EqualsFilter equalsFilter = new EqualsFilter("objectClass", "inetOrgPerson");
		
		@SuppressWarnings("unchecked")
		ParameterizedContextMapper<String> lastNameMapper = new AbstractParameterizedContextMapper<String>() {
			@Override
			protected String doMapFromContext(DirContextOperations context) {
				return context.getStringAttribute("sn");
			}
		};
		
		List<String> lastNames = ldapTemplate.search("", equalsFilter.encode(), 
								searchControls, lastNameMapper, scdcp);
		
		for(String ln : lastNames) {
			System.out.println(ln);
		}
		return lastNames;
	}
}