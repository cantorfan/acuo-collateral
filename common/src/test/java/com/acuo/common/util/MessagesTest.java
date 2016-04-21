/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.acuo.common.util;

import static com.acuo.common.TestHelper.assertUtilityClass;
import static org.junit.Assert.assertEquals;

import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

/**
 * Test Messages.
 */
@RunWith(DataProviderRunner.class)
public class MessagesTest
{

	@DataProvider
	public static Object[][] formatMessageSingle()
	{
		return new Object[][]
		{
			// null template
			{null, null, "", " - [null]"},
			{null, "", "", " - []"},
			// null in array
			{"", null, "", " - [null]"},
			{"{}", null, "null", ""},
			{"{}{}", null, "null{}", ""},
			{"{} and {}", null, "null and {}", ""},
			// empty string in array
			{"", "", "", " - []"},
			{"{}", "", "", ""},
			{"{}{}", "", "{}", ""},
			{"{} and {}", "", " and {}", ""},
			// main tests
			{"{}", 67, "67", ""},
			{"{}{}", 67, "67{}", ""},
			{"{} and {}", 67, "67 and {}", ""},};
	}

	@Test
	@UseDataProvider("formatMessageSingle")
	public void test_formatMessageSingle(String template, Object arg, String expMain, String expExcess)
	{
		assertEquals(Messages.format(template, arg), expMain + expExcess);
	}

	@Test
	@UseDataProvider("formatMessageSingle")
	public void test_formatMessageSingle_prefix(String template, Object arg, String expMain, String expExcess)
	{
		assertEquals(Messages.format("::" + Objects.toString(template, ""), arg), "::" + expMain + expExcess);
	}

	@Test
	@UseDataProvider("formatMessageSingle")
	public void test_formatMessageSingle_suffix(String template, Object arg, String expMain, String expExcess)
	{
		assertEquals(Messages.format(Objects.toString(template, "") + "@@", arg), expMain + "@@" + expExcess);
	}

	@Test
	@UseDataProvider("formatMessageSingle")
	public void test_formatMessageSingle_prefixSuffix(String template, Object arg, String expMain, String expExcess)
	{
		assertEquals(	Messages.format("::" + Objects.toString(template, "") + "@@", arg),
						"::" + expMain + "@@" + expExcess);
	}

	@DataProvider
	public static Object[][] formatMessage()
	{
		return new Object[][]
		{
			// null template
			{null, null, "", ""},
			{null, new Object[]
				{}, "", ""},
			{null, new Object[]
				{null}, "", " - [null]"},
			{null, new Object[]
				{67}, "", " - [67]"},
			// null array
			{"", null, "", ""},
			{"{}", null, "{}", ""},
			{"{}{}", null, "{}{}", ""},
			{"{} and {}", null, "{} and {}", ""},
			// null in array
			{"", new Object[]
				{null}, "", " - [null]"},
			{"{}", new Object[]
				{null}, "null", ""},
			{"{}{}", new Object[]
				{null}, "null{}", ""},
			{"{} and {}", new Object[]
				{null}, "null and {}", ""},
			// main tests
			{"", new Object[]
				{}, "", ""},
			{"{}", null, "{}", ""},
			{"{}", new Object[]
				{}, "{}", ""},
			{"{}", new Object[]
				{67}, "67", ""},
			{"{}", new Object[]
				{67, 78}, "67", " - [78]"},
			{"{}", new Object[]
				{67, 78, 89}, "67", " - [78, 89]"},
			{"{}", new Object[]
				{67, 78, 89, 90}, "67", " - [78, 89, 90]"},
			{"{}{}", null, "{}{}", ""},
			{"{}{}", new Object[]
				{}, "{}{}", ""},
			{"{}{}", new Object[]
				{67}, "67{}", ""},
			{"{}{}", new Object[]
				{67, 78}, "6778", ""},
			{"{}{}", new Object[]
				{67, 78, 89}, "6778", " - [89]"},
			{"{}{}", new Object[]
				{67, 78, 89, 90}, "6778", " - [89, 90]"},
			{"{} and {}", null, "{} and {}", ""},
			{"{} and {}", new Object[]
				{}, "{} and {}", ""},
			{"{} and {}", new Object[]
				{67}, "67 and {}", ""},
			{"{} and {}", new Object[]
				{67, 78}, "67 and 78", ""},
			{"{} and {}", new Object[]
				{67, 78, 89}, "67 and 78", " - [89]"},
			{"{} and {}", new Object[]
				{67, 78, 89, 90}, "67 and 78", " - [89, 90]"},
			{"{}, {} and {}", new Object[]
				{}, "{}, {} and {}", ""},
			{"{}, {} and {}", new Object[]
				{67}, "67, {} and {}", ""},
			{"{}, {} and {}", new Object[]
				{67, 78}, "67, 78 and {}", ""},
			{"{}, {} and {}", new Object[]
				{67, 78, 89}, "67, 78 and 89", ""},
			{"{}, {} and {}", new Object[]
				{67, 78, 89, 90}, "67, 78 and 89", " - [90]"},
			{"ABC", new Object[]
				{}, "ABC", ""},
			{"Message {}, {}, {}", new Object[]
				{"A", 2, 3.}, "Message A, 2, 3.0", ""},
			{"Message {}, {} blah", new Object[]
				{"A", 2, 3.}, "Message A, 2 blah", " - [3.0]"},
			{"Message {}, {}", new Object[]
				{"A", 2, 3., true}, "Message A, 2", " - [3.0, true]"},
			{"Message {}, {}, {}, {} blah", new Object[]
				{"A", 2, 3.}, "Message A, 2, 3.0, {} blah", ""},};
	}

	@Test
	@UseDataProvider("formatMessage")
	public void test_formatMessage(String template, Object[] args, String expMain, String expExcess)
	{
		assertEquals(Messages.format(template, args), expMain + expExcess);
	}

	@Test
	@UseDataProvider("formatMessage")
	public void test_formatMessage_prefix(String template, Object[] args, String expMain, String expExcess)
	{
		assertEquals(Messages.format("::" + Objects.toString(template, ""), args), "::" + expMain + expExcess);
	}

	@Test
	@UseDataProvider("formatMessage")
	public void test_formatMessage_suffix(String template, Object[] args, String expMain, String expExcess)
	{
		assertEquals(Messages.format(Objects.toString(template, "") + "@@", args), expMain + "@@" + expExcess);
	}

	@Test
	@UseDataProvider("formatMessage")
	public void test_formatMessage_prefixSuffix(String template, Object[] args, String expMain, String expExcess)
	{
		assertEquals(	Messages.format("::" + Objects.toString(template, "") + "@@", args),
						"::" + expMain + "@@" + expExcess);
	}

	// -------------------------------------------------------------------------
	public void test_validUtilityClass()
	{
		assertUtilityClass(Messages.class);
	}
}
