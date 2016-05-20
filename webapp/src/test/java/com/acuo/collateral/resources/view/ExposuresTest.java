package com.acuo.collateral.resources.view;

import static com.acuo.common.TestHelper.matchesRegex;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.acuo.collateral.model.ProductType;

import info.solidsoft.mockito.java8.api.WithBDDMockito;

public class ExposuresTest implements WithBDDMockito {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Spy
	public List<Exposure> listofExposures = new ArrayList<>();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		prepareMock(listofExposures);
	}

	@Test
	public void testConstructorWithNullList() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'exposures'.*null.*"));
		new Exposures(null);
	}

	// @Test
	// public void testClassifyByProductSet() {
	// Exposures exposures = new Exposures(listofExposures);
	// assertThat(exposures.classifyByProductSet()).containsOnlyKeys(ProductSet.ETD,
	// ProductSet.CLEARED)
	// .doesNotContainKeys(ProductSet.BILATERAL, ProductSet.UNKNOWN);
	// }

	@Test
	public void testClassifyByProductSetAndCount() {
		Exposures exposures = new Exposures(listofExposures);
		assertThat(exposures.classifyByProductSetAndCount())
				.contains(entry(ProductSet.ETD, 1), entry(ProductSet.CLEARED, 1))
				.doesNotContainKeys(ProductSet.BILATERAL, ProductSet.UNKNOWN);
	}

	@Test
	public void testToStringWithEmptyList() {
		Exposures exposures = new Exposures(Collections.emptyList());
		assertThat(exposures).extracting(Exposures::toString).containsOnly("");
	}

	// @Test
	// public void testToString() {
	// Exposures exposures = new Exposures(listofExposures);
	// assertThat(exposures.classifyByProductSet()).containsOnlyKeys(ProductSet.ETD,
	// ProductSet.CLEARED)
	// .doesNotContainKeys(ProductSet.BILATERAL, ProductSet.UNKNOWN);
	// assertThat(exposures).extracting(Exposures::toString).containsOnly("1,
	// 2");
	// }

	private void prepareMock(List<Exposure> exposures) {
		Exposure exposure = Mockito.mock(Exposure.class);
		given(exposure.getPositionId()).willReturn("1", "2");
		given(exposure.getProductType()).willReturn(ProductType.FUTURES, ProductType.IRS);

		exposures.add(exposure);
		exposures.add(exposure);
	}

}
