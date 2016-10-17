package com.inflinx.book.ldap.transactions;

import com.inflinx.book.ldap.domain.Patron;

public interface PatronDao {
	
	public void create(Patron patron);
	
	public void delete(String id);
	
	public void update(Patron patron);
	
	public Patron find(String id);
}
