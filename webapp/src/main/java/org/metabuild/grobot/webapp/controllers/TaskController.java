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

import org.metabuild.grobot.common.domain.Script;
import org.metabuild.grobot.common.domain.Task;
import org.metabuild.grobot.scripts.groovy.GroovyScript;
import org.metabuild.grobot.scripts.groovy.GroovyScriptCache;
import org.metabuild.grobot.server.service.RecordNotFoundException;
import org.metabuild.grobot.server.service.ScriptService;
import org.metabuild.grobot.server.service.TaskService;
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
 * @since 10/21/2012
 */
@RequestMapping("/tasks")
@Controller
public class TaskController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
	private static final String TASKS_LIST_VIEW = "tasks/list";
	private static final String TASKS_DETAILS_VIEW = "tasks/details";
	private static final String TASKS_FORM_VIEW = "tasks/update";

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ScriptService scriptService;
	
	@Autowired
	private GroovyScriptCache groovyScriptCache;
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) throws Exception {
//		binder.registerCustomEditor(Set.class, new CustomCollectionEditor(Set.class) { 
//			protected Object convertElement(Object element) {
//				if (element instanceof Script) {
//					return element;
//				} if (element instanceof String) {
//					return scriptService.findById(element.toString());
//					GroovyScript script = groovyScriptCache.get((String) element);
//					return script != null ? script.getHash() : null;
//				} else {
//					return null;
//				}
//			}
//		});
//	}

	/**
	 * Display the list of tasks
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model uiModel, Pageable pageable) {
		final Page<Task> page = taskService.findAll(pageable);
		uiModel.addAttribute("page", page);
		addSelectedMenuItem(uiModel);
		
		return TASKS_LIST_VIEW;
	}
	
	/**
	 * Display the details of a task
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String details(@PathVariable("id") String id, Model uiModel) {
		uiModel.addAttribute("task", taskService.findById(id));
		addSelectedMenuItem(uiModel);
		return TASKS_DETAILS_VIEW;
	}
	
	/**
	 * setup the create form
	 * @param uiModel the model
	 * @return the view
	 */
	@RequestMapping(method=RequestMethod.GET, params="form")
	public String createForm(Model uiModel) {
		Task task = new Task();
		uiModel.addAttribute("task", task);
		populateScriptsSelect(uiModel);
		return TASKS_FORM_VIEW;
	}

	/**
	 * creates a new record and presents the detail view
	 */
	@RequestMapping(method=RequestMethod.POST, params="form")
	public String create(@ModelAttribute Task task, BindingResult result, Model uiModel) {
		LOGGER.info("Creating new Task with {}", task);
		taskService.create(task);
		return getTaskDetailRedirect(task);
	}

	/**
	 * the update form
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET, params="form")
	public String updateForm(@PathVariable("id") String id, Model uiModel) {
		uiModel.addAttribute("task", taskService.findById(id));
		populateScriptsSelect(uiModel);
		return TASKS_FORM_VIEW;
	}
	
	/**
	 * updates and presents the detail view
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST, params="form")
	public String update(@ModelAttribute Task task, BindingResult result, Model uiModel) {
		if (result.hasErrors()) {
			uiModel.addAttribute("errorMessage", result.getAllErrors());
		}
		
		LOGGER.info("Updating Task with {}", task);
		try {
			taskService.update(task);
		} catch (RecordNotFoundException e) {
			uiModel.addAttribute("errorMessage", e.getMessage());

		}
		return getTaskDetailRedirect(task);
	}
	
	/**
	 * deletes and presents the list view
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET, params="delete")
	public String delete(@PathVariable("id") String id, Model uiModel) {
		Task task = taskService.findById(id);
		uiModel.addAttribute("task", task);
		taskService.delete(task);
		return "redirect:/" + getSelectedNavMenuItem().getPath();
	}
	
	/**
	 * @param uiModel
	 */
	protected void populateScriptsSelect(Model uiModel) {
		uiModel.addAttribute("allScripts", groovyScriptCache.getAll());
	}
	
	/**
	 * 
	 * @param task
	 * @return the redirect path to the task page
	 */
	protected String getTaskDetailRedirect(Task task) {
		return new StringBuilder("redirect:/")
			.append(getSelectedNavMenuItem().getPath())
			.append("/").append(task.getId())
			.toString();
	}
	
	@Override
	public NavMenuItems getSelectedNavMenuItem() {
		return NavMenuItems.TASKS;
	}
}
