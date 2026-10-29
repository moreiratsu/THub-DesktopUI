package com.alvaro.thub.desktop.views;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.alvaro.thub.desktop.utils.CountryToStringConverter;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.service.CountryService;

import java.awt.BorderLayout;
import java.util.List;

import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.TeamServiceImpl;

public class SwingXTestView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private CountryService countryService = null;
	private JXComboBox paisCB = null;

	public SwingXTestView() {
		initialize();
		postInitialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);

		paisCB = new JXComboBox();
		centerPanel.add(paisCB);
	}

	private void initServices() {
		// Inicializar el servicio
		countryService = new CountryServiceImpl();

		// Obtener todos los países desde el servicio
		List<Country> countries = countryService.findAll();

		// Crear el modelo y cargar los países
		DefaultComboBoxModel<Country> model = new DefaultComboBoxModel<>();
		for (Country country : countries) {
			model.addElement(country);
		}

		// Asignar el modelo al combo box
		paisCB.setModel(model);
		
		// Configurar el renderer para mostrar el nombre del país
		paisCB.setRenderer(new CountryCBRenderer());
	}
	private void postInitialize() {
		initServices();
		AutoCompleteDecorator.decorate(paisCB, new CountryToStringConverter());
	}
}
