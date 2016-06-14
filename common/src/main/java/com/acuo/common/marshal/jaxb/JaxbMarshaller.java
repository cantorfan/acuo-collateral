package com.acuo.common.marshal.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.xml.sax.InputSource;

import com.acuo.common.marshal.Marshaller;
import com.acuo.common.marshal.MarshallerSupport;
import com.acuo.common.util.ArgChecker;

/**
 * <a href="http://jaxb.java.net/">JAXB</a> {@link Marshaller}.
 *
 */
public class JaxbMarshaller extends MarshallerSupport {

	private final JaxbContextFactory factory;

	@Inject
	public JaxbMarshaller(final JaxbContextFactory factory) {
		this.factory = ArgChecker.notNull(factory, "factory");
	}

	@Override
	protected String doMarshal(final Object body) {
		StringWriter buff = new StringWriter();
		try {
			factory.marshaller().marshal(body, buff);
		} catch (JAXBException e) {
			throw new JAXBRuntimeException("Error marshalling [" + body + "] :" + e.getMessage(), e);
		}
		return buff.toString();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected <T> T doUnmarshal(final String marshaled, final Class<T> type) {
		StringReader buff = new StringReader(marshaled);
		try {
			return (T) factory.unmarshaller().unmarshal(new InputSource(buff));
		} catch (JAXBException e) {
			throw new JAXBRuntimeException(
					"Error unmarshalling [" + marshaled + "] against [" + type + "] :" + e.getMessage(), e);
		}
	}
}