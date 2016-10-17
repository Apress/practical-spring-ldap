package com.inflinx.book.ldap.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldapunit.util.LdapUnitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:repositoryContext-test3.xml")
public class PatronServiceImplCustomTest {

	@Autowired
	private PatronService patronService;
	
	private static final int PORT = 12389;
	private static final String ROOT_DN = "dc=inflinx,dc=com";

	@Before
	public void setup() throws Exception {
		System.out.println("Inside the setup");
		LdapUnitUtils.loadData(new ClassPathResource("patrons.ldif"), PORT);
	}
	
	@After
	public void teardown() throws Exception {
		System.out.println("Inside the teardown");
		LdapUnitUtils.clearSubContexts(new DistinguishedName(ROOT_DN), PORT);
	}

	@Test
	public void testService() {
		Patron patron = new Patron();
		patron.setDn(new DistinguishedName("uid=patron10001,ou=patrons,dc=inflinx,dc=com"));
		patron.setFirstName("Patron");
		patron.setLastName("Test 1");
		patron.setFullName("Patron Test 1");
		patron.setMail("balaji@inflinx.com");
		patron.setEmployeeNumber(1234);
		patron.setPhoneNumber(new PhoneNumber(801, 864, 8050));
		
		patronService.create(patron);
		
		// Lets read the patron
		patron = patronService.find("patron10001");
		assertNotNull(patron);
		System.out.println(patron.getPhoneNumber());
		
		patron.setPhoneNumber(new PhoneNumber(435, 757, 9369)); 
		patronService.update(patron);
		System.out.println("updated phone: " + patron.getPhoneNumber());
		
		
		patron = patronService.find("patron10001");
		System.out.println("Read the phone number: " + patron.getPhoneNumber()); 
		assertEquals(patron.getPhoneNumber(), new PhoneNumber(435, 757, 9369));
		
		patronService.delete("patron10001");
		try {
			patron = patronService.find("patron10001");
			assertNull(patron);
		}
		catch(NameNotFoundException e) {
		}
	}
}