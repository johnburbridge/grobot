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
package org.metabuild.grobot.server.data;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.metabuild.grobot.common.domain.Bot;
import org.metabuild.grobot.common.domain.BotGroup;
import org.metabuild.grobot.server.service.BotGroupService;
import org.metabuild.grobot.server.service.RecordNotFoundException;
import org.metabuild.grobot.server.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback=true)
public class BotGroupServiceTest extends AbstractDataTester {

	@Autowired
	private BotGroupService botGroupService;
	
	@Autowired
	private BotService botService;
	
	@Test
	public void testFindAll() {
		List<BotGroup> groups = botGroupService.findAll();
		assertTrue(groups.size() >= 1);
		assertTrue(groups.get(0).getBots().size() >= 1);
	}
	
	@Test
	public void testFindById() {
		BotGroup group = botGroupService.findById("225c172b-950f-4c24-9ee7-4d3b4ea1cf55");
		assertNotNull(group);
		assertEquals("groupName", group.getName());
		assertTrue(group.isActive());
		assertNull(botGroupService.findById("invalid-id"));
	}
	
	@Test
	public void testCreateWithBots() {
		Bot newBot1 = new Bot("botName1", "bot-address1", true);
		Bot newBot2 = new Bot("botName2", "bot-address2", true);
		Bot newBot3 = new Bot("botName3", "bot-address3", true);
		assertNotNull(botService.create(newBot1));
		assertNotNull(botService.create(newBot2));
		assertNotNull(botService.create(newBot3));
		Set<Bot> bots = new HashSet<Bot>();
		bots.add(newBot1);
		bots.add(newBot2);
		bots.add(newBot3);
		BotGroup newGroup = new BotGroup("newBotGroupName1", bots, null, true);
		newGroup = botGroupService.create(newGroup);
		assertNotNull(newGroup.getId());
		assertEquals(3, newGroup.getBots().size());
	}
	
	@Test
	public void testUpdateWithBots() throws RecordNotFoundException {
		BotGroup newGroup = new BotGroup("newBotGroupName2", new HashSet<Bot>(), null, true);
		botGroupService.create(newGroup);
		assertNotNull(newGroup.getId());
		assertEquals(0, newGroup.getBots().size());
		
		Bot bot4 = new Bot("botName4", "bot-address4", true);
		Bot bot5 = new Bot("botName5", "bot-address5", true);
		Bot bot6 = new Bot("botName6", "bot-address6", true);
		assertNotNull(botService.create(bot4));
		assertNotNull(botService.create(bot5));
		assertNotNull(botService.create(bot6));
		assertEquals(0, bot4.getBotGroups().size());
		assertEquals(0, bot5.getBotGroups().size());
		assertEquals(0, bot6.getBotGroups().size());
		
		newGroup.addBot(bot4);
		newGroup.addBot(bot5);
		newGroup.addBot(bot6);
		assertEquals(3, newGroup.getBots().size());
		botGroupService.update(newGroup);

		// reload from database
		bot4 = botService.findById(bot4.getId());
		assertEquals(1, bot4.getBotGroups().size());

		newGroup.removeBot(bot6);
		botGroupService.update(newGroup);
		botService.update(bot6);
		assertEquals(2, newGroup.getBots().size());
		
		// reload from database
		bot6 = botService.findById(bot6.getId());
		assertEquals(0, bot6.getBotGroups().size());
		
	}

}
