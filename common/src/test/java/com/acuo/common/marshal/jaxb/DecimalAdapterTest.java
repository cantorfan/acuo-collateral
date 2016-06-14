package com.acuo.common.marshal.jaxb;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DecimalAdapterTest {

	private DecimalAdapter adapter = new DecimalAdapter();

	@Test
	public void test() {
		asList(0.0002333333333, 0.0029142818929).stream().forEach(d -> marshal(d));
	}

	private void marshal(Double d) {
		try {
			String str = adapter.marshal(d);
			Double result = adapter.unmarshal(str);

			assertThat(d, is(equalTo(result)));
		} catch (Exception exception) {
		}
	}
}
