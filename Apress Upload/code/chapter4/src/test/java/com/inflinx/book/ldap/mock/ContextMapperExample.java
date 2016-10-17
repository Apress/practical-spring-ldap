package com.inflinx.book.ldap.mock;

import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.ldap.core.DirContextOperations;

import com.inflinx.book.ldap.EmployeeContextMapper;
import com.inflinx.book.ldap.domain.Employee;

public class ContextMapperExample {

	@Test
	public void testConextMapper() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("uid", "employee1");
		attributes.put("givenName", "John");
		attributes.put("surname", "Doe");
		attributes.put("telephoneNumber", new String[]{"8011001000", "8011001001"});
		
		DirContextOperations contextOperations = LdapMockUtils.mockContextOperations(attributes);
		
		replay(contextOperations);
		
		// Now we can use the context operations to test a mapper
		EmployeeContextMapper mapper = new EmployeeContextMapper();
		Employee employee = (Employee)mapper.mapFromContext(contextOperations);
		
		verify(contextOperations);
		
		// test the employee object
		assertEquals(employee.getUid(), "employee1");
		assertEquals(employee.getFirstName(), "John");
	}
	
}
