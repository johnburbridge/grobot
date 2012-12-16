package org.metabuild.grobot.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.metabuild.grobot.common.jms.StatusResponse;

/**
 * @author jburbridge
 * @since 9/27/2012
 */
@Entity
@Table(name="TARGET_HOSTS")
public class TargetHost implements Serializable {
	
	private static final long serialVersionUID = 150135564407144746L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTERED")
	private Date registered;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TARGET_GROUP_MEMBERS",
		joinColumns = @JoinColumn(name = "TARGET_HOST_ID"),
		inverseJoinColumns = @JoinColumn(name = "TARGET_GROUP_ID"))
	private Set<TargetGroup> groups = new HashSet<TargetGroup>();
	
	@Transient
	private boolean active;
	@Transient
	private Properties systemProperties;
	@Transient
	private Properties customProperties;
	@Transient
	private DateTime lastUpdatedStatus;
	@Transient
	private TargetHostStatus status;

	/**
	 * No-arg constructor for Hibernate
	 */
	public TargetHost() {}
	
	/**
	 * Default constructor - initializes the object with empty properties
	 * 
	 * @param name
	 * @param address - the fully qualified host name
	 * @param active - is the host available for targeting
	 */
	public TargetHost(String name, String address, boolean active) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.address = address;
		this.active = active;
		this.systemProperties = new Properties();
		this.customProperties = new Properties();
		this.status = TargetHostStatus.INITIALIZING;
	}
	
	/**
	 * Constructor for creating TargetHost from PingResponses
	 * 
	 * @param statusResponse
	 */
	public TargetHost(StatusResponse statusResponse) {
		this.name = statusResponse.getHostname();
		this.address = statusResponse.getHostname();
		this.systemProperties = statusResponse.getSystemProperties();
		this.customProperties = statusResponse.getOtherProperties();
		this.lastUpdatedStatus = new DateTime(statusResponse.getTimeStamp());
		this.status = TargetHostStatus.IDLE;
		this.active = true;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the systemProperties
	 */
	public Properties getSystemProperties() {
		return systemProperties;
	}

	/**
	 * @param systemProperties the systemProperties to set
	 */
	public void setSystemProperties(Properties systemProperties) {
		this.systemProperties = systemProperties;
	}

	/**
	 * @return the otherProperties
	 */
	public Properties getCustomProperties() {
		return customProperties;
	}

	/**
	 * @param customProperties the customProperties to set
	 */
	public void setCustomProperties(Properties customProperties) {
		this.customProperties = customProperties;
	}

	/**
	 * @return the registered date
	 */
	public Date getRegistered() {
		return (Date) registered.clone();
	}

	/**
	 * @param registered the date to set
	 */
	public void setRegistered(Date registered) {
		this.registered = (Date) registered.clone();
	}

	/**
	 * @return the lastUpdatedStatus
	 */
	public DateTime getLastUpdatedStatus() {
		return lastUpdatedStatus;
	}

	/**
	 * @param lastUpdatedStatus the lastKnownResponse to set
	 */
	public void setLastUpdatedStatus(DateTime lastUpdatedStatus) {
		this.lastUpdatedStatus = lastUpdatedStatus;
	}

	/**
	 * @return the status
	 */
	public TargetHostStatus getStatus() {
		return status;
	}

	/**
	 * @return the groups
	 */
	public Set<TargetGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<TargetGroup> groups) {
		this.groups = groups;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TargetHostStatus status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TargetHost [id=").append(id).append(", name=")
				.append(name).append(", address=").append(address)
				.append(", registered=").append(registered).append(", active=")
				.append(active).append(", status=").append(status).append("]");
		return builder.toString();
	}
}
