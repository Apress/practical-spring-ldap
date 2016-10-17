package com.inflinx.book.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.spi.DirObjectFactory;

import com.inflinx.book.ldap.domain.Patron;

public class PatronObjectFactory implements DirObjectFactory {

	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx,
			Hashtable<?, ?> environment, Attributes attrs) throws Exception {
		
		Patron patron = new Patron();
		patron.setUid(attrs.get("uid").toString());
		patron.setFullName(attrs.get("cn").toString());
		
		return patron;
	}
	
	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx,
			Hashtable<?, ?> environment) throws Exception {
		
		return getObjectInstance(obj, name, nameCtx, environment, new BasicAttributes());
	}
}
