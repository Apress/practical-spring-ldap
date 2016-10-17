package com.inflinx.book.ldap.paging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:repositoryContext-test.xml")
public class SpringPagingTest {

	@Autowired
	private SpringPaging springPaging;
	
	@Test
	public void testPagedResults() {
		springPaging.pagedResults();
	}
}