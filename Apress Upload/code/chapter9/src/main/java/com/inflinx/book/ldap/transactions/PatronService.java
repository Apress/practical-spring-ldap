package com.inflinx.book.ldap.transactions;

import java.util.List;

import com.inflinx.book.ldap.domain.Patron;

public interface PatronService {

	public void create(Patron patron);
	
	public void create(List<Patron> patronList);
	
	public void delete(String id);
	
	public void update(Patron patron);
	
	public Patron find(String id);
}
