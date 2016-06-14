package com.acuo.common.marshal;

import com.google.inject.TypeLiteral;

/**
 * Abstraction of marshaling strategy.
 */
public interface Marshaller {
	String marshal(Object value) throws Exception;

	<T> T unmarshal(String marshaled, Class<T> type) throws Exception;

	<T> T unmarshal(String marshaled, TypeLiteral<T> type) throws Exception;
}