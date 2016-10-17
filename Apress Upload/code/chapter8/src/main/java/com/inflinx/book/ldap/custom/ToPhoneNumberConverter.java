package com.inflinx.book.ldap.custom;

import org.springframework.ldap.odm.typeconversion.impl.Converter;

public class ToPhoneNumberConverter implements Converter {

	@Override
	public <T> T convert(Object source, Class<T> toClass) throws Exception {
		T result = null;
			
		if (String.class.isAssignableFrom(source.getClass()) && toClass == PhoneNumber.class) {
			// Simple implementation 
			String[] tokens = ((String)source).split(" ");
			int i = 0;
			if(tokens.length == 4) {
				i = 1;
			}
			result = toClass.cast(new PhoneNumber(Integer.parseInt(tokens[i]), Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2])));
        } 
		
		return result;
	}
}