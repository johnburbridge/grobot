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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.metabuild.grobot.scripts.BindingProvider;
import org.metabuild.grobot.scripts.ScriptWrapper;
import org.metabuild.grobot.scripts.groovy.GroovyBindingProvider;
import org.metabuild.grobot.scripts.groovy.GroovyScriptFactory;

/**
 * @author jburbridge
 *
 */
public class GroovyScriptFactoryTest {
	
	private final static String SCRIPTS_DIR = "src/test/resources/scripts";

	@Test
	public void testGrobotTaskFactoryValidDirectory() throws IOException {
		GroovyScriptFactory factory = new GroovyScriptFactory(getGroovyBindingProvider(), new File(SCRIPTS_DIR), new GroovyScriptEngine(SCRIPTS_DIR));
		assertNotNull(factory);
	}

	@Test(expected=RuntimeException.class)
	public void testGrobotTaskFactoryInvalidDirectory() throws IOException {
		new GroovyScriptFactory( getGroovyBindingProvider(), new File(SCRIPTS_DIR + "/b0rk3n"), new GroovyScriptEngine(SCRIPTS_DIR + "/b0rk3n"));
		fail("Should have thrown a RuntimeException but didn't");
	}

	@Test
	public void testGetScripts() throws IOException {
		GroovyScriptFactory factory = new GroovyScriptFactory(getGroovyBindingProvider(), new File(SCRIPTS_DIR), new GroovyScriptEngine(SCRIPTS_DIR));
		List<ScriptWrapper> scripts = factory.loadScripts();
		assertNotNull(scripts);
		assertTrue(scripts.size() > 0);
	}

	@Test
	public void testGetFiles() throws IOException {
		GroovyScriptFactory factory = new GroovyScriptFactory(getGroovyBindingProvider(), new File(SCRIPTS_DIR),  new GroovyScriptEngine(SCRIPTS_DIR));
		assertNotNull(factory.getFiles(new File(SCRIPTS_DIR)));
	}

	@Test
	public void testGetBinding() throws IOException {
		GroovyScriptFactory factory = new GroovyScriptFactory(getGroovyBindingProvider(), new File(SCRIPTS_DIR), new GroovyScriptEngine(SCRIPTS_DIR));
		assertNotNull(factory.getBinding());
	}

	@Test
	public void testGetEngine() throws IOException {
		GroovyScriptFactory factory = new GroovyScriptFactory(getGroovyBindingProvider(), new File(SCRIPTS_DIR), new GroovyScriptEngine(SCRIPTS_DIR));
		assertNotNull(factory.getEngine());
	}

	@Test
	public void testGetScriptsDir() throws IOException {
		GroovyScriptFactory factory = new GroovyScriptFactory(getGroovyBindingProvider(), new File(SCRIPTS_DIR), new GroovyScriptEngine(SCRIPTS_DIR));
		assertEquals(SCRIPTS_DIR, factory.getScriptsDir().getPath());
	}

	protected BindingProvider getGroovyBindingProvider() {
		return new GroovyBindingProvider(new HashMap<Object,Object>(), new Binding());
	}
}
