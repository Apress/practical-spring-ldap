package com.inflinx.book.ldap.test;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.directory.server.core.DirectoryService;
import org.apache.directory.server.core.factory.DefaultDirectoryServiceFactory;
import org.apache.directory.server.core.factory.DirectoryServiceFactory;
import org.apache.directory.server.core.factory.PartitionFactory;
import org.apache.directory.server.core.partition.Partition;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.store.LdifFileLoader;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;

public class ApacheDSConfigurer {
	
	private DirectoryService directoryService;
	private LdapServer ldapServer;
	
	private String rootDn;
	private int port;
	
	public ApacheDSConfigurer(String rootDn, int port) {
		this.rootDn = rootDn;
		this.port = port;
	}
	
	public void startServer() {
		
		try {
				System.out.println("Starting Embedded LDAP Server ....");
				
				DirectoryServiceFactory dsf = DefaultDirectoryServiceFactory.DEFAULT;
				directoryService = dsf.getDirectoryService();
				directoryService.getChangeLog().setEnabled( true );
				dsf.init( "default" + UUID.randomUUID().toString() );
				
				PartitionFactory partitionFactory = DefaultDirectoryServiceFactory.DEFAULT.getPartitionFactory();
				// Create Partition takes id, suffix, cache size, working directory
				Partition partition = partitionFactory.createPartition(rootDn, rootDn, 1000, 
										new File( directoryService.getWorkingDirectory(), rootDn ));
				partition.setSchemaManager( directoryService.getSchemaManager() );
				
				// Inject the partition into the DirectoryService
				directoryService.addPartition( partition );
	
				// Create the LDAP server
				ldapServer = new LdapServer();
				ldapServer.setServiceName("Embedded LDAP service");
				TcpTransport ldapTransport = new TcpTransport(port);
				ldapServer.setTransports(ldapTransport);
				ldapServer.setDirectoryService( directoryService );
	
				directoryService.startup();
				ldapServer.start();
				
				System.out.println("Embedded LDAP server has started successfully ....");
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
	}
	
	public void stopServer() {
		try {
			System.out.println("Shutting down LDAP Server ....");
			
			ldapServer.stop();
	        directoryService.shutdown();
	        FileUtils.deleteDirectory( directoryService.getWorkingDirectory() );
	        System.out.println("LDAP Server shutdown successful ....");
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void loadData(String ldifFile) {
		try {
			LdifFileLoader loader = new LdifFileLoader(directoryService.getAdminSession(), ldifFile);
	        loader.execute();
		}
		catch(Exception e) {
			throw new RuntimeException(e); 
		}
	}
}
