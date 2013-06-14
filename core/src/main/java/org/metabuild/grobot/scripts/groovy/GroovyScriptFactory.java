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

import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.metabuild.grobot.scripts.BindingProvider;
import org.metabuild.grobot.scripts.ScriptWrapper;
import org.metabuild.grobot.scripts.ScriptWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TaskFactory is responsible for loading the groovy scripts from the scripts directory
 * and creating GroovyScript instances that can be executed against a target node
 * 
 * @author jburbridge
 * @since 9/27/2012
 */
public class GroovyScriptFactory implements ScriptWrapperFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroovyScriptFactory.class.getName());
	private static final String DOT_GROOVY = ".groovy";
	
	private final BindingProvider bindingProvider;
	private final File scriptsDir;
	private final GroovyScriptEngine engine;
	
	/**
	 * @param bindingProvider
	 * @param scriptsDir
	 * @param engine
	 */
	protected GroovyScriptFactory(BindingProvider bindingProvider, File scriptsDir, GroovyScriptEngine engine) {
		this.bindingProvider = bindingProvider;
		this.scriptsDir = scriptsDir;
		this.engine = engine;
		if (!scriptsDir.isDirectory() || !scriptsDir.canRead()) {
			throw new RuntimeException("Could not find the scripts directory " + scriptsDir.getAbsolutePath());
		}
	}

	/**
	 * @param scriptsDir
	 * @throws IOException 
	 */
	public GroovyScriptFactory(File scriptsDir) throws IOException {
		this(new GroovyBindingProvider(System.getProperties(), new Binding()), 
				scriptsDir, new GroovyScriptEngine(scriptsDir.toString()));
	}

	/**
	 * Recurses through the scripts directory and loads the groovy files
	 * 
	 * @return a list of scripts
	 */
	@Override
	public List<ScriptWrapper> loadScripts() {
		List<ScriptWrapper> scripts = new ArrayList<ScriptWrapper>();
		for (File file : getFiles(scriptsDir)) {
			try {
				Script script = engine.createScript(file.getAbsolutePath(), getBinding());
				GroovyScript groovyScript = new GroovyScript(script);
				groovyScript.setHash(DigestUtils.md5Hex(file.toString()));
				scripts.add(groovyScript);
			} catch (ResourceException e) {
				LOGGER.warn("Could not load task from {}", file.getAbsolutePath(), e);
			} catch (ScriptException e) {
				LOGGER.warn("Could not load task from {}", file.getAbsolutePath(), e);
			}
		}
		return scripts;
	}
	
	/**
	 * Recursively gets all of the files in scriptsDir that end in ".groovy"
	 * 
	 * @param scriptsDir
	 * @return a List of files
	 */
	protected List<File> getFiles(File scriptsDir) {
		List<File> files = new ArrayList<File>();
		if (scriptsDir.isDirectory()) {
			for (File file : scriptsDir.listFiles()) {
				if (file.isDirectory()) {
					files.addAll(getFiles(file));
				} else if (file.getName().endsWith(DOT_GROOVY)) {
					files.add(file);
				}
			}
		}
		return files;
	}

	/**
	 * @return the scriptsDir
	 */
	public File getScriptsDir() {
		return scriptsDir;
	}

	/**
	 * @return the engine
	 */
	public GroovyScriptEngine getEngine() {
		return engine;
	}

	/**
	 * @return the binding provider
	 */
	public BindingProvider getBindingProvider() {
		return bindingProvider;
	}

	/**
	 * @return the binding
	 */
	public Binding getBinding() {
		return bindingProvider.getBinding();
	}
}
