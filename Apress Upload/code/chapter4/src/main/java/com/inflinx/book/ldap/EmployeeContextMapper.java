package com.inflinx.book.ldap;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

import com.inflinx.book.ldap.domain.Employee;

public class EmployeeContextMapper extends AbstractContextMapper {

	@Override
	public Object doMapFromContext(DirContextOperations context) {
		Employee employee = new Employee();
		employee.setUid(context.getStringAttribute("uid"));
		employee.setFirstName(context.getStringAttribute("givenName"));
		employee.setLastName(context.getStringAttribute("surname"));
		employee.setPhone(context.getStringAttributes("telephoneNumber"));
		
		return employee;
	}
	
}
