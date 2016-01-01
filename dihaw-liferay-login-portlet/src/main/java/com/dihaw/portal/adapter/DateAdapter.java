package com.dihaw.portal.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Long> {

	@Override
	public Long unmarshal(String v) throws Exception {
		return Long.parseLong(v);
	}

	@Override
	public String marshal(Long v) throws Exception {
		return v.toString();
	}

}
