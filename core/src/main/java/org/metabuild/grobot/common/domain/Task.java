/*
 * Copyright 2012 Metabuild Software, LLC. (http://www.metabuild.org)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.metabuild.grobot.common.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author jburbridge
 * @since 12/14/2012
 */
@Entity
@Table(name="TASKS")
public class Task  implements Serializable {

	private static final long serialVersionUID = -6750099046329133365L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL, optional=false)
	@JoinColumn(name = "SCRIPT_ID")
	private Script script;
	
	@Column(name = "SCRIPT_NAME")
	private String scriptName;
	
	@Column(name = "ARGUMENTS")
	private String arguments;
	
	@OneToMany(mappedBy="id", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<TaskExecution> taskExecutions;

	/**
	 * No-arg constructor for Hibernate
	 */
	public Task() {}
	
	/**
	 * @param name
	 * @param script
	 * @param taskExecutions
	 */
	public Task(String name, Script script, List<TaskExecution> taskExecutions) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.script = script;
		this.taskExecutions = taskExecutions;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
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
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(Script script) {
		this.script = script;
	}

	/**
	 * @return the scriptName
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * @param scriptName the scriptName to set
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * @return the arguments
	 */
	public String getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	/**
	 * @return the taskExecutions
	 */
	public List<TaskExecution> getTaskExecutions() {
		return taskExecutions;
	}

	/**
	 * @param taskExecutions the taskExecutions to set
	 */
	public void setTaskExecutions(List<TaskExecution> taskExecutions) {
		this.taskExecutions = taskExecutions;
	}
}
