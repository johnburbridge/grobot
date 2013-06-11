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
package org.metabuild.grobot.server.service;

import java.util.List;

import org.metabuild.grobot.common.domain.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author jburbridge
 * @since 6/7/2013
 */
public interface ScriptService {

	public List<Script> findAll();
	
	public Page<Script> findAll(Pageable pageable);
	
	public Script findById(String id);
	
	public Script findByPath(String path);
	
	public Script create(Script script);
	
	public Script update(Script script) throws RecordNotFoundException;
	
	public void delete(Script script);
}
