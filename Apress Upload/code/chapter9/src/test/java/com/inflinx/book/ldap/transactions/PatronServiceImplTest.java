package com.inflinx.book.ldap.transactions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inflinx.book.ldap.domain.Patron;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:repositoryContext-test.xml")
public class PatronServiceImplTest {

	@Autowired
	private PatronService patronService;
	
	// Uncomment @Test and Comment 	@Test(expected=RuntimeException.class) when PatronServiceImpl's delete is not simulating rollback
//	@Test
//	@Test(expected=RuntimeException.class)
	public void testDeletePatron() {
		patronService.delete("patron10001");
	}

	@Test(expected=RuntimeException.class)
	public void testCreate() {
		Patron patron = new Patron();
		
		patron.setUserId("patron10001");
		patron.setLastName("Patron10001");
		patron.setCn("Test Patron10001");
		
		patronService.create(patron);
	}	
}