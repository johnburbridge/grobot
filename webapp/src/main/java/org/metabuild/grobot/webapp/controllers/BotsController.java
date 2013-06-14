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

import java.util.Set;

import org.metabuild.grobot.common.domain.Bot;
import org.metabuild.grobot.common.domain.BotGroup;
import org.metabuild.grobot.common.domain.BotStatus;
import org.metabuild.grobot.server.service.BotGroupService;
import org.metabuild.grobot.server.service.RecordNotFoundException;
import org.metabuild.grobot.server.service.BotService;
import org.metabuild.grobot.server.status.StatusRequestProducer;
import org.metabuild.grobot.server.status.StatusRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jburbridge
 * @since 10/12/2012
 */
@RequestMapping("/bots")
@Controller
public class BotsController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BotsController.class);
	private static final String BOTS_LIST_VIEW = "bots/list";
	private static final String BOTS_DETAILS_VIEW = "bots/details";
	private static final String BOTS_FORM_VIEW = "bots/update";

	@Autowired
	private BotService botService;

	@Autowired
	private BotGroupService botGroupService;

	@Autowired
	private StatusRequestProducer producer;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(Set.class, "botGroups", new CustomCollectionEditor(Set.class) {
			protected Object convertElement(Object element) {
				if (element instanceof BotGroup) {
					LOGGER.debug("Returning BotGroup element {} as-is", ((BotGroup) element).getId());
					return element;
				} else if (element instanceof String) {
					LOGGER.debug("Converting String element {} to BotGroup", element);
					return botGroupService.findById(element.toString());
				} else {
					LOGGER.warn("Element not converted, returning null for {}", element);
					return null;
				}
			}
		});
	}
	


	/**
	 * Display the list of targets
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model uiModel, Pageable pageable) {
		
		final Page<Bot> page = botService.findAll(pageable);
		final long lastStatusRequest = StatusRequestService.getLastRunTimestamp();
		
		for (Bot target : page.getContent()) {
			if (target.getLastUpdatedStatus() == null || 
					lastStatusRequest > target.getLastUpdatedStatus().getMillis() + 30000) {
				LOGGER.debug("No activity for {} in the last 30 seconds", target);
				target.setStatus(BotStatus.STOPPED);
			}
		}
		
		uiModel.addAttribute("page", page);
		addSelectedMenuItem(uiModel);
		
		return BOTS_LIST_VIEW;
	}
	
	/**
	 * Display the details of a target
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String details(@PathVariable("id") String id, Model uiModel) {
		uiModel.addAttribute("bot", botService.findById(id));
		addSelectedMenuItem(uiModel);
		return BOTS_DETAILS_VIEW;
	}
	
	/**
	 * setup the create form
	 * @param uiModel the model
	 * @return the view
	 */
	@RequestMapping(method=RequestMethod.GET, params="form")
	public String createForm(Model uiModel) {
		Bot bot = new Bot();
		uiModel.addAttribute("bot", bot);
		populateBotGroupsSelect(uiModel);
		return BOTS_FORM_VIEW;
	}
	
	/**
	 * creates a new record and presents the detail view
	 */
	@RequestMapping(method=RequestMethod.POST, params="form")
	public String create(@ModelAttribute Bot bot, BindingResult result, Model uiModel) {
		LOGGER.info("Creating new Bot with {}", bot);
		botService.create(bot);
		return getBotDetailRedirect(bot);
	}

	/**
	 * the update form
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET, params="form")
	public String updateForm(@PathVariable("id") String id, Model uiModel) {
		uiModel.addAttribute("bot", botService.findById(id));
		populateBotGroupsSelect(uiModel);
		return BOTS_FORM_VIEW;
	}
	
	/**
	 * updates and presents the detail view
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST, params="form")
	public String update(@ModelAttribute Bot bot, BindingResult result, Model uiModel) {
		if (result.hasErrors()) {
			uiModel.addAttribute("errorMessage", result.getAllErrors());
		}
		
		LOGGER.info("Updating Bot with {}", bot);
		try {
			botService.update(bot);
		} catch (RecordNotFoundException e) {
			uiModel.addAttribute("errorMessage", e.getMessage());
		}
		return getBotDetailRedirect(bot);
	}

	
	/**
	 * deletes and presents the list view
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST, params="delete")
	public String delete(@PathVariable("id") String id, Model uiModel) {
		
		Bot bot = botService.findById(id);
		uiModel.addAttribute("bot", bot);
		botService.delete(bot);
		
		return "redirect:/" + getSelectedNavMenuItem().getPath();
	}

	
	
	/**
	 * @param uiModel
	 */
	protected void populateBotGroupsSelect(Model uiModel) {
		uiModel.addAttribute("allBotGroups", botGroupService.findAll());
	}
	
	/**
	 * @param bot
	 * @return the redirect path to the bot detail page
	 */
	protected String getBotDetailRedirect(Bot bot) {
		return new StringBuilder("redirect:/")
			.append(getSelectedNavMenuItem().getPath())
			.append("/").append(bot.getId())
			.toString();
	}

	@Override
	public NavMenuItems getSelectedNavMenuItem() {
		return NavMenuItems.BOTS;
	}
}
