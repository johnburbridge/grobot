/*
 * Copyright 2014 Metabuild Software, LLC. (http://www.metabuild.org)
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
package org.metabuild.grobot.server.rest;

import java.util.List;

import org.metabuild.grobot.common.domain.Bot;
import org.metabuild.grobot.server.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordnik.swagger.annotations.Api;

/**
 * @author jburbridge
 * @since 7/4/2014
 */
@Api(value="bots", description="The Bots API")
@RequestMapping("/api/bots")
@Controller
public class BotApi {

	@Autowired
	private BotService botService;

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET)
	public List<Bot> getList() {
		return botService.findAll();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Bot getDetail(@PathVariable("id") String botId) {
		return botService.findById(botId);
	}
}
