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
import org.metabuild.grobot.server.repository.ScriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jburbridge
 * @since 6/7/2013
 */
@Service("scriptService")
@Transactional
public class ScriptServiceImpl implements ScriptService {

	@Autowired
	private ScriptRepository scriptRepository;
	
	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#findAll()
	 */
	@Override
	public List<Script> findAll() {
		return scriptRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Script> findAll(Pageable pageable) {
		return scriptRepository.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#findById(java.lang.String)
	 */
	@Override
	public Script findById(String id) {
		return scriptRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#findByPath()
	 */
	@Override
	public Script findByPath(String path) {
		return scriptRepository.findByPath(path);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#create(org.metabuild.grobot.common.domain.Script)
	 */
	@Override
	public Script create(Script script) {
		return scriptRepository.save(script);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#update(org.metabuild.grobot.common.domain.Script)
	 */
	@Override
	public Script update(Script script) throws RecordNotFoundException {
		if (null == scriptRepository.findOne(script.getId())) {
			throw new RecordNotFoundException();
		}
		return scriptRepository.saveAndFlush(script);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.ScriptService#delete(org.metabuild.grobot.common.domain.Script)
	 */
	@Override
	public void delete(Script script) {
		scriptRepository.delete(script);
	}

}
