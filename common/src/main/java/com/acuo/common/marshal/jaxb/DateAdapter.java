package com.acuo.common.marshal.jaxb;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String marshal(Date value) throws Exception {
		if (value == null)
			return null;
		return dateFormat.format(value);
	}

	@Override
	public Date unmarshal(String value) throws Exception {
		if (value == null)
			return null;
		return dateFormat.parse(value);
	}
}