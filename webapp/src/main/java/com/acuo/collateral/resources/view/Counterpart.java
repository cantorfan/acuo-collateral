package com.acuo.collateral.resources.view;

import com.acuo.common.type.TypedString;
import org.joda.convert.FromString;

import java.util.function.Function;

public class Counterpart extends TypedString<Counterpart> {

    protected Counterpart(String name) {
        super(name);
    }

    private static final long serialVersionUID = -6770289992698244606L;

    @FromString
    public static Counterpart of(String name) {
        return new Counterpart(name);
    }

    public static Function<com.acuo.collateral.model.Counterpart, Counterpart> create = t -> {
        return of(t.getName());
    };

    public static Function<com.acuo.collateral.model.Counterpart, Counterpart> createDetailed = t -> {
        return create.apply(t);
    };
}