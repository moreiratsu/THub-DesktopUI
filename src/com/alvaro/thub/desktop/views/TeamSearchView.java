package com.alvaro.thub.desktop.views;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.alvaro.thub.dao.criteria.TeamCriteria;
import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.controller.CancelController;
import com.alvaro.thub.desktop.controller.Controller;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.EventSearchController;
import com.alvaro.thub.desktop.controller.EventTableMouseController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.TeamSearchController;
import com.alvaro.thub.desktop.controller.TeamSetEditableController;
import com.alvaro.thub.desktop.controller.TeamTableMouseController;
import com.alvaro.thub.desktop.controller.TeamUpdateController;
import com.alvaro.thub.desktop.utils.ConversionUtils;
import com.alvaro.thub.desktop.utils.CountryToStringConverter;
import com.alvaro.thub.desktop.utils.LocalityToStringConverter;
import com.alvaro.thub.desktop.utils.ProvinceToStringConverter;
import com.alvaro.thub.desktop.utils.SportToStringConverter;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Sport;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.SportService;
import com.alvaro.thub.service.impl.TeamServiceImpl;
import com.alvaro.thub.utils.Results;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.SportServiceImpl;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.SportCBRenderer;
import com.alvaro.thub.desktop.views.renderer.TeamTableRenderer;
import com.alvaro.thub.desktop.views.tableModel.EventButtonEditor;
import com.alvaro.thub.desktop.views.tableModel.TeamButtonEditor;
import com.alvaro.thub.desktop.views.tableModel.TeamTableModel;

import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

/**
 * VISTA PARA LA BÚSQUEDA DE EQUIPOS deportivos.
 * Permite filtrar por código, nombre, siglas, país, provincia, localidad y deporte.
 * Muestra los resultados en una tabla con paginación.
 */

public class TeamSearchView extends AbstractView {

	private TeamService teamService = null;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private SportService sportService;

	private JTextField nameTF;
	private JTextField initialismTF;
	private JTextField idTF;

	// Declaración de los combobox como variables de clase
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox sportCB;

	// Declaración de la tabla como variable de clase
	private JTable resultsTable;
	private Results<TeamDTO> model = null;
	private JLabel totalResultadosLabel;
	private JButton searchButton;


	public TeamSearchView() {
		initServices();
		initialize();
		postInitialize();
	}

	public void initialize() {
		setName("Búsqueda de equipos");
		setLayout(new BorderLayout(0, 0));

		//FORMULARIO DE BUSQUEDA
		JPanel criteriosPanel = new JPanel();
		add(criteriosPanel, BorderLayout.NORTH);
		GridBagLayout gbl_criteriosPanel = new GridBagLayout();
		gbl_criteriosPanel.columnWidths = new int[]{68, 150, 20, 150, 20, 0};
		gbl_criteriosPanel.rowHeights = new int[]{30, 30, 30, 30, 0};
		gbl_criteriosPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_criteriosPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		criteriosPanel.setLayout(gbl_criteriosPanel);

		// CODIGO
		JLabel idLabel = new JLabel("Código:");
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.anchor = GridBagConstraints.EAST;
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 0;
		gbc_idLabel.gridy = 0;
		criteriosPanel.add(idLabel, gbc_idLabel);

		idTF = new JTextField();
		GridBagConstraints gbc_idTF = new GridBagConstraints();
		gbc_idTF.insets = new Insets(0, 0, 5, 5);
		gbc_idTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTF.gridx = 1;
		gbc_idTF.gridy = 0;
		criteriosPanel.add(idTF, gbc_idTF);
		idTF.setColumns(10);

		// PAIS
		JLabel countryLabel = new JLabel("País:");
		GridBagConstraints gbc_countryLabel = new GridBagConstraints();
		gbc_countryLabel.anchor = GridBagConstraints.EAST;
		gbc_countryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryLabel.gridx = 2;
		gbc_countryLabel.gridy = 0;
		criteriosPanel.add(countryLabel, gbc_countryLabel);
		provinceCB = new JComboBox();
		localityCB = new JComboBox();
		countryCB = new JComboBox();

		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Todas las provincias"));

		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 3;
		gbc_countryCB.gridy = 0;
		criteriosPanel.add(countryCB, gbc_countryCB);

		// NOMBRE
		JLabel nameLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(5, 5, 5, 5);
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 1;
		criteriosPanel.add(nameLabel, gbc_nameLabel);

		nameTF = new JTextField();
		GridBagConstraints gbc_nameTF = new GridBagConstraints();
		gbc_nameTF.anchor = GridBagConstraints.WEST;
		gbc_nameTF.insets = new Insets(5, 5, 5, 5);
		gbc_nameTF.gridx = 1;
		gbc_nameTF.gridy = 1;
		gbc_nameTF.fill = GridBagConstraints.HORIZONTAL;
		criteriosPanel.add(nameTF, gbc_nameTF);
		nameTF.setColumns(10);

		// PROVINCIA
		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 2;
		gbc_provinceLabel.gridy = 1;
		criteriosPanel.add(provinceLabel, gbc_provinceLabel);

		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Todas las localidades"));

		GridBagConstraints gbc_provinceCB = new GridBagConstraints();
		gbc_provinceCB.insets = new Insets(0, 0, 5, 5);
		gbc_provinceCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinceCB.gridx = 3;
		gbc_provinceCB.gridy = 1;
		criteriosPanel.add(provinceCB, gbc_provinceCB);

		// SIGLAS 
		JLabel initialismLabel = new JLabel("Siglas:");
		GridBagConstraints gbc_initialismLabel = new GridBagConstraints();
		gbc_initialismLabel.anchor = GridBagConstraints.EAST;
		gbc_initialismLabel.insets = new Insets(5, 5, 5, 5);
		gbc_initialismLabel.gridx = 0;
		gbc_initialismLabel.gridy = 2;
		criteriosPanel.add(initialismLabel, gbc_initialismLabel);

		initialismTF = new JTextField();
		GridBagConstraints gbc_initialismTF = new GridBagConstraints();
		gbc_initialismTF.anchor = GridBagConstraints.WEST;
		gbc_initialismTF.insets = new Insets(5, 5, 5, 5);
		gbc_initialismTF.gridx = 1;
		gbc_initialismTF.gridy = 2;
		gbc_initialismTF.fill = GridBagConstraints.HORIZONTAL;
		criteriosPanel.add(initialismTF, gbc_initialismTF);
		initialismTF.setColumns(10);

		// LOCALIDAD
		JLabel localityLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localityLabel = new GridBagConstraints();
		gbc_localityLabel.anchor = GridBagConstraints.EAST;
		gbc_localityLabel.insets = new Insets(5, 5, 5, 5);
		gbc_localityLabel.gridx = 2;
		gbc_localityLabel.gridy = 2;
		criteriosPanel.add(localityLabel, gbc_localityLabel);

		GridBagConstraints gbc_localityCB = new GridBagConstraints();
		gbc_localityCB.insets = new Insets(5, 5, 5, 5);
		gbc_localityCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityCB.gridx = 3;
		gbc_localityCB.gridy = 2;
		criteriosPanel.add(localityCB, gbc_localityCB);

		// DEPORTE
		JLabel sportLabel = new JLabel("Deporte:");
		GridBagConstraints gbc_sportLabel = new GridBagConstraints();
		gbc_sportLabel.anchor = GridBagConstraints.EAST;
		gbc_sportLabel.insets = new Insets(5, 5, 0, 5);
		gbc_sportLabel.gridx = 0;
		gbc_sportLabel.gridy = 3;
		criteriosPanel.add(sportLabel, gbc_sportLabel);

		sportCB = new JComboBox();
		GridBagConstraints gbc_sportCB = new GridBagConstraints();
		gbc_sportCB.insets = new Insets(5, 5, 0, 5);
		gbc_sportCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_sportCB.gridx = 1;
		gbc_sportCB.gridy = 3;
		criteriosPanel.add(sportCB, gbc_sportCB);

		// Botón de búsqueda
		searchButton = new JButton("Buscar");
		searchButton.setIcon(new ImageIcon(TeamSearchView.class.getResource("/nuvola/16x16/1339_kmag_kmag.png")));
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.fill = GridBagConstraints.NONE;
		gbc_searchButton.insets = new Insets(5, 5, 0, 5);
		gbc_searchButton.gridx = 3;
		gbc_searchButton.gridy = 3;
		criteriosPanel.add(searchButton, gbc_searchButton);

		// PANEL DE RESULTADOS
		JPanel resultsPanel = new JPanel(new BorderLayout());
		add(resultsPanel, BorderLayout.CENTER);

		resultsTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(resultsTable);
		resultsPanel.add(scrollPane, BorderLayout.CENTER);

		// PANEL DE PAGINACIÓN
		JPanel paginationPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(paginationPanel, BorderLayout.SOUTH);	
		totalResultadosLabel = new JLabel("Total resultados:");
		paginationPanel.add(totalResultadosLabel);
	}

	private void postInitialize() {
		// Configurar renderers
		countryCB.setRenderer(new CountryCBRenderer());
		provinceCB.setRenderer(new ProvinceCBRenderer());
		localityCB.setRenderer(new LocalityCBRenderer());
		sportCB.setRenderer(new SportCBRenderer());

		// Cargar países
		List<Country> countries = countryService.findAll();
		DefaultComboBoxModel<Country> paisModel = new DefaultComboBoxModel<>();
		Country placeholderPais = new Country();
		placeholderPais.setId(null);
		placeholderPais.setName("Todos los países");
		paisModel.addElement(placeholderPais);
		if (countries != null) {
			for (Country pais : countries) {
				paisModel.addElement(pais);
			}
		}
		countryCB.setModel(paisModel);

		// Cargar deportes
		List<Sport> deportes = sportService.findAll();
		DefaultComboBoxModel<Sport> sportModel = new DefaultComboBoxModel<>();
		Sport placeholderSport = new Sport();
		placeholderSport.setId(null);
		placeholderSport.setName("Todos los deportes");
		sportModel.addElement(placeholderSport);
		if (deportes != null) {
			for (Sport deporte : deportes) {
				sportModel.addElement(deporte);
			}
		}
		sportCB.setModel(sportModel);

		DefaultComboBoxModel<ProvinceDTO> provinceModel = new DefaultComboBoxModel<>();
		ProvinceDTO placeholderProv = new ProvinceDTO();
		placeholderProv.setId(null);
		placeholderProv.setName("Todas las provincias");
		provinceModel.addElement(placeholderProv);
		provinceCB.setModel(provinceModel);

		DefaultComboBoxModel<LocalityDTO> localityModel = new DefaultComboBoxModel<>();
		LocalityDTO placeholderLoc = new LocalityDTO();
		placeholderLoc.setId(null);
		placeholderLoc.setName("Todas las localidades");
		localityModel.addElement(placeholderLoc);
		localityCB.setModel(localityModel);


		// Configurar autocompletado
		AutoCompleteDecorator.decorate(countryCB, new CountryToStringConverter());
		AutoCompleteDecorator.decorate(sportCB, new SportToStringConverter());
		AutoCompleteDecorator.decorate(provinceCB, new ProvinceToStringConverter());
		AutoCompleteDecorator.decorate(localityCB, new LocalityToStringConverter());

		// setModel ( new ArrayList<TeamDTO>()); ??
		// el controlador lo ponemos aqui, en el postinitialize por si en algun momento se nos corrige dentro del initialize	
		// Enganchamos controladores
		TeamSearchController teamSearchAction = new TeamSearchController(this);
		searchButton.setAction(teamSearchAction);
		idTF.addKeyListener(teamSearchAction);
		nameTF.addKeyListener(teamSearchAction);
		initialismTF.addKeyListener(teamSearchAction);
		countryCB.setAction(teamSearchAction);
		provinceCB.setAction(teamSearchAction);
		localityCB.setAction(teamSearchAction);
		sportCB.setAction(teamSearchAction);

		TeamTableMouseController mouseController = new TeamTableMouseController();
		resultsTable.addMouseListener(mouseController);
		resultsTable.addMouseMotionListener(mouseController);		
	}

	private void initServices() {
		teamService = new TeamServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		sportService = new SportServiceImpl();
	}

	public TeamCriteria getCriteria() {
		TeamCriteria criteria = new TeamCriteria();
		// Validar ID
		Long id = ConversionUtils.toLong(idTF.getText());
		if (id == null && idTF.getText() != null && !idTF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(TeamSearchView.this, "El código debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
			idTF.setBackground(Color.red);
			return criteria;
		} else {
			idTF.setBackground(Color.WHITE);
			criteria.setId(id);
		}

		criteria.setName(nameTF.getText());
		criteria.setInitialism(initialismTF.getText());

		// Obtener valores de los combobox
		Sport selectedSport = (Sport) sportCB.getSelectedItem();
		Country selectedCountry = (Country) countryCB.getSelectedItem();      
		ProvinceDTO selectedProvince = (ProvinceDTO) provinceCB.getSelectedItem();
		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();

		// Establecer criterios de deporte
		if (selectedSport != null && selectedSport.getId() != null) {
			criteria.setSportId(selectedSport.getId());
		}
		// Establecer criterios de country
		if (selectedCountry != null && selectedCountry.getId() != null) {
			criteria.setCountryId(selectedCountry.getId());
		}
		// Establecer criterios de provincia
		if (selectedProvince != null && selectedProvince.getId() != null) {
			criteria.setProvinceId(selectedProvince.getId());
		}
		// Establecer criterios de localización - SOLO localityId
		if (selectedLocality != null && selectedLocality.getId() != null) {
			criteria.setLocalityId(selectedLocality.getId());
		}
		return criteria;        
	}

	public void setModel (Results<TeamDTO> model) {
		this.model = model;
		updateView();      
	}

	public void updateView() {
		TeamTableModel tableModel = new TeamTableModel(model.getPage());
		resultsTable.setModel(tableModel);

		TeamTableRenderer renderer = new TeamTableRenderer();
		resultsTable.setDefaultRenderer(Object.class, renderer);

		resultsTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		resultsTable.getColumnModel().getColumn(7).setCellEditor(new TeamButtonEditor(resultsTable));

		resultsTable.setRowHeight(25); // 25 píxeles de altura

		totalResultadosLabel.setText(model.getTotal()+" resultados encontrados.");
	}

}