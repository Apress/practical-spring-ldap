package com.inflinx.book.ldap.repository;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldapunit.util.LdapUnitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inflinx.book.ldap.domain.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:repositoryContext-test.xml"})
public class EmployeeDaoLdapImplTest {
	
	private static final String ROOT_DN = "dc=inflinx,dc=com";
		
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao employeeDao;
	
	@Autowired
	private Integer port;
	
	@Before
	public void setup() throws Exception {
		System.out.println("Inside the setup");
		LdapUnitUtils.loadData(new ClassPathResource("employees.ldif"), port);
	}
	
	@After
	public void teardown() throws Exception {
		System.out.println("Inside the teardown");
		LdapUnitUtils.clearSubContexts(new DistinguishedName(ROOT_DN), port);
	}

	@Test
	public void testFindAll() {
		List<Employee> employeeList = employeeDao.findAll();
		Assert.assertTrue(employeeList.size() > 0);
	}

	@Test
	public void testFind() {
		Employee employee = employeeDao.find("employee1");
		Assert.assertNotNull(employee);
	}
	
	@Test
	public void testCreate() {
	Employee employee = new Employee();
		employee.setUid("employee1000");
		employee.setFirstName("Test");
		employee.setLastName("Employee1000");
		employee.setCommonName("Test Employee1000");
		employee.setEmail("employee1000@inflinx.com");
		employee.setDepartmentNumber(12356);
		employee.setEmployeeNumber("45678");
		employee.setPhone(new String[]{"801-100-1200"});
			
		employeeDao.create(employee);			
	}
	
	@Test
	public void testUpdate() {
			
		Employee employee1 = employeeDao.find("employee1");
		employee1.setFirstName("Employee New");
		employeeDao.update(employee1);
		
		employee1 = employeeDao.find("employee1");
		Assert.assertEquals(employee1.getFirstName(), "Employee New");		
	}
	
	@Test(expected=org.springframework.ldap.NameNotFoundException.class)
	public void testDelete() {
		String empUid = "employee11";
		
		employeeDao.delete(empUid);
		employeeDao.find(empUid);
	}

}

