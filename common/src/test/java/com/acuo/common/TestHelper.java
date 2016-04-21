package com.acuo.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.convert.StringConvert;

/**
 * Test helper.
 * <p>
 * Provides additional classes to help with testing.
 */
public class TestHelper {

	/**
	 * Asserts that the object can be serialized and deserialized to an equal form.
	 * 
	 * @param base the object to be tested
	 */
	public static void assertSerialization(Object base)
	{
		assertNotNull(base);
		try
		{
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
			{
				try (ObjectOutputStream oos = new ObjectOutputStream(baos))
				{
					oos.writeObject(base);
					oos.close();
					try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()))
					{
						try (ObjectInputStream ois = new ObjectInputStream(bais))
						{
							assertEquals(ois.readObject(), base);
						}
					}
				}
			}
		} catch (IOException | ClassNotFoundException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Asserts that the object can be serialized and deserialized via a string using Joda-Convert.
	 * 
	 * @param cls the effective type
	 * @param base the object to be tested
	 */
	public static <T> void assertJodaConvert(Class<T> cls, Object base)
	{
		assertNotNull(base);
		StringConvert convert = StringConvert.create();
		String str = convert.convertToString(base);
		T result = convert.convertFromString(cls, str);
		assertEquals(result, base);
	}

	/**
	 * Asserts that a class is a well-defined utility class.
	 * <p>
	 * Must be final and with one zero-arg private constructor. All public methods must be static.
	 * 
	 * @param clazz the class to test
	 */
	public static void assertUtilityClass(Class<?> clazz)
	{
		assertNotNull("assertUtilityClass() called with null class", clazz);
		assertTrue("Utility class must be final", Modifier.isFinal(clazz.getModifiers()));
		assertEquals("Utility class must have one constructor", clazz.getDeclaredConstructors().length, 1);
		Constructor<?> con = clazz.getDeclaredConstructors()[0];
		assertEquals("Utility class must have zero-arg constructor", con.getParameterTypes().length, 0);
		assertTrue("Utility class must have private constructor", Modifier.isPrivate(con.getModifiers()));
		for (Method method : clazz.getDeclaredMethods())
		{
			if (Modifier.isPublic(method.getModifiers()))
			{
				assertTrue("Utility class public methods must be static", Modifier.isStatic(method.getModifiers()));
			}
		}
	}

	public static Matcher<String> matchesRegex(final String regex)
	{
		return new TypeSafeMatcher<String>()
		{
	
			@Override
			protected boolean matchesSafely(final String item)
			{
				return item.matches(regex);
			}
	
			@Override
			public void describeTo(Description description)
			{
				description.appendText("The exception message doesn't match the regexp ")
							.appendValue(regex);
			}
		};
	}
}