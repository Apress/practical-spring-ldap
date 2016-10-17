package com.inflinx.book.ldap.service;

import com.inflinx.book.ldap.domain.Patron;

public interface PatronService {

	public void create(Patron patron);
	
	public void delete(String id);
	
	public void update(Patron patron);
	
	public Patron find(String id);
}
