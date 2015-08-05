/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link DefaultApplicationArguments}.
 *
 * @author Phillip Webb
 */
public class DefaultApplicationArgumentsTests {

	private static final String[] ARGS = new String[] { "--foo=bar", "--foo=baz",
			"--debug", "spring", "boot" };

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void argumentsMustNotBeNull() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Args must not be null");
		new DefaultApplicationArguments(null);
	}

	@Test
	public void getArgs() throws Exception {
		ApplicationArguments arguments = new DefaultApplicationArguments(ARGS);
		assertThat(arguments.getSourceArgs(), equalTo(ARGS));
	}

	@Test
	public void optionNames() throws Exception {
		ApplicationArguments arguments = new DefaultApplicationArguments(ARGS);
		Set<String> expected = new HashSet<String>(Arrays.asList("foo", "debug"));
		assertThat(arguments.getOptionNames(), equalTo(expected));
	}

	@Test
	public void containsOption() throws Exception {
		ApplicationArguments arguments = new DefaultApplicationArguments(ARGS);
		assertThat(arguments.containsOption("foo"), equalTo(true));
		assertThat(arguments.containsOption("debug"), equalTo(true));
		assertThat(arguments.containsOption("spring"), equalTo(false));
	}

	@Test
	public void getOptionValues() throws Exception {
		ApplicationArguments arguments = new DefaultApplicationArguments(ARGS);
		assertThat(arguments.getOptionValues("foo"), equalTo(Arrays.asList("bar", "baz")));
		assertThat(arguments.getOptionValues("debug"),
				equalTo(Collections.<String> emptyList()));
		assertThat(arguments.getOptionValues("spring"), equalTo(null));
	}

	@Test
	public void getNonOptionArgs() throws Exception {
		ApplicationArguments arguments = new DefaultApplicationArguments(ARGS);
		assertThat(arguments.getNonOptionArgs(), equalTo(Arrays.asList("spring", "boot")));
	}

	@Test
	public void getNoNonOptionArgs() throws Exception {
		ApplicationArguments arguments = new DefaultApplicationArguments(
				new String[] { "--debug" });
		assertThat(arguments.getNonOptionArgs(),
				equalTo(Collections.<String> emptyList()));
	}

}