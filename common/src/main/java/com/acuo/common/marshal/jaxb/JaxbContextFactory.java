package com.acuo.common.marshal.jaxb;

import static java.lang.Boolean.TRUE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Provides access to JAXB {@link Marshaller} and {@link Unmarshaller}
 * instances.
 */
public interface JaxbContextFactory {

	JAXBContext getContext();

	default Marshaller marshaller() {
		try {
			Marshaller marshaller = getContext().createMarshaller();
			marshaller.setEventHandler(new MarshallingEventHandler());
			marshaller.setProperty(JAXB_FORMATTED_OUTPUT, TRUE);
			return marshaller;
		} catch (JAXBException e) {
			throw new JAXBRuntimeException("Error creating JAXB Marshaller: " + e.getMessage(), e);
		}
	}

	default Unmarshaller unmarshaller() {
		try {
			Unmarshaller unmarshaller = getContext().createUnmarshaller();
			unmarshaller.setEventHandler(new MarshallingEventHandler());
			return unmarshaller;
		} catch (JAXBException e) {
			throw new JAXBRuntimeException("Error creating JAXB Unmarshaller: " + e.getMessage(), e);
		}
	}
}