package com.inflinx.book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldapunit.util.LdapUnitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.GreaterThanOrEqualsFilter;
import org.springframework.ldap.filter.LessThanOrEqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.NotFilter;
import org.springframework.ldap.filter.NotPresentFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.PresentFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.inflinx.book.custom.filter.ApproximateFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:repositoryContext-test.xml")
public class SearchFilterDemoTest {

	private static final String ROOT_DN = "dc=inflinx,dc=com";

	@Autowired
	private SearchFilterDemo searchFilterDemo;
	
	@Autowired
	private Integer port;
	
	@Before
	public void setup() throws Exception {
		System.out.println("Inside the setup");
		LdapUnitUtils.loadData(new ClassPathResource("patrons.ldif"), port);
	}
	
	@After
	public void teardown() throws Exception {
		System.out.println("Inside the teardown");
		LdapUnitUtils.clearSubContexts(new DistinguishedName(ROOT_DN), port);
	}
	
	@Test
	public void testEqualsFilter() {
		Filter filter = new EqualsFilter("givenName", "Jacob");
		searchFilterDemo.searchAndPrintResults(filter);
	}

	@Test
	public void testLikeFilter() {
		Filter filter = new LikeFilter("givenName", "Ja*");
		searchFilterDemo.searchAndPrintResults(filter);
	}
	
	@Test
	public void testPresentFilter() {
		Filter filter = new PresentFilter("mail");
		searchFilterDemo.searchAndPrintResults(filter);
	}

	@Test
	public void testNotPresentFilter() {
		Filter filter = new NotPresentFilter("mail");
		searchFilterDemo.searchAndPrintResults(filter);
	}

	@Test
	public void testNotFilter() {
		NotFilter notFilter = new NotFilter(new LikeFilter("givenName", "Ja*"));
		searchFilterDemo.searchAndPrintResults(notFilter);
	}

	@Test
	public void testGreaterThanOrEqualsFilter() {
		Filter filter = new GreaterThanOrEqualsFilter("givenName", "Jacob");
		searchFilterDemo.searchAndPrintResults(filter);
	}
	
	@Test
	public void testLessThanOrEqualsFilter() {
		Filter filter = new LessThanOrEqualsFilter("givenName", "Jacob");
		searchFilterDemo.searchAndPrintResults(filter);
	}

	@Test
	public void testAndFilter() {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("postalCode", "84047"));
		andFilter.and(new PresentFilter("mail"));
		searchFilterDemo.searchAndPrintResults(andFilter);
	}

	@Test
	public void testOrFilter() {
		OrFilter orFilter = new OrFilter();
		orFilter.or(new EqualsFilter("postalCode", "84047"));
		orFilter.or(new EqualsFilter("postalCode", "84121"));
		searchFilterDemo.searchAndPrintResults(orFilter);
	}
	
	@Test
	public void testApproximateFilter() {
		ApproximateFilter approx = new ApproximateFilter("givenName", "Adeli");		
		searchFilterDemo.searchAndPrintResults(approx);
	}
}