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
package org.metabuild.grobot.webapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.metabuild.grobot.AbstractSpringEnabledTest;
import org.metabuild.grobot.common.domain.Task;
import org.metabuild.grobot.server.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;


/**
 * @author jburbridge
 * @since 10/23/2012
 */
public class TaskControllerTest extends AbstractSpringEnabledTest {

	private static final List<Task> tasks = new ArrayList<Task>();
	
	@Before
	public void initTaskControllerTest() {
		Task task = mock(Task.class);
		when(task.getId()).thenReturn("883075bc-a392-415e-8957-131b70be821d");
		when(task.getName()).thenReturn("MyMockTask");
		tasks.add(task);
	}
	
	@Test
	public void testList() {
		
		@SuppressWarnings("unchecked")
		Page<Task> page = mock(Page.class);
		when(page.getContent()).thenReturn(tasks);
		
		TaskService taskService = mock(TaskService.class);
		when(taskService.findAll(any(Pageable.class))).thenReturn(page);
		
		TaskController controller = new TaskController();
		
		ReflectionTestUtils.setField(controller, "taskService", taskService);
		
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = controller.list(uiModel, null);
		
		assertNotNull(result);
		assertEquals("tasks/list", result);
		
		@SuppressWarnings("unchecked")
		Page<Task> modelTasks = (Page<Task>) uiModel.get("tasks");
		
		assertEquals(1, modelTasks.getContent().size());
	}
}
