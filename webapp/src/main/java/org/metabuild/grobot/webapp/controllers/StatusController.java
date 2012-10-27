package org.metabuild.grobot.webapp.controllers;

import java.util.List;

import javax.jms.JMSException;

import org.metabuild.grobot.domain.TargetHostCache;
import org.metabuild.grobot.domain.TargetHost;
import org.metabuild.grobot.server.mq.StatusRequestProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jburbridge
 * @since 10/12/2012
 */
@RequestMapping("/status")
@Controller
public class StatusController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);
	private static final String STATUS_LIST_VIEW = "status/list";
	private static final String STATUS_DETAILS_VIEW = "status/details";

	@Autowired
	private TargetHostCache targetHostCache;

	@Autowired
	private StatusRequestProducer producer;

	/**
	 * Display the list of targets
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model uiModel) {
		
		final List<TargetHost> targets = 	targetHostCache.getAll();
		uiModel.addAttribute("targets", targets);
		
		try {
			producer.sendStatusRequest();
		} catch (JMSException e) {
			LOGGER.error("JMS Exception caught while attempting to send status request", e);
		}
		return STATUS_LIST_VIEW;
	}
	
	/**
	 * Display the details of a target
	 */
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public String details(@PathVariable("name") String name, Model uiModel) {
		
		final TargetHost target = targetHostCache.get(name);
		uiModel.addAttribute("target", target);
		
		return STATUS_DETAILS_VIEW;
	}
}
