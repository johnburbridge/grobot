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
package org.metabuild.grobot.server.service;

import java.util.List;

import org.metabuild.grobot.common.domain.Bot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author jburbridge
 * @since 11/16/2012
 */
public interface BotService {

	public List<Bot> findAll();
	
	public Page<Bot> findAll(Pageable pageable);
	
	public List<Bot> findAllWithProperties();
	
	public Bot findByName(String name);

	public Bot findById(String id);
	
	public Bot create(Bot bot);
	
	public Bot update(Bot bot) throws RecordNotFoundException;
	
	public void delete(Bot bot);


}
