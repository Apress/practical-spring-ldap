package com.inflinx.book.ldap.custom;


public interface PatronService {

	public void create(Patron patron);
	
	public void delete(String id);
	
	public void update(Patron patron);
	
	public Patron find(String id);
}
