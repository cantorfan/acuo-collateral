/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.acuo.common.util;

import static com.acuo.common.TestHelper.assertUtilityClass;
import static com.acuo.common.TestHelper.matchesRegex;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableSortedMap;

/**
 * Test ArgChecker.
 */
public class ArgCheckerTest {
	private static double EPSILON = 0.0d;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void test_isTrue_simple_ok() {
		ArgChecker.isTrue(true);
	}

	@Test
	public void test_isTrue_simple_false() {
		expectedException.expect(IllegalArgumentException.class);
		ArgChecker.isTrue(false);
	}

	public void test_isTrue_ok() {
		ArgChecker.isTrue(true, "Message");
	}

	@Test
	public void test_isTrue_false() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Message");
		ArgChecker.isTrue(false, "Message");
	}

	public void test_isTrue_ok_args() {
		ArgChecker.isTrue(true, "Message {} {} {}", "A", 2, 3d);
	}

	@Test
	public void test_isTrue_false_args() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Message A 2 3.0");
		ArgChecker.isTrue(false, "Message {} {} {}", "A", 2, 3d);
	}

	public void test_isTrue_ok_longArg() {
		ArgChecker.isTrue(true, "Message {}", 3L);
	}

	@Test
	public void test_isTrue_false_longArg() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Message 3");
		ArgChecker.isTrue(false, "Message {}", 3L);
	}

	public void test_isTrue_ok_doubleArg() {
		ArgChecker.isTrue(true, "Message {}", 3d);
	}

	@Test
	public void test_isTrue_false_doubleArg() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Message 3.0");
		ArgChecker.isTrue(false, "Message {}", 3d);
	}

	public void test_isFalse_ok() {
		ArgChecker.isFalse(false, "Message");
	}

	@Test
	public void test_isFalse_true() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Message");
		ArgChecker.isFalse(true, "Message");
	}

	public void test_isFalse_ok_args() {
		ArgChecker.isFalse(false, "Message {} {} {}", "A", 2., 3, true);
	}

	@Test
	public void test_isFalse_ok_args_true() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Message A 2 3.0");
		ArgChecker.isFalse(true, "Message {} {} {}", "A", 2, 3.);
	}

	public void test_notNull_ok() {
		assertEquals(ArgChecker.notNull("OG", "name"), "OG");
		assertEquals(ArgChecker.notNull(1, "name"), Integer.valueOf(1));
	}

	@Test
	public void test_notNull_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notNull(null, "name");
	}

	public void test_notNullItem_noText_ok() {
		assertEquals(ArgChecker.notNullItem("OG"), "OG");
		assertEquals(ArgChecker.notNullItem(1), Integer.valueOf(1));
	}

	@Test
	public void test_notNullItem_noText_null() {
		expectedException.expect(IllegalArgumentException.class);
		ArgChecker.notNullItem(null);
	}

	public void test_matches_String_ok() {
		assertEquals(ArgChecker.matches(Pattern.compile("[A-Z]+"), "OG", "name"), "OG");
	}

	@Test
	public void test_matches_String_nullPattern() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'pattern'.*null.*"));
		ArgChecker.matches(null, "", "name");
	}

	@Test
	public void test_matches_String_nullString() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.matches(Pattern.compile("[A-Z]+"), null, "name");
	}

	@Test
	public void test_matches_String_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*"));
		ArgChecker.matches(Pattern.compile("[A-Z]+"), "", "name");
	}

	@Test
	public void test_matches_String_noMatch() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*"));
		ArgChecker.matches(Pattern.compile("[A-Z]+"), "123", "name");
	}

	public void test_notBlank_String_ok() {
		assertEquals(ArgChecker.notBlank("OG", "name"), "OG");
	}

	public void test_notBlank_String_ok_notTrimmed() {
		assertEquals(ArgChecker.notBlank(" OG ", "name"), " OG ");
	}

	@Test
	public void test_notBlank_String_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notBlank(null, "name");
	}

	@Test
	public void test_notBlank_String_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*blank.*"));
		ArgChecker.notBlank("", "name");
	}

	@Test
	public void test_notBlank_String_spaces() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*blank"));
		ArgChecker.notBlank("  ", "name");
	}

	public void test_notEmpty_String_ok() {
		assertEquals(ArgChecker.notEmpty("OG", "name"), "OG");
		assertEquals(ArgChecker.notEmpty(" ", "name"), " ");
	}

	@Test
	public void test_notEmpty_String_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((String) null, "name");
	}

	@Test
	public void test_notEmpty_String_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*empty.*"));
		ArgChecker.notEmpty("", "name");
	}

	public void test_notEmpty_Array_ok() {
		Object[] expected = new Object[] { "Element" };
		Object[] result = ArgChecker.notEmpty(expected, "name");
		assertArrayEquals(result, expected);
	}

	@Test
	public void test_notEmpty_Array_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((Object[]) null, "name");
	}

	@Test
	public void test_notEmpty_Array_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*array.*'name'.*empty.*"));
		ArgChecker.notEmpty(new Object[] {}, "name");
	}

	@Test
	public void test_notEmpty_2DArray_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((Object[][]) null, "name");
	}

	@Test
	public void test_notEmpty_2DArray_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*array.*'name'.*empty.*"));
		ArgChecker.notEmpty(new Object[0][0], "name");
	}

	public void test_notEmpty_intArray_ok() {
		int[] expected = new int[] { 6 };
		int[] result = ArgChecker.notEmpty(expected, "name");
		assertEquals(result, expected);
	}

	@Test
	public void test_notEmpty_intArray_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((int[]) null, "name");
	}

	@Test
	public void test_notEmpty_intArray_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*empty.*"));
		ArgChecker.notEmpty(new int[0], "name");
	}

	public void test_notEmpty_longArray_ok() {
		long[] expected = new long[] { 6L };
		long[] result = ArgChecker.notEmpty(expected, "name");
		assertEquals(result, expected);
	}

	@Test
	public void test_notEmpty_longArray_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((long[]) null, "name");
	}

	@Test
	public void test_notEmpty_longArray_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*array.*'name'.*empty.*"));
		ArgChecker.notEmpty(new long[0], "name");
	}

	public void test_notEmpty_doubleArray_ok() {
		double[] expected = new double[] { 6.0d };
		double[] result = ArgChecker.notEmpty(expected, "name");
		assertEquals(result, expected);
	}

	@Test
	public void test_notEmpty_doubleArray_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((double[]) null, "name");
	}

	@Test
	public void test_notEmpty_doubleArray_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*array.*'name'.*empty.*"));
		ArgChecker.notEmpty(new double[0], "name");
	}

	public void test_notEmpty_Iterable_ok() {
		Iterable<String> expected = Arrays.asList("Element");
		Iterable<String> result = ArgChecker.notEmpty(expected, "name");
		assertEquals(result, expected);
	}

	@Test
	public void test_notEmpty_Iterable_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((Iterable<?>) null, "name");
	}

	@Test
	public void test_notEmpty_Iterable_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*iterable.*'name'.*empty.*"));
		ArgChecker.notEmpty((Iterable<?>) Collections.emptyList(), "name");
	}

	public void test_notEmpty_Collection_ok() {
		List<String> expected = Arrays.asList("Element");
		List<String> result = ArgChecker.notEmpty(expected, "name");
		assertEquals(result, expected);
	}

	@Test
	public void test_notEmpty_Collection_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((Collection<?>) null, "name");
	}

	@Test
	public void test_notEmpty_Collection_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*collection.*'name'.*empty.*"));
		ArgChecker.notEmpty(Collections.emptyList(), "name");
	}

	public void test_notEmpty_Map_ok() {
		SortedMap<String, String> expected = ImmutableSortedMap.of("Element", "Element");
		SortedMap<String, String> result = ArgChecker.notEmpty(expected, "name");
		assertEquals(result, expected);
	}

	@Test
	public void test_notEmpty_Map_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.notEmpty((Map<?, ?>) null, "name");
	}

	@Test
	public void test_notEmpty_Map_empty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*map.*'name'.*empty.*"));
		ArgChecker.notEmpty(Collections.emptyMap(), "name");
	}

	public void test_noNulls_Array_ok() {
		String[] expected = new String[] { "Element" };
		String[] result = ArgChecker.noNulls(expected, "name");
		assertArrayEquals(result, expected);
	}

	public void test_noNulls_Array_ok_empty() {
		Object[] array = new Object[] {};
		ArgChecker.noNulls(array, "name");
	}

	@Test
	public void test_noNulls_Array_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.noNulls((Object[]) null, "name");
	}

	@Test
	public void test_noNulls_Array_nullElement() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*array.*'name'.*null.*"));
		ArgChecker.noNulls(new Object[] { null }, "name");
	}

	public void test_noNulls_Iterable_ok() {
		List<String> expected = Arrays.asList("Element");
		List<String> result = ArgChecker.noNulls(expected, "name");
		assertEquals(result, expected);
	}

	public void test_noNulls_Iterable_ok_empty() {
		Iterable<?> coll = Arrays.asList();
		ArgChecker.noNulls(coll, "name");
	}

	@Test
	public void test_noNulls_Iterable_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.noNulls((Iterable<?>) null, "name");
	}

	@Test
	public void test_noNulls_Iterable_nullElement() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.noNulls(Arrays.asList((Object) null), "name");
	}

	public void test_noNulls_Map_ok() {
		ImmutableSortedMap<String, String> expected = ImmutableSortedMap.of("A", "B");
		ImmutableSortedMap<String, String> result = ArgChecker.noNulls(expected, "name");
		assertEquals(result, expected);
	}

	public void test_noNulls_Map_ok_empty() {
		Map<Object, Object> map = new HashMap<>();
		ArgChecker.noNulls(map, "name");
	}

	@Test
	public void test_noNulls_Map_null() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*null.*"));
		ArgChecker.noNulls((Map<Object, Object>) null, "name");
	}

	@Test
	public void test_noNulls_Map_nullKey() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*map.*'name'.*null.*"));
		Map<Object, Object> map = new HashMap<>();
		map.put("A", "B");
		map.put(null, "Z");
		ArgChecker.noNulls(map, "name");
	}

	@Test
	public void test_noNulls_Map_nullValue() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*map.*'name'.*null.*"));
		Map<Object, Object> map = new HashMap<>();
		map.put("A", "B");
		map.put("Z", null);
		ArgChecker.noNulls(map, "name");
	}

	public void test_notNegative_int_ok() {
		assertEquals(ArgChecker.notNegative(0, "name"), 0);
		assertEquals(ArgChecker.notNegative(1, "name"), 1);
	}

	@Test
	public void test_notNegative_int_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*"));
		ArgChecker.notNegative(-1, "name");
	}

	public void test_notNegative_long_ok() {
		assertEquals(ArgChecker.notNegative(0L, "name"), 0L);
		assertEquals(ArgChecker.notNegative(1L, "name"), 1L);
	}

	@Test
	public void test_notNegative_long_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*"));
		ArgChecker.notNegative(-1L, "name");
	}

	public void test_notNegative_double_ok() {
		assertEquals(ArgChecker.notNegative(0d, "name"), 0d, 0.0001d);
		assertEquals(ArgChecker.notNegative(1d, "name"), 1d, 0.0001d);
	}

	@Test
	public void test_notNegative_double_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*"));
		ArgChecker.notNegative(-1.0d, "name");
	}

	public void test_notNegativeOrZero_int_ok() {
		assertEquals(ArgChecker.notNegativeOrZero(1, "name"), 1);
	}

	@Test
	public void test_notNegativeOrZero_int_zero() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*zero.*"));
		ArgChecker.notNegativeOrZero(0, "name");
	}

	@Test
	public void test_notNegativeOrZero_int_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*zero.*"));
		ArgChecker.notNegativeOrZero(-1, "name");
	}

	public void test_notNegativeOrZero_long_ok() {
		assertEquals(ArgChecker.notNegativeOrZero(1L, "name"), 1);
	}

	@Test
	public void test_notNegativeOrZero_long_zero() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*zero.*"));
		ArgChecker.notNegativeOrZero(0L, "name");
	}

	@Test
	public void test_notNegativeOrZero_long_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*zero.*"));
		ArgChecker.notNegativeOrZero(-1L, "name");
	}

	public void test_notNegativeOrZero_double_ok() {
		assertEquals(ArgChecker.notNegativeOrZero(1d, "name"), 1d, 0.0001d);
	}

	@Test
	public void test_notNegativeOrZero_double_zero() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*zero.*"));
		ArgChecker.notNegativeOrZero(0.0d, "name");
	}

	@Test
	public void test_notNegativeOrZero_double_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*negative.*zero.*"));
		ArgChecker.notNegativeOrZero(-1.0d, "name");
	}

	public void test_notNegativeOrZero_double_eps_ok() {
		assertEquals(ArgChecker.notNegativeOrZero(1d, 0.0001d, "name"), 1d, 0.0001d);
		assertEquals(ArgChecker.notNegativeOrZero(0.1d, 0.0001d, "name"), 0.1d, 0.0001d);
	}

	@Test
	public void test_notNegativeOrZero_double_eps_zero() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*zero.*"));
		ArgChecker.notNegativeOrZero(0.0000001d, 0.0001d, "name");
	}

	@Test
	public void test_notNegativeOrZero_double_eps_negative() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*greater.*zero.*"));
		ArgChecker.notNegativeOrZero(-1.0d, 0.0001d, "name");
	}

	public void test_notZero_double_ok() {
		assertEquals(ArgChecker.notZero(1d, 0.1d, "name"), 1d, 0.0001d);
	}

	@Test
	public void test_notZero_double_zero() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'name'.*zero.*"));
		ArgChecker.notZero(0d, 0.1d, "name");
	}

	public void test_notZero_double_negative() {
		ArgChecker.notZero(-1d, 0.1d, "name");
	}

	public void test_inRange() {
		double low = 0d;
		double mid = 0.5d;
		double high = 1d;
		double small = 0.00000000001d;
		assertEquals(ArgChecker.inRange(mid, low, high, "name"), mid, EPSILON);
		assertEquals(ArgChecker.inRange(low, low, high, "name"), low, EPSILON);
		assertEquals(ArgChecker.inRange(high - small, low, high, "name"), high - small, EPSILON);
		assertEquals(ArgChecker.inRangeInclusive(mid, low, high, "name"), mid, EPSILON);
		assertEquals(ArgChecker.inRangeInclusive(low, low, high, "name"), low, EPSILON);
		assertEquals(ArgChecker.inRangeInclusive(high, low, high, "name"), high, EPSILON);
		assertEquals(ArgChecker.inRangeExclusive(mid, low, high, "name"), mid, EPSILON);
		assertEquals(ArgChecker.inRangeExclusive(small, low, high, "name"), small, EPSILON);
		assertEquals(ArgChecker.inRangeExclusive(high - small, low, high, "name"), high - small, EPSILON);
	}

	@Test
	public void testNotEmptyLongArray() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*array.*'name'.*empty.*"));
		ArgChecker.notEmpty(new double[0], "name");
	}

	public void test_inOrderNotEqual_true() {
		LocalDate a = LocalDate.of(2011, 7, 2);
		LocalDate b = LocalDate.of(2011, 7, 3);
		ArgChecker.inOrderNotEqual(a, b, "a", "b");
	}

	@Test
	public void test_inOrderNotEqual_false_invalidOrder() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*a.* [<] .*b.*"));
		LocalDate a = LocalDate.of(2011, 7, 2);
		LocalDate b = LocalDate.of(2011, 7, 3);
		ArgChecker.inOrderNotEqual(b, a, "a", "b");
	}

	@Test
	public void test_inOrderNotEqual_false_equal() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*a.* [<] .*b.*"));
		LocalDate a = LocalDate.of(2011, 7, 3);
		ArgChecker.inOrderNotEqual(a, a, "a", "b");
	}

	public void test_inOrderOrEqual_true() {
		LocalDate a = LocalDate.of(2011, 7, 2);
		LocalDate b = LocalDate.of(2011, 7, 3);
		ArgChecker.inOrderOrEqual(a, b, "a", "b");
		ArgChecker.inOrderOrEqual(a, a, "a", "b");
		ArgChecker.inOrderOrEqual(b, b, "a", "b");
	}

	@Test
	public void test_inOrderOrEqual_false() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*a.* [<][=] .*b.*"));
		LocalDate a = LocalDate.of(2011, 7, 3);
		LocalDate b = LocalDate.of(2011, 7, 2);
		ArgChecker.inOrderOrEqual(a, b, "a", "b");
	}

	public void test_validUtilityClass() {
		assertUtilityClass(ArgChecker.class);
	}
}
