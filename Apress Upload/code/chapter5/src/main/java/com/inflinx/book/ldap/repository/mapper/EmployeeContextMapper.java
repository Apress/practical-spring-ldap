package com.inflinx.book.ldap.repository.mapper;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.simple.AbstractParameterizedContextMapper;

import com.inflinx.book.ldap.domain.Employee;

public class EmployeeContextMapper extends AbstractParameterizedContextMapper<Employee>  {
	@Override
	protected Employee doMapFromContext(DirContextOperations context) {
		Employee employee = new Employee();
		
		employee.setUid(context.getStringAttribute("UID"));
		employee.setFirstName(context.getStringAttribute("givenName"));
		employee.setLastName(context.getStringAttribute("surname"));
		employee.setCommonName(context.getStringAttribute("commonName"));
		employee.setEmployeeNumber(context.getStringAttribute("employeeNumber"));
		employee.setEmail(context.getStringAttribute("mail"));
		if(context.getStringAttribute("departmentNumber") != null) {
			employee.setDepartmentNumber(Integer.parseInt(context.getStringAttribute("departmentNumber")));
		}
		employee.setPhone(context.getStringAttributes("telephoneNumber"));
				
		return employee;
	}
}
