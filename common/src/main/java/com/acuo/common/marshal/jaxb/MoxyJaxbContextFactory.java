package com.acuo.common.marshal.jaxb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;

import com.acuo.common.util.ArgChecker;

public class MoxyJaxbContextFactory implements JaxbContextFactory {

	private final JAXBContext context;

	@Inject
	MoxyJaxbContextFactory(@JaxbTypes List<Class<?>> types) {
		ArgChecker.notEmpty(types, "types");
		try {
			List<Class<?>> classes = addMoxyClasses(types);
			this.context = JAXBContextFactory.createContext(toArray(classes), Collections.EMPTY_MAP);
		} catch (JAXBException e) {
			throw new JAXBRuntimeException("Error creating Moxy/JAXB Context: " + e.getMessage(), e);
		}
	}

	@Override
	public JAXBContext getContext() {
		return context;
	}

	private List<Class<?>> addMoxyClasses(List<Class<?>> types) {
		List<Class<?>> classes = new ArrayList<>(types.size() + 1);
		classes.addAll(types);
		classes.add(ObjectFactory.class);
		return classes;
	}

	private Class<?>[] toArray(List<Class<?>> classes) {
		return classes.toArray(new Class<?>[classes.size()]);
	}
}
