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
package org.metabuild.grobot.scripts.groovy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.metabuild.grobot.scripts.ScriptWrapper;

/**
 * @author jburbridge
 *
 */
public class GroovyScriptCache {
	
	private final GroovyScriptFactory groovyScriptFactory;
	private final Map<String, GroovyScript> registry;
	
	
	/**
	 * @param groovyScriptFactory
	 */
	public GroovyScriptCache(GroovyScriptFactory groovyScriptFactory) {
		this(groovyScriptFactory, new HashMap<String,GroovyScript>());
	}

	
	/**
	 * @param groovyScriptFactory
	 * @param registry
	 */
	protected GroovyScriptCache(GroovyScriptFactory groovyScriptFactory, Map<String, GroovyScript> registry) {
		this.groovyScriptFactory = groovyScriptFactory;
		this.registry = loadScripts(registry);
	}
	
	/**
	 * @param registry map to be populated
	 * @return the populated registry map
	 */
	protected Map<String, GroovyScript> loadScripts(Map<String, GroovyScript> registry) {
		for (ScriptWrapper script : groovyScriptFactory.loadScripts()) {
			registry.put(script.getHash(), (GroovyScript) script);
		}
		return registry;
	}
	
	/**
	 * @param hash
	 * @return the script
	 */
	public GroovyScript get(String hash) {
		return registry.get(hash);
	}
	
	/**
	 * @return all the scripts currently cached
	 */
	public List<GroovyScript> getAll() {
		return new ArrayList<GroovyScript>(registry.values());
	}
}
