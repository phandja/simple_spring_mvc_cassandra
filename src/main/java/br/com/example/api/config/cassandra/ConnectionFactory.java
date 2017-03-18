package br.com.example.api.config.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantSpeculativeExecutionPolicy;

public class ConnectionFactory {
	
	private static Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);
	
	 private static Cluster cluster;

	 public static Session openSession() {
		 try{
			 
			if(cluster == null || cluster.isClosed()){
				 cluster = Cluster.builder()
			    		    .addContactPoint("127.0.0.1")
			    		    .withSpeculativeExecutionPolicy(
			    		        new ConstantSpeculativeExecutionPolicy(
			    		            500, // delay before a new execution is launched
			    		            2    // maximum number of executions
			    		        ))
			    		    .build();
			     
			}
		  LOG.info("Connection opened!\n");	
		 
		 return cluster.connect("cassandra_db");
			 
		 }catch (Exception e) {
			 LOG.error(e.getLocalizedMessage(), e);
			 return null;
		}
	 }
	 
	 public void close() {
		 cluster.close();
	 }
}
