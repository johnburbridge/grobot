package org.metabuild.grobot.server.rest;

import java.util.List;

import org.metabuild.grobot.common.domain.BotGroup;
import org.metabuild.grobot.server.service.BotGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordnik.swagger.annotations.Api;

/**
 * @author jburbridge
 * @since 7/4/2014
 */
@Api(value="groups", description="The Bot Groups API")
@RequestMapping("/api/groups")
@Controller
public class BotGroupApi {

	@Autowired
	private BotGroupService botGroupService;

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method=RequestMethod.GET)
	public List<BotGroup> getList() {
		return botGroupService.findAll();
	}
}
