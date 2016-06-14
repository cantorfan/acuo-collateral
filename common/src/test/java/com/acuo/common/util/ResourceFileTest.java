package com.acuo.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

public class ResourceFileTest {

	@Rule
	public ResourceFile res = new ResourceFile("/log4j.xml");

	@Test
	public void testResourceFileExist() throws Exception {
		assertTrue(res.getContent().length() > 0);
		assertTrue(res.getFile().exists());
	}

}