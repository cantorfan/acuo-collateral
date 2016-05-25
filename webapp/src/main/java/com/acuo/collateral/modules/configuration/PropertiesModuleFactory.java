package com.acuo.collateral.modules.configuration;

import com.acuo.collateral.model.Environment;
import com.smokejumperit.guice.properties.PropertiesModule;

public interface PropertiesModuleFactory {

	PropertiesModule create(Environment environment);
}
