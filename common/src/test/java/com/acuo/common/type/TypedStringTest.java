package com.acuo.common.type;

import static com.acuo.common.TestHelper.assertJodaConvert;
import static com.acuo.common.TestHelper.assertSerialization;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test {@link TypedString}.
 */
public class TypedStringTest
{

	@Test
	public void test_of()
	{
		SampleType test = SampleType.of("A");
		assertEquals(test.toString(), "A");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_of_invalid()
	{
		SampleType.of(null);
		SampleType.of("");
	}

	@Test
	public void test_of_validated()
	{
		SampleValidatedType test = SampleValidatedType.of("ABC");
		assertEquals(test.toString(), "ABC");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_of_validated_invalid()
	{
		SampleValidatedType.of(null);
		SampleValidatedType.of("ABc");
	}

	@Test
	public void test_equalsHashCode()
	{
		SampleType a1 = SampleType.of("A");
		SampleType a2 = SampleType.of("A");
		SampleType b = SampleType.of("B");
		assertEquals(a1.equals(a1), true);
		assertEquals(a1.equals(a2), true);
		assertEquals(a1.equals(b), false);
		assertEquals(a1.equals(null), false);
		assertEquals(a1.equals("A"), false);
		assertEquals(a1.hashCode(), a2.hashCode());
	}

	@Test
	public void test_compareTo()
	{
		SampleType a = SampleType.of("A");
		SampleType b = SampleType.of("B");
		SampleType c = SampleType.of("C");
		assertEquals(a.compareTo(a) == 0, true);
		assertEquals(a.compareTo(b) < 0, true);
		assertEquals(a.compareTo(c) < 0, true);
		assertEquals(b.compareTo(a) > 0, true);
		assertEquals(b.compareTo(b) == 0, true);
		assertEquals(b.compareTo(c) < 0, true);
		assertEquals(c.compareTo(a) > 0, true);
		assertEquals(c.compareTo(b) > 0, true);
		assertEquals(c.compareTo(c) == 0, true);
	}

	@Test
	public void test_serialization()
	{
		assertSerialization(SampleType.of("A"));
	}

	@Test
	public void test_jodaConvert()
	{
		assertJodaConvert(SampleType.class, SampleType.of("A"));
	}
}
