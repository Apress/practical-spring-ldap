package com.inflinx.book.ldap.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Repository;

import com.inflinx.book.ldap.domain.Patron;


@Repository("patronDao")
public class PatronDaoImpl implements PatronDao {

	private static final String PATRON_BASE  = "ou=patrons,dc=inflinx,dc=com";
	
	@Autowired
	@Qualifier("ldapTemplate")
	private LdapTemplate ldapTemplate;

	@Override
	public void create(Patron patron) {
		System.out.println("Inside the create method ...");
		DistinguishedName dn = new DistinguishedName(PATRON_BASE);
		dn.add("uid", patron.getUserId());

		DirContextAdapter context = new DirContextAdapter(dn);
		context.setAttributeValues("objectClass", new String[] {"top", "uidObject", "person", "organizationalPerson", "inetOrgPerson"});
		context.setAttributeValue("sn", patron.getLastName());
		context.setAttributeValue("cn", patron.getCn());
		
		ldapTemplate.bind(context);
	}
	
	@Override
	public void delete(String id) {
		DistinguishedName dn = new DistinguishedName(PATRON_BASE);
		dn.add("uid", id);
		ldapTemplate.unbind(dn);
	}

	@Override
	public void update(Patron patron) {
		DistinguishedName dn = new DistinguishedName(PATRON_BASE);
		dn.add("uid", patron.getUserId());

		DirContextOperations context = ldapTemplate.lookupContext(dn);
		context.setAttributeValues("objectClass", new String[] {"top", "uidObject", "person", "organizationalPerson", "inetOrgPerson"});
		context.setAttributeValue("sn", patron.getLastName());
		context.setAttributeValue("cn", patron.getCn());
		
		ldapTemplate.modifyAttributes(context);

	}
	
	@Override
	public Patron find(String id) {
		EqualsFilter filter = new EqualsFilter("uid", id);
		return (Patron)ldapTemplate.searchForObject(PATRON_BASE, filter.encode(), new PatronContextMapper());
	}
	
}
