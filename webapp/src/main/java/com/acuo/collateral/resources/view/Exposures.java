package com.acuo.collateral.resources.view;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.resources.CounterpartResource.Counterpart;
import com.acuo.common.util.ArgChecker;

public class Exposures {

	private final List<Exposure> exposures;

	public Exposures(List<Exposure> exposures) {
		ArgChecker.notNull(exposures, "exposures");
		this.exposures = exposures;
	}

	public Map<Counterpart, Map<ProductType, Long>> classifyByCounterPartAndProductType() {

		return exposures.stream()
				.collect(groupingBy(Exposure::getCounterpart, groupingBy(Exposure::getProductType, counting())));
	}

	public Map<Counterpart, Map<ProductSet, Long>> classifyByCounterPartAndProductSet() {
		return exposures.stream().collect(
				groupingBy(Exposure::getCounterpart, groupingBy(ProductSet.resolveProductCategory, counting())));
	}

	public Map<ProductSet, Integer> classifyByProductSetAndCount() {
		Map<ProductSet, Integer> map = classifyByProductSet(exposures).entrySet().stream()
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue().size()));
		return map;
	}

	@Override
	public String toString() {
		return exposures.stream().map(e -> e.getPositionId()).collect(joining(", "));
	}

	private static Map<ProductSet, List<Exposure>> classifyByProductSet(List<Exposure> exposures) {
		return classifyExposures(exposures, ProductSet.resolveProductCategory);
	}

	private static <T> Map<T, List<Exposure>> classifyExposures(List<Exposure> exposures,
			Function<Exposure, T> classifier) {
		return exposures.stream().collect(groupingBy(classifier));
	}
}