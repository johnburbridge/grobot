/*
 * Copyright 2012 Metabuild Software, LLC. (http://www.metabuild.org)
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
package org.metabuild.grobot.server.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

/**
 * Spring Web JavaConfig style configuration
 * 
 * Typically this would be the configuration class for the webapp only, but because the webapp also happens to be
 * the entry point for the grobot server, all other configuration elements (embedded activemq broker, jms elements, 
 * spring security, schedulers and jpa/hibernate configurations are imported from here.
 * 
 * @author jburbridge
 * @since 9/30/2012
 */
@Configuration
@EnableWebMvc
@ImportResource(value = { 
	"classpath:activeMqBrokerConfig.xml",
	"classpath:spring-security.xml"
})
@Import({ 
	DefaultAppConfig.class, 
	ServerJmsConfig.class
})
@ComponentScan(basePackages = { "org.metabuild.grobot.webapp.controllers" })
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * Add the "resources" directory at the root of the webapp to the registry. This is where we add
	 * our static resources - images, js, css, etc.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	/**
	 * We need to enable the default servlet handler since the dispatcher servlet is mapped to receive 
	 * all requests... otherwise we wouldn't be able to serve static content from the /resources folder
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * Add PageableArgumentResolver to help with pagination
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableArgumentResolver resolver = new PageableArgumentResolver();
		resolver.setFallbackPagable(new PageRequest(1, 50));
		argumentResolvers.add(new ServletWebArgumentResolverAdapter(resolver));
	}
		
	@Bean(name="tilesViewResolver")
	public ViewResolver getUrlBasedViewResolver() {
		final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setViewClass(org.springframework.web.servlet.view.tiles3.TilesView.class);
		return resolver;
	}
	
	@Bean(name="tilesConfigurer")
	public TilesConfigurer getTilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		final String[] definitions = { "/WEB-INF/layouts/layouts.xml", "/WEB-INF/views/**/views.xml" };
		configurer.setDefinitions(definitions);
		configurer.setValidateDefinitions(true);
		return configurer;
	}
}