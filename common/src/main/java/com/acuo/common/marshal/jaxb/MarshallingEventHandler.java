package com.acuo.common.marshal.jaxb;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarshallingEventHandler implements ValidationEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MarshallingEventHandler.class);

	@Override
	public boolean handleEvent(ValidationEvent event) {
		LOG.warn("{}", event);

		if (event.getLinkedException() instanceof NumberFormatException)
			return false;

		return true;
	}

}