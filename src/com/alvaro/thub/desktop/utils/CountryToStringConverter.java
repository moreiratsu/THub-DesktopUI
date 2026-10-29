package com.alvaro.thub.desktop.utils;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.alvaro.thub.model.Country;

public class CountryToStringConverter extends ObjectToStringConverter {

	public CountryToStringConverter() {

	}
	
	@Override
	public String getPreferredStringForItem(Object item) {
		Country country = (Country) item;
		return country.getName();
	}

}
