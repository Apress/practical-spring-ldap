package com.inflinx.book.ldap.sorting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:repositoryContext-test.xml")
public class SpringSortingTest {

	@Autowired
	private SpringSorting springSorting;
	
	@Test
	public void testSearchByLastName() {		
		springSorting.sortByLastName();
	}	
	
	@Test
	public void testSearch() {
		springSorting.sortByLocation();
	}
}