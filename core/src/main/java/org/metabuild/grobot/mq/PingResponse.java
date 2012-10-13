package org.metabuild.grobot.mq;

import java.io.Serializable;
import java.util.Properties;

/**
 * A response to a ping request from the Grobot server, containing system details about the target host
 * 
 * @author jburbridge
 * @since 10/11/2012
 */
public class PingResponse implements Serializable {

	private static final long serialVersionUID = 4305522797844648205L;
	
	private final String hostname;
	private final Properties systemProperties;
	private final Properties otherProperties;

	/**
	 * Default constructor, sets the hostname to "localhost" and creates empty maps
	 */
	public PingResponse() {
		this("localhost", new Properties(), new Properties());
	}
	
	/**
	 * @param hostname the hostname of the system sending the ping response
	 * @param systemProperties a map populated with system properties
	 * @param otherProperties a map populated with additional properties
	 */
	public PingResponse(String hostname, Properties systemProperties, Properties otherProperties) {
		this.hostname = hostname;
		this.systemProperties = systemProperties;
		this.otherProperties = otherProperties;
	}
	/**
	 * @return the hostname of the system sending the ping response
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @return the systemProperties
	 */
	public Properties getSystemProperties() {
		return systemProperties;
	}
	/**
	 * @return the otherProperties
	 */
	public Properties getOtherProperties() {
		return otherProperties;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PingResponse [hostname=" + hostname + "]";
	}
}
