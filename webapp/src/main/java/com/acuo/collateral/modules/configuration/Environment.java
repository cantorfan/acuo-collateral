package com.acuo.collateral.modules.configuration;

import org.joda.convert.FromString;

import com.acuo.common.type.TypedString;
import com.acuo.common.util.ArgChecker;

public class Environment extends TypedString<Environment> {

	private static final long serialVersionUID = 3995569058391895567L;

	public static final Environment DEVELOPMENT = Environment.of("dev");
	public static final Environment TEST = Environment.of("test");
	public static final Environment INTEGRATION = Environment.of("int");
	public static final Environment PRODUCTION = Environment.of("prod");

	protected Environment(String name) {
		super(name);
	}

	@FromString
	private static Environment of(String name) {
		ArgChecker.notNull(name, "name");
		return new Environment(name);
	}
}
