package com.inflinx.book.ldap.transactions;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.simple.AbstractParameterizedContextMapper;

import com.inflinx.book.ldap.domain.Patron;

public class PatronContextMapper extends AbstractParameterizedContextMapper<Patron> {

	@Override
	protected Patron doMapFromContext(DirContextOperations context) {
		Patron patron = new Patron();
		patron.setLastName(context.getStringAttribute("sn"));
		patron.setUserId(context.getStringAttribute("uid"));
		patron.setCn(context.getStringAttribute("cn"));
		patron.setEmail(context.getStringAttribute("mail"));
		
		return patron;
	}

}
