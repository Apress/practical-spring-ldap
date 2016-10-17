package com.inflinx.book.ldap.test;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.core.support.LdapContextSource;

public class EmbeddedContextSourceFactory extends AbstractFactoryBean<ContextSource> {
	
	private static final String ADMIN_DN = "uid=admin,ou=system";
	private static final String ADMIN_PWD = "secret";
	
	private int port;
	private String rootDn;
	
	private ApacheDSConfigurer apacheDsConfigurer;
	
	@Override
	public Class<?> getObjectType() {
		return ContextSource.class;
	}
	
	@Override
	protected ContextSource createInstance() throws Exception {
		
		apacheDsConfigurer = new ApacheDSConfigurer(rootDn, port);
		apacheDsConfigurer.startServer();

		LdapContextSource targetContextSource = new LdapContextSource();
		targetContextSource.setUrl("ldap://localhost:" + port);
		targetContextSource.setUserDn(ADMIN_DN);
		targetContextSource.setPassword(ADMIN_PWD);
		targetContextSource.setDirObjectFactory(DefaultDirObjectFactory.class);
		targetContextSource.afterPropertiesSet();
		
		return targetContextSource;
	}
		
	@Override
	protected void destroyInstance(ContextSource instance) throws Exception {
		super.destroyInstance(instance);
		apacheDsConfigurer.stopServer();
	}
	
	public void setRootDn(String rootDn) {
		this.rootDn = rootDn;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
