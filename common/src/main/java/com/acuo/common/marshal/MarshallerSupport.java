package com.acuo.common.marshal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acuo.common.util.ArgChecker;
import com.google.inject.TypeLiteral;

/**
 * Support for {@link Marshaller} implementations.
 *
 * @since 1.0
 */
public abstract class MarshallerSupport implements Marshaller {

	private static final Logger log = LoggerFactory.getLogger(MarshallerSupport.class);

	@Override
	public String marshal(final Object body) throws Exception {
		ArgChecker.notNull(body, "body");

		log.trace("Marshalling: {}", body);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(body.getClass().getClassLoader());
		try {
			String value = doMarshal(body);
			log.trace("Value: {}", value);

			return value;
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
		}
	}

	protected abstract String doMarshal(Object body) throws Exception;

	@Override
	public <T> T unmarshal(final String marshaled, final Class<T> type) throws Exception {
		ArgChecker.notNull(marshaled, "marshaled");
		ArgChecker.notNull(type, "type");

		log.trace("Unmarshalling {}: {}", type, marshaled);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(type.getClassLoader());
		try {
			T value = doUnmarshal(marshaled, type);
			log.trace("Value: {}", value);

			return value;
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
		}
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public <T> T unmarshal(final String marshaled, final TypeLiteral<T> type) throws Exception {
		ArgChecker.notNull(type, "type");
		return (T) unmarshal(marshaled, type.getRawType());
	}

	protected abstract <T> T doUnmarshal(String marshaled, Class<T> type) throws Exception;
}