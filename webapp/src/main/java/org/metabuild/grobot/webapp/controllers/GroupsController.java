/*
 * Copyright 2013 Metabuild Software, LLC. (http://www.metabuild.org)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
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
import org.metabuild.grobot.server.service.RecordNotFoundException;
import org.metabuild.grobot.server.service.BotGroupService;
import org.metabuild.grobot.server.service.BotService;
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
 * @since 03/11/2013
 */
@Controller
@RequestMapping("/groups")
public class GroupsController extends AbstractBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupsController.class);
	private static final String GROUPS_LIST_VIEW = "groups/list";
	private static final String GROUPS_DETAIL_VIEW = "groups/details";
	private static final String GROUPS_FORM_VIEW = "groups/update";

	@Autowired
	private BotGroupService botGroupService;
	
	@Autowired
	private BotService botService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(Set.class, "bots", new CustomCollectionEditor(Set.class) {
			protected Object convertElement(Object element) {
				if (element instanceof Bot) {
					LOGGER.debug("Returning Bot element {} as-is", ((Bot) element).getId());
					return element;
				} if (element instanceof String) {
					LOGGER.debug("Converting String element {} to Bot", element);
					return botService.findById(element.toString());
				} else {
					LOGGER.warn("Element not converted, returning null for {}", element);
					return null;
				}
			}
		});
	}
	
	/**
	 * Display the list of groups
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model uiModel, Pageable pageable) {
		
		final Page<BotGroup> page = botGroupService.findAll(pageable);
		uiModel.addAttribute("page", page);
		
		return GROUPS_LIST_VIEW;
	}
	
	/**
	 * the details of a target
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String show(@PathVariable("id") String id, Model uiModel) {
		
		uiModel.addAttribute("group", botGroupService.findById(id));
		
		return GROUPS_DETAIL_VIEW;
	}
	
	/**
	 * setup the create form
	 */
	@RequestMapping(method=RequestMethod.GET, params="form")
	public String createForm(Model uiModel) {
		BotGroup group = new BotGroup();
		uiModel.addAttribute("group", group);
		populateBotsSelect(uiModel);
		return GROUPS_FORM_VIEW;
	}

	/**
	 * creates a new record and presents the detail view
	 */
	@RequestMapping(method=RequestMethod.POST, params="form")
	public String create(@ModelAttribute BotGroup group, BindingResult result, Model uiModel) {
		LOGGER.info("Creating new BotGroup with {}", group);
		botGroupService.create(group);
		return "redirect:/"  + getSelectedNavMenuItem().getPath() 
				+ "/" + group.getId();
	}

	/**
	 * the update form
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET, params="form")
	public String updateForm(@PathVariable("id") String id, Model uiModel) {
		
		uiModel.addAttribute("group", botGroupService.findById(id));
		populateBotsSelect(uiModel);
		return GROUPS_FORM_VIEW;
	}
	
	/**
	 * updates and presents the detail view
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST, params="form")
	public String update(@ModelAttribute BotGroup group, BindingResult result, Model uiModel) {
		
		if (result.hasErrors()) {
			uiModel.addAttribute("errorMessage", result.getAllErrors());
		}
		LOGGER.info("Updating BotGroup with {}", group);
		try {
			botGroupService.update(group);
		} catch (RecordNotFoundException e) {
			uiModel.addAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/"  + getSelectedNavMenuItem().getPath() 
				+ "/" + group.getId();
	}

	
	/**
	 * deletes and presents the list view
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST, params="delete")
	public String delete(@PathVariable("id") String id, Model uiModel) {
		
		BotGroup group = botGroupService.findById(id);
		uiModel.addAttribute("group", group);
		botGroupService.delete(group);
		
		return "redirect:/" + getSelectedNavMenuItem().getPath();
	}

	/**
	 * Add all bots to the model so we can populate the select box
	 * 
	 * @param uiModel
	 */
	protected void populateBotsSelect(Model uiModel) {
		uiModel.addAttribute("allBots", botService.findAll());
	}
	
	@Override
	public NavMenuItems getSelectedNavMenuItem() {
		return NavMenuItems.GROUPS;
	}
}
