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
package org.metabuild.grobot.server.config;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.io.IOException;

import org.metabuild.grobot.scripts.BindingProvider;
import org.metabuild.grobot.scripts.groovy.GroovyBindingProvider;
import org.metabuild.grobot.scripts.groovy.GroovyScriptCache;
import org.metabuild.grobot.scripts.groovy.GroovyScriptFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Common spring application configuration
 * 
 * @author jburbridge
 * @since 9/30/2012
 */
@Configuration
@Profile("default")
@Import({ 
	DevelopmentAppConfig.class, 
	ProductionAppConfig.class,
	JpaConfig.class
})
@ComponentScan(basePackages = { 
	"org.metabuild.grobot.webapp.domain"
})
@PropertySource("classpath:grobot-server.properties")
public class DefaultAppConfig {

	@Autowired
	Environment environment;
	
	/**
	 * @return the directory where groovy scripts are saved
	 */
	@Bean(name="scriptsDir")
	public String getScriptsDir() {
		return new StringBuilder(environment.getProperty("user.dir"))
			.append(File.separator)
			.append("scripts")
			.toString();
	}
	
	/**
	 * @param scriptsDir the directory where groovy scripts are located
	 * @return the GroovyScriptEngine
	 * @throws IOException
	 */
	@Autowired(required=true)
	@Bean(name="GroovyScriptEngine")
	public GroovyScriptEngine getGroovyScriptEngine(@Qualifier(value="scriptsDir") String scriptsDir) 
			throws IOException {
		return new GroovyScriptEngine(scriptsDir);
	}
	
	/**
	 * @return the GroovyBindingProvider
	 */
	@Bean(name="bindingProvider")
	public BindingProvider getBindingProvider() {
		return new GroovyBindingProvider(System.getProperties(), new Binding());
	}
	
	/**
	 * @param bindingProvider
	 * @param scriptsDir
	 * @param groovyScriptEngine
	 * @return the in-memory cache for scripts
	 * @throws IOException 
	 */
	@Autowired(required=true)
	@Bean(name="groovyTaskCache")
	public GroovyScriptCache getGroovyTaskCache(String scriptsDir) throws IOException {
		final GroovyScriptFactory groovyScriptFactory = new GroovyScriptFactory(new File(scriptsDir));
		return new GroovyScriptCache(groovyScriptFactory);
	}
	
	/**
	 * @return the server's fqdn
	 */
	@Bean(name="serverHostname")
	public String getServerHostname() {
		return environment.getProperty("grobot.server.hostname");
	}
}
