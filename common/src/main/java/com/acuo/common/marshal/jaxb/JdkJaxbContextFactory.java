package com.acuo.common.marshal.jaxb;

import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Default {@link JaxbContextFactory} implementation.
 */
public class JdkJaxbContextFactory implements JaxbContextFactory {

	private final JAXBContext context;

	@Inject
	JdkJaxbContextFactory(@JaxbTypes List<Class<?>> types) {
		try {
			this.context = JAXBContext.newInstance(types.toArray(new Class<?>[types.size()]));
		} catch (JAXBException e) {
			throw new JAXBRuntimeException("Error creating JAXB Context: " + e.getMessage(), e);
		}
	}

	@Override
	public JAXBContext getContext() {
		return context;
	}

}