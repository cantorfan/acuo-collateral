package com.acuo.collateral.resources.view;

import com.acuo.collateral.model.ProductType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.acuo.collateral.model.ProductType.*;

public enum ProductSet {

    ETD(FUTURES), CLEARED(CDS, CDX, IRS, NDF), BILATERAL(BILTERAL), UNKNOWN;

    private final Set<ProductType> types = new HashSet<>();

    private ProductSet(ProductType... productTypes) {
        types.addAll(Arrays.stream(productTypes).collect(Collectors.toSet()));
    }

    private final Predicate<ProductType> inRange = i -> types.contains(i);

    public static final Function<Exposure, ProductSet> resolveProductCategory = m -> ETD.inRange
            .test(m.getProductType()) ? ETD
            : CLEARED.inRange.test(m.getProductType()) ? CLEARED
            : BILATERAL.inRange.test(m.getProductType()) ? BILATERAL : UNKNOWN;
}