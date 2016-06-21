package com.acuo.common.util;

import static com.acuo.common.TestHelper.matchesRegex;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Dispatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WithResteasyFixturesTest implements WithResteasyFixtures {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testCreatedDispatcherWithNullArgument() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*must not contain null.*"));

		createDispatcher((Class<?>) null);
	}

	@Test
	public void testCreatedDispatcherWithEmptyArgument() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'providers'.*empty.*"));

		createDispatcher();
	}

	@Test
	public void testCreatedDispatcherWithGenericProvider() {
		Dispatcher dispatcher = createDispatcher(Provider.class);

		assertThat(dispatcher).isNotNull();
	}

	@Test
	public void testCreatedDispatcherWithGenericProviderAnANull() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*must not contain null.*"));
		createDispatcher(Provider.class, (Class<?>) null);
	}

}