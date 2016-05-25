package com.acuo.collateral.model;

import org.joda.convert.FromString;

import com.acuo.common.type.TypedString;
import com.acuo.common.util.ArgChecker;

public class Environment extends TypedString<Environment> {

	private static final long serialVersionUID = 3995569058391895567L;

	protected Environment(String name) {
		super(name);
	}

	@FromString
	public static Environment of(String name) {
		ArgChecker.notNull(name, "name");
		return new Environment(name);
	}
}
