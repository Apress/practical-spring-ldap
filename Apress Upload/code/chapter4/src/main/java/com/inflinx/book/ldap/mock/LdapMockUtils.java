package com.inflinx.book.ldap.mock;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.ldap.core.DirContextOperations;

import static org.easymock.EasyMock.*;

public class LdapMockUtils {

	public static DirContextOperations mockContextOperations(Map<String, Object> attributes)
	{
		DirContextOperations contextOperations = createMock(DirContextOperations.class);
			
		for(Entry<String, Object> entry : attributes.entrySet())
		{
			//contextOperations.gets
			if(entry.getValue() instanceof String)
			{
				expect(contextOperations.getStringAttribute(eq(entry.getKey())))
				.andReturn((String)entry.getValue());
				expectLastCall().anyTimes();
				
			}
			else if(entry.getValue() instanceof String[])
			{
				expect(contextOperations.getStringAttributes(eq(entry.getKey())))
				.andReturn((String[])entry.getValue());
				expectLastCall().anyTimes();
			}
		}
		return contextOperations;
	}
	
}
