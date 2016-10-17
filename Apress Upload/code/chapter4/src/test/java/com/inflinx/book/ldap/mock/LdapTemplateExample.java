package com.inflinx.book.ldap.mock;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.ldap.core.LdapTemplate;

import com.inflinx.book.ldap.EmployeeContextMapper;
import com.inflinx.book.ldap.domain.Employee;

public class LdapTemplateExample {
	
	@Test
	public void testLdapTemplate() {
		String base = "dc=inflinx,dc=com";
		String search = "(objectClass=inetOrgPerson)";
		
		// Lets say we want to test the search method
		LdapTemplate ldapTemplate = createMock(LdapTemplate.class);
		
		expect(ldapTemplate.search(eq(base), eq(search), isA(EmployeeContextMapper.class))).andReturn(getEmployeeList());
		expectLastCall().anyTimes();
		
		replay(ldapTemplate);
		
		// LDAP Template is now ready for use. This can be injected into a DAO and tests can be run. 
		
	}
	
	private List<Employee> getEmployeeList() {
		List<Employee> employeeList = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setUid("employee1");
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employeeList.add(employee);
		
		employee = new Employee();
		employee.setUid("employee2");
		employee.setFirstName("Jane");
		employee.setLastName("Doe");
		employeeList.add(employee);
		
		return employeeList;
	}

}
