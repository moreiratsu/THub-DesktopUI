package com.alvaro.thub.desktop.utils;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.alvaro.thub.model.LocalityDTO;

public class LocalityToStringConverter extends ObjectToStringConverter {

	public LocalityToStringConverter() {

	}
	
	@Override
	public String getPreferredStringForItem(Object item) {
		LocalityDTO locality = (LocalityDTO) item;
		return locality.getName();
	}

}