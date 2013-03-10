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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.metabuild.grobot.AbstractSpringEnabledTest;
import org.springframework.ui.ExtendedModelMap;

/**
 * @author jburbridge
 * @since 10/24/2012
 */
public class HomePageControllerTest extends AbstractSpringEnabledTest {

	@Test
	public void testIndex() {
		
		HomePageController controller = new HomePageController();
		ExtendedModelMap uiModel = new ExtendedModelMap();
		String result = controller.getIndex(uiModel);
		
		assertNotNull(result);
		assertEquals("bots/list", result);
	}

}
