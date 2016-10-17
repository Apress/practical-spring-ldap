package com.inflinx.book.custom.filter;

import org.springframework.ldap.filter.AbstractFilter;

public class ApproximateFilter extends AbstractFilter
{
	private static final String APPROXIMATE_SIGN = "~=";

	private String attribute;
	private String value;
	
	public ApproximateFilter(String attribute, String value)
	{
		this.attribute = attribute;
		this.value = value;
	}
	
	@Override
	public StringBuffer encode(StringBuffer buff) {
		buff.append('(');
		buff.append(attribute).append(APPROXIMATE_SIGN).append(value);
		buff.append(')');
		return buff;
	}		
}