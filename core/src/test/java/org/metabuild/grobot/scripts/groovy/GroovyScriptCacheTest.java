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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.metabuild.grobot.scripts.ScriptWrapper;
import org.metabuild.grobot.scripts.groovy.GroovyScript;
import org.metabuild.grobot.scripts.groovy.GroovyScriptCache;
import org.metabuild.grobot.scripts.groovy.GroovyScriptFactory;

/**
 * @author jburbridge
 * @since 10/23/2012
 */
public class GroovyScriptCacheTest {

	/**
	 * Test method for {@link org.metabuild.grobot.scripts.groovy.GroovyScriptCache#GroovyTaskCache(org.metabuild.grobot.scripts.groovy.GroovyScriptFactory, java.util.Map)}.
	 */
	@Test
	public void testGroovyScriptCacheEmptyMap() {
		assertNotNull(new GroovyScriptCache(getMockScriptFactory(), new HashMap<String, GroovyScript>()));
	}

	/**
	 * Test method for {@link org.metabuild.grobot.scripts.groovy.GroovyScriptCache#get(java.lang.String)}.
	 */
	@Test
	public void testGetScriptWithMatch() {
		GroovyScriptCache scriptCache = new GroovyScriptCache(getMockScriptFactory(), new HashMap<String, GroovyScript>());
		assertNotNull(scriptCache.get("MyMockScriptHash"));
		assertEquals("MyMockScriptName",scriptCache.get("MyMockScriptHash").toString());
		assertNull(scriptCache.get(null));
	}

	public static GroovyScriptFactory getMockScriptFactory() {
		
		GroovyScriptFactory scriptFactory = mock(GroovyScriptFactory.class);
		GroovyScript script = mock(GroovyScript.class);
		when(script.getHash()).thenReturn("MyMockScriptHash");
		when(script.toString()).thenReturn("MyMockScriptName");
		List<ScriptWrapper> scripts = new ArrayList<ScriptWrapper>();
		scripts.add(script);
		when(scriptFactory.loadScripts()).thenReturn(scripts);
		return scriptFactory;
	}
}
