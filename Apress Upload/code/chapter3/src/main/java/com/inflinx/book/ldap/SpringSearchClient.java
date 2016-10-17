package com.inflinx.book.ldap;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringSearchClient {
	
	@Autowired
	@Qualifier("ldapTemplate")
	private LdapTemplate ldapTemplate;
	
	@SuppressWarnings("unchecked")
	public List<String> search() {
		
		List<String> nameList = ldapTemplate.search("dc=inflinx,dc=com", "(objectclass=person)", 
				new AttributesMapper() {
					@Override
					public Object mapFromAttributes(Attributes attributes) throws NamingException {
						return (String)attributes.get("cn").get();
					} });		
		return nameList;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SpringSearchClient client = context.getBean(SpringSearchClient.class);
		List<String> names = client.search();
		
		for(String name: names) {
			System.out.println(name);
		}
	}
}
