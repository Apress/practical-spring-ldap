package com.inflinx.book.ldap;

import java.util.Arrays;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.ContextNotEmptyException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.inflinx.book.ldap.domain.Employee;
import com.sun.jndi.ldap.LdapName;

public class JndiLdapImpl {

	private static final String BASE_PATH = "ou=employees,dc=inflinx,dc=com";
	
	private DirContext getContext() throws NamingException {
		Properties environment = new Properties();
		environment.setProperty(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		environment.setProperty(DirContext.PROVIDER_URL, "ldap://localhost:11389");
		environment.setProperty(DirContext.SECURITY_PRINCIPAL, "cn=Directory Manager");
		environment.setProperty(DirContext.SECURITY_CREDENTIALS, "opendj");

		DirContext context = new InitialDirContext(environment);
		
		return context;
	}
	
	private void closeContext(DirContext context) {
		try {
			if(null != context) {
				context.close();
			}
		}
		catch(NamingException e) {
			// Ignore the exception
		}
	}
	
	public void search() {
		DirContext context = null;
		NamingEnumeration<SearchResult> searchResults = null;
		try{
			context = getContext();
			// Setup Search meta data
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setReturningAttributes(new String[]{"givenName", "telephoneNumber"});
			searchResults = context.search("dc=inflinx,dc=com",
					"(objectClass=inetOrgPerson)", searchControls);
			
			while (searchResults.hasMore()) {
				SearchResult result = searchResults.next();
				Attributes attributes = result.getAttributes();
				String firstName = (String)attributes.get("givenName").get(); 
				
				// Read the multi-valued attribute
				Attribute phoneAttribute = attributes.get("telephoneNumber");
				String[] phone = new String[phoneAttribute.size()];
				NamingEnumeration phoneValues = phoneAttribute.getAll();
				
				for(int i = 0; phoneValues.hasMore(); i++) {
					phone[i] = (String)phoneValues.next();
				}
				System.out.println(firstName +  "> " + Arrays.toString(phone)); 
			}
		}
		catch(NamingException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (null != searchResults) {
					searchResults.close();
				}
				closeContext(context);
			} catch (NamingException e) {
				// Ignore this
			}
		}
	}
	
	
	
	public void addEmploye(Employee employee) {
		
		DirContext context = null;
		try {
			context = getContext();
			
			// Populate the attributes
			Attributes attributes = new BasicAttributes();
			attributes.put(new BasicAttribute("objectClass", "inetOrgPerson"));
			attributes.put(new BasicAttribute("uid", employee.getUid()));
			attributes.put(new BasicAttribute("givenName", employee.getFirstName()));
			attributes.put(new BasicAttribute("surname", employee.getLastName()));
			attributes.put(new BasicAttribute("commonName", employee.getCommonName()));
			attributes.put(new BasicAttribute("departmentNumber", employee.getDepartmentNumber()));
			attributes.put(new BasicAttribute("mail", employee.getEmail()));
			attributes.put(new BasicAttribute("employeeNumber", employee.getEmployeeNumber()));
			
			Attribute phoneAttribute = new BasicAttribute("telephoneNumber");
			for(String phone : employee.getPhone()) {
				phoneAttribute.add(phone);
			}
			attributes.put(phoneAttribute);
			
			// Get the fully qualified DN
			String dn = "uid=" + employee.getUid() + "," + BASE_PATH;
			
			// Add the entry
			context.createSubcontext("dn", attributes);
		}
		catch(NamingException e) {
			// Handle the exception properly
			e.printStackTrace();
		}
		finally {
			closeContext(context);
		}
	}
	
	public void update(String dn, ModificationItem[] items) {
		DirContext context = null;
		try {
			context = getContext();
			context.modifyAttributes(dn, items);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		finally {
			closeContext(context);
		}
	}
	
	
	public void remove(String dn) {
		DirContext context = null;
		try {
			context = getContext();
			context.destroySubcontext(dn);
		}
		catch(NamingException e) {
			e.printStackTrace();
		}
		finally {
			closeContext(context);
		}
	}
	
	
	public void removeSubTree(DirContext ctx, String root) throws NamingException {
		NamingEnumeration enumeration = null;
		try {
			enumeration = ctx.listBindings(root);
			while (enumeration.hasMore()) {
				Binding childEntry = (Binding) enumeration.next();
				LdapName childName = new LdapName(root);
				childName.add(childEntry.getName());
				
				try {
					ctx.destroySubcontext(childName);
				}
				catch (ContextNotEmptyException e) {
					removeSubTree(ctx, childName.toString());
					ctx.destroySubcontext(childName);
				}
			}
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		finally {
			try {
				enumeration.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		JndiLdapImpl jli = new JndiLdapImpl();
		jli.search();
	}
}