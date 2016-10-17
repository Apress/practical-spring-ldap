package com.inflinx.book.ldap.custom;

public class PhoneNumber {
	private int areaCode;
	private int exchange;
	private int extension;
	
	public PhoneNumber(int areaCode, int exchange, int extension) {
		this.areaCode = areaCode;
		this.exchange = exchange;
		this.extension = extension;
	}

	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != this.getClass()) { return false; }
		PhoneNumber p = (PhoneNumber) obj;
		return (this.areaCode ==  p.areaCode) && (this.exchange == p.exchange) && (this.extension == p.extension);
	}

	public String toString() {
		return String.format("+1 %03d %03d %04d", areaCode, exchange, extension);
	}

	// satisfies the hashCode contract
	public int hashCode() {
		int result = 17;
		result = 37 * result + areaCode;
		result = 37 * result + exchange;
		result = 37 * result + extension;
		
		return result;
	}
}
