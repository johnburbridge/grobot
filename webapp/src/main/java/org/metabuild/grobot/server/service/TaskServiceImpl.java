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

import org.metabuild.grobot.common.domain.Task;
import org.metabuild.grobot.server.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jburbridge
 * @since 12/16/2012
 */
@Service("taskService")
@Repository
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#findAll()
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Task> findAll(Pageable pageable) {
		return taskRepository.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#findByName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Task> findByName(String name) {
		return taskRepository.findByName(name);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#find(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public Task find(String id) {
		return taskRepository.findById(id);
	}

	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#save(org.metabuild.grobot.common.domain.Task)
	 */
	@Override
	@Transactional(readOnly=false)
	public Task create(Task task) {
		return taskRepository.save(task);
	}

	/*
	 * (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#update(org.metabuild.grobot.common.domain.Task)
	 */
	@Override
	public Task update(Task task) throws RecordNotFoundException {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.metabuild.grobot.server.service.TaskService#delete(org.metabuild.grobot.common.domain.Task)
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Task task) {
		taskRepository.delete(task);
	}
}
