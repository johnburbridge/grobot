/*
 * Copyright 2013 Metabuild Software, LLC. (http://www.metabuild.org)
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

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The domain object representation of a script file
 * 
 * @author jburbridge
 * @since 5/31/2013
 */
@Entity
@Table(name="SCRIPTS")
public class Script implements Serializable {

	private static final long serialVersionUID = -7297547215650120187L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String id;

	@Column(name = "PATH")
	private String path;
	
	@Column(name = "BODY")
	private String body;

	/**
	 * No-arg constructor for hibernate
	 */
	public Script() {}
	
	/**
	 * @param path
	 * @param body
	 */
	public Script(String path, String body) {
		this.path = path;
		this.body = body;
	}

	/**
	 * @param file
	 */
	public Script(File file) {
		this.path = file.getAbsolutePath();
	}

	/**
	 * @return the record id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the location
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.path = location;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
