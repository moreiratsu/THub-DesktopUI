package com.alvaro.thub.desktop.utils;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.alvaro.thub.model.ProvinceDTO;



public class ProvinceToStringConverter extends ObjectToStringConverter {

	public ProvinceToStringConverter() {

	}
	
	@Override
	public String getPreferredStringForItem(Object item) {
		ProvinceDTO province = (ProvinceDTO) item;
		return province.getName();
	}

}