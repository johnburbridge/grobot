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
import org.metabuild.grobot.server.repository.BotRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jburbridge
 * @since 11/20/2012
 */
@Service("botService")
@Repository
@Transactional
public class BotServiceImpl implements BotService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BotServiceImpl.class);
	
	@Autowired
	private BotRepository botRepository;

	@Override
	@Transactional(readOnly=true)
	public List<Bot> findAll() {
		return botRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Bot> findAll(Pageable pageable) {
		return botRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Bot> findAllWithProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public Bot findById(String id) {
		return botRepository.findOne(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Bot findByName(String name) {
		final List<Bot> results =  botRepository.findByName(name);
		return results.size() > 0 ? results.get(0) : null;
	}

	@Override
	@Transactional(readOnly=false)
	public Bot create(Bot bot) {
		LOGGER.info("Saving new bot {}", bot);
		return botRepository.save(bot);
	}

	@Override
	@Transactional(rollbackFor = RecordNotFoundException.class, readOnly=false)
	public Bot update(Bot updatedBot) throws RecordNotFoundException {
		LOGGER.info("Saving existing bot with id {}", updatedBot.getId());
		if (null == botRepository.findOne(updatedBot.getId())) {
			throw new RecordNotFoundException();
		}
		return botRepository.save(updatedBot);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Bot bot) {
		LOGGER.info("Deleting bot with id {}", bot.getId());
		botRepository.delete(bot);
	}
}
