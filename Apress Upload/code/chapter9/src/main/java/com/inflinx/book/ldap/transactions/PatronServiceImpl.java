package com.inflinx.book.ldap.transactions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inflinx.book.ldap.domain.Patron;


@Service("patronService")
@Transactional
public class PatronServiceImpl implements PatronService {
	
	@Autowired
	@Qualifier("patronDao")
	private PatronDao patronDao;
	
	@Override
	public void create(Patron patron) {
		System.out.println("Begining the transaction");
		patronDao.create(patron);
		System.out.println("Ending the patron creation");
		throw new RuntimeException(); // Will roll back the transaction
	}
	
	public void create(List<Patron> patronList) {
		System.out.println("Starting the transaction .....");
		
		for(Patron patron : patronList) {
			patronDao.create(patron);
		}
		
		System.out.println("Ending the transaction");
		
		throw new RuntimeException();
	}
	
	public void delete(String id) {
		patronDao.delete(id);
		throw new RuntimeException(); // Need this to simulate a rollback 
	}
	
	public void update(Patron patron) {
		patronDao.update(patron);
	}
	
	public Patron find(String id) {
		
		return patronDao.find(id);
	}
}