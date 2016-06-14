package com.acuo.common.marshal.jaxb;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DecimalAdapter extends XmlAdapter<String, Double> {

	@Override
	public Double unmarshal(String value) throws Exception {
		if (value == null)
			return null;
		return new BigDecimal(value).doubleValue();
	}

	@Override
	public String marshal(Double value) throws Exception {
		if (value == null)
			return null;
		return BigDecimal.valueOf(value).toPlainString();
	}

}
