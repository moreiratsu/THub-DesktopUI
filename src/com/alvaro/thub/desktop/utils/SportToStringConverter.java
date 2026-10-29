package com.alvaro.thub.desktop.utils;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.Sport;


public class SportToStringConverter extends ObjectToStringConverter {

	public SportToStringConverter() {

	}
	
	@Override
	public String getPreferredStringForItem(Object item) {
		Sport sport = (Sport) item;
		return sport.getName();
	}

}
