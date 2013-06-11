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
import org.metabuild.grobot.common.domain.BotGroup;
import org.metabuild.grobot.server.repository.BotGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author jburbridge
 * @since 12/16/2012
 */
@Service("botGroupService")
@Repository
@Transactional
public class BotGroupServiceImpl implements BotGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BotGroupServiceImpl.class);
	
	@Autowired
	private BotGroupRepository botGroupRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<BotGroup> findAll() {
		return botGroupRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<BotGroup> findAll(Pageable pageable) {
		return botGroupRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public BotGroup find(String id) {
		return botGroupRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public BotGroup findById(String id) {
		return find(id);
	}

	@Override
	@Transactional(readOnly=true)
	public BotGroup findByName(String name) {
		final List<BotGroup> results = botGroupRepository.findByName(name);
		return results.size() > 0 ? results.get(0) : null;
	}

	@Override
	@Transactional(readOnly=false)
	public BotGroup create(BotGroup botGroup) {
		LOGGER.info("Creating new BotGroup with {}", botGroup); 
		return botGroupRepository.save(botGroup);
	}

	@Override
	@Transactional(readOnly=false)
	public BotGroup update(BotGroup botGroup) throws RecordNotFoundException {
		LOGGER.info("Updating BotGroup with {}", botGroup); 
		if (null == botGroupRepository.findOne(botGroup.getId())) {
			throw new RecordNotFoundException();
		}
		return botGroupRepository.save(botGroup);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(BotGroup botGroup) {
		LOGGER.info("Deleting BotGroup with id {}", botGroup.getId());
		botGroupRepository.delete(botGroup);
	}
}
