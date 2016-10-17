package com.inflinx.book.ldap.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.stereotype.Repository;

import com.inflinx.book.ldap.domain.Employee;
import com.inflinx.book.ldap.repository.mapper.EmployeeContextMapper;


@Repository("employeeDao")
public class EmployeeDaoLdapImpl implements EmployeeDao {

	@Autowired
	@Qualifier("ldapTemplate")
	private SimpleLdapTemplate ldapTemplate;
	
	@Override
	public List<Employee> findAll() {
		return ldapTemplate.search("", "(objectClass=inetOrgPerson)", new EmployeeContextMapper());
	}
	
	@Override
	public Employee find(String id) { 
		DistinguishedName dn = new DistinguishedName();
		dn.add("uid", id);
		return ldapTemplate.lookup(dn, new EmployeeContextMapper()); 
	}
	
	@Override
	public void create(Employee employee) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("uid", employee.getUid());
		
		DirContextAdapter context = new DirContextAdapter();
		context.setDn(dn);
		context.setAttributeValues("objectClass", new String[] {"top", "person", "organizationalPerson", "inetOrgPerson"});
		context.setAttributeValue("givenName", employee.getFirstName());
		context.setAttributeValue("surname", employee.getLastName());
		context.setAttributeValue("commonName", employee.getCommonName());
		context.setAttributeValue("mail", employee.getEmail());
		context.setAttributeValue("departmentNumber", Integer.toString(employee.getDepartmentNumber()));
		context.setAttributeValue("employeeNumber", employee.getEmployeeNumber());
		context.setAttributeValues("telephoneNumber", employee.getPhone());
	
		ldapTemplate.bind(context);
	}

	@Override
	public void update(Employee employee) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("uid", employee.getUid());
		
		DirContextOperations context = ldapTemplate.lookupContext(dn);
		
		context.setAttributeValues("objectClass", new String[] {"top", "person", "organizationalPerson", "inetOrgPerson"});
		context.setAttributeValue("givenName", employee.getFirstName());
		context.setAttributeValue("surname", employee.getLastName());
		context.setAttributeValue("commonName", employee.getCommonName());
		context.setAttributeValue("mail", employee.getEmail());
		context.setAttributeValue("departmentNumber", Integer.toString(employee.getDepartmentNumber()));
		context.setAttributeValue("employeeNumber", employee.getEmployeeNumber());
		context.setAttributeValues("telephoneNumber", employee.getPhone());
	
		ldapTemplate.modifyAttributes(context);
	}
	
	@Override
	public void delete(String id) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("uid", id);
		ldapTemplate.unbind(dn);
	}

}
