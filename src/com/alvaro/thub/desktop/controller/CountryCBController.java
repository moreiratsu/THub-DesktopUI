package com.alvaro.thub.desktop.controller;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;

/**
 * Controlador para el JComboBox de países.
 * 
 * Actualiza el JComboBox de provincias en función del país seleccionado.
 */

public class CountryCBController extends AbstractController {

	// Las variables de provincias y localidades se pasan para poder actualizar sus
	// modelos cuando se selecciona un país.
	// El servicio de provincias se utiliza para obtener las provincias asociadas al
	// país seleccionado.
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private String placeholderText;

	// Constructor que recibe los JComboBox de provincias y localidades, así como el
	// servicio de provincias para usarlos despues.
	public CountryCBController(JComboBox countryCB, JComboBox provinceCB, String placeholderText) {
		super(); // No vinculado a ninguna vista
		this.countryCB = countryCB;
		this.provinceCB = provinceCB;
		this.placeholderText = placeholderText;
	}

	@Override  
	public void doAction() {
		// Obtener el país
		Country selectedCountry = (Country) countryCB.getSelectedItem();
		ProvinceService provinceService = new ProvinceServiceImpl();

		if (selectedCountry != null && selectedCountry.getId() != null) {
			List<ProvinceDTO> provincias = provinceService.findByCountry(selectedCountry.getId());
			DefaultComboBoxModel<ProvinceDTO> model = new DefaultComboBoxModel<>();

			ProvinceDTO placeholder = new ProvinceDTO();
			placeholder.setId(null);
			placeholder.setName(placeholderText);
			model.addElement(placeholder);

			for (ProvinceDTO provincia : provincias) {
				model.addElement(provincia);
			}

			provinceCB.setModel(model);
		} else {
			provinceCB.setModel(new DefaultComboBoxModel<>());
		}
	}
}