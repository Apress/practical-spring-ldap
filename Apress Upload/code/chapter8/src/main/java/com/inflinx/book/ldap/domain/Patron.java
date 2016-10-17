package com.inflinx.book.ldap.domain;

import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(objectClasses = { "inetorgperson", "organizationalperson", "person", "top" })
public class Patron {
	
    @Id
    private Name dn;
	
    @Attribute(name = "sn")
    private String lastName;
    
    @Attribute(name = "givenName")
    private String firstName;
    
    private String telephoneNumber;
    
    @Attribute(name = "cn")
    private String fullName;
    
    private String mail;
	
    @Attribute(name = "objectClass")
    private List<String> objectClasses;
    
    @Attribute(name = "employeeNumber", syntax = "2.16.840.1.113730.3.1.3")
    private int employeeNumber;

	public Name getDn() { return dn; }

	public void setDn(Name dn) { this.dn = dn; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getTelephoneNumber() { return telephoneNumber; }

	public void setTelephoneNumber(String telephoneNumber) { this.telephoneNumber = telephoneNumber; }

	public String getFullName() { return fullName; }

	public void setFullName(String fullName) { this.fullName = fullName; }

	public String getMail() { return mail; }

	public void setMail(String mail) { this.mail = mail; }

	public List<String> getObjectClasses() { return objectClasses; }

	public void setObjectClasses(List<String> objectClasses) { this.objectClasses = objectClasses; }
	
	public int getEmployeeNumber() { return employeeNumber; }

	public void setEmployeeNumber(int employeeNumber) { this.employeeNumber = employeeNumber; }

	@Override
	public String toString() {
		return "Dn: " + dn + ", firstName: " + firstName + ", fullName: " + fullName 
					+ ", Telephone Number: " + telephoneNumber;
	}
}
