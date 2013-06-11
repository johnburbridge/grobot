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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.metabuild.grobot.AbstractSpringEnabledTest;
import org.metabuild.grobot.common.domain.BotGroup;
import org.metabuild.grobot.common.domain.Bot;
import org.metabuild.grobot.server.service.BotGroupService;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

/**
 * 
 * @author jburbridge
 * @since 03/11/2013
 */
public class GroupsControllerTest extends AbstractSpringEnabledTest {
	
	private final List<BotGroup> groups = new ArrayList<BotGroup>();
	
	@Before
	public void initStatus() {
		groups.add(new BotGroup("groupname1", new HashSet<Bot>()));
	}
	
	@Test
	public void testList() {
		
		@SuppressWarnings("unchecked")
		Page<BotGroup> page = mock(Page.class);
		when(page.getContent()).thenReturn(groups);
		
		BotGroupService botGroupService = mock(BotGroupService.class);
		when(botGroupService.findAll(any(Pageable.class))).thenReturn(page);
		
		GroupsController controller = new GroupsController();
		
		ReflectionTestUtils.setField(controller, "botGroupService", botGroupService);
		
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = controller.list(uiModel, null);
		
		assertNotNull(result);
		assertEquals("groups/list", result);
		
		@SuppressWarnings("unchecked")
		Page<BotGroup> modelPage = (Page<BotGroup>) uiModel.get("page");
		
		assertEquals(1, modelPage.getContent().size());
	}

	@Test
	public void testCreate() {
		
		final Bot bot = new Bot("botName", "botAddress", true);
		final BotGroup newGroup = new BotGroup("groupName");
		newGroup.addBot(bot);
		
		BotGroupService botGroupService = mock(BotGroupService.class);
		when(botGroupService.create(newGroup)).thenAnswer(new Answer<BotGroup>() {
			public BotGroup answer(InvocationOnMock invocation) throws Throwable {
				groups.add(newGroup);
				return newGroup;
			}
		});
		GroupsController controller = new GroupsController();
		
		ReflectionTestUtils.setField(controller, "botGroupService", botGroupService);
		
		ExtendedModelMap uiModel = new ExtendedModelMap();
		BindingResult bindingResult = mock(BindingResult.class);
		String view = controller.create(newGroup, bindingResult, uiModel);
		
		assertNotNull(view);
		assertEquals("redirect:/groups/" + newGroup.getId(), view);
		assertEquals(2, groups.size());

	}
	
	@Test
	public void testCreateForm() {
	}

	@Test
	public void testDetail() {
		
		String randomUUId = UUID.randomUUID().toString();
		BotGroupService botGroupService = mock(BotGroupService.class);
		when(botGroupService.findById(randomUUId)).thenReturn(groups.get(0));
		
		GroupsController controller = new GroupsController();
		
		ReflectionTestUtils.setField(controller, "botGroupService", botGroupService);
		
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = controller.show(randomUUId, uiModel);
		
		assertNotNull(result);
		assertEquals("groups/details", result);
		
		BotGroup group = (BotGroup) uiModel.get("group");
		assertEquals("groupname1", group.getName());
		assertEquals(1, groups.size());
		assertEquals(0, group.getBots().size());
		assertTrue(group.isActive());

	}

	@Test
	public void testUpdate() {
		
		String randomUUId = UUID.randomUUID().toString();
		BotGroupService botGroupService = mock(BotGroupService.class);
		when(botGroupService.findById(randomUUId)).thenReturn(groups.get(0));
		
		GroupsController controller = new GroupsController();
		
		ReflectionTestUtils.setField(controller, "botGroupService", botGroupService);
		
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = controller.show(randomUUId, uiModel);
		
		assertNotNull(result);
		assertEquals("groups/details", result);
		
		BotGroup group = (BotGroup) uiModel.get("group");
		assertEquals("groupname1", group.getName());
		assertEquals(0, group.getBots().size());
		assertTrue(group.isActive());

	}

	public void testUpdateForm() {
	}

	public void testDelete() {
		
		assertEquals("Test setup is in an invalid state", 1, groups.size());
		
		final BotGroup group = groups.get(0);
		final String groupId = group.getId();
		
		BotGroupService botGroupService = mock(BotGroupService.class);
		Mockito.doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) throws Throwable {
				groups.remove(group);
				return null;
			}
		}).when(botGroupService).delete(group);
		
		GroupsController controller = new GroupsController();
		ReflectionTestUtils.setField(controller, "botGroupService", botGroupService);
		
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String view = controller.delete(groupId, uiModel);
		
		assertNotNull(view);
		assertEquals(group, uiModel.get("group"));
		assertEquals("groups/list", view);
		assertEquals(0, groups.size());

	}
	
}
