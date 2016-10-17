package com.inflinx.book.ldap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.odm.core.OdmManager;
import org.springframework.stereotype.Service;

import com.inflinx.book.ldap.domain.Patron;


@Service("patronService")
public class PatronServiceImpl implements PatronService {
	
	private static final String PATRON_BASE  = "ou=patrons,dc=inflinx,dc=com";
	
	@Autowired
	@Qualifier("odmManager")
	private OdmManager odmManager;

	@Override
	public void create(Patron patron) {
		odmManager.create(patron);
	}

	@Override
	public void update(Patron patron) {
		odmManager.update(patron);
	}

	@Override
	public Patron find(String id) {
		DistinguishedName dn = new DistinguishedName(PATRON_BASE);
		dn.add("uid", id);
		return odmManager.read(Patron.class, dn);
	}
	
	@Override
	public void delete(String id) {
		odmManager.delete(find(id));
	}
}