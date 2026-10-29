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
import javax.swing.table.TableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.alvaro.thub.dao.criteria.EventCriteria;
import com.alvaro.thub.dao.criteria.PlayerCriteria;
import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.OpenPlayerCreateFromSearchController;
import com.alvaro.thub.desktop.controller.PlayerCreateController;
import com.alvaro.thub.desktop.controller.PlayerSearchController;
import com.alvaro.thub.desktop.controller.PlayerSetEditableController;
import com.alvaro.thub.desktop.controller.PlayerTableMouseController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.TeamSetEditableController;
import com.alvaro.thub.desktop.utils.ConversionUtils;
import com.alvaro.thub.desktop.utils.CountryToStringConverter;
import com.alvaro.thub.desktop.utils.LocalityToStringConverter;
import com.alvaro.thub.desktop.utils.ProvinceToStringConverter;
import com.alvaro.thub.model.PlayerDTO;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Gender;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.service.PlayerService;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.GenderService;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.PlayerServiceImpl;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.GenderServiceImpl;
import com.alvaro.thub.service.impl.TeamServiceImpl;
import com.alvaro.thub.utils.Results;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.PlayerTableRenderer;
import com.alvaro.thub.desktop.views.renderer.GenderCBRenderer;
import com.alvaro.thub.desktop.views.renderer.TeamCBRenderer;
import com.alvaro.thub.desktop.views.tableModel.PlayerButtonEditor;
import com.alvaro.thub.desktop.views.tableModel.PlayerTableModel;
import com.alvaro.thub.desktop.views.renderer.DateTableCellRenderer;

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
import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;

/**
 * Vista para la búsqueda de participantes.
 * Permite filtrar por múltiples criterios y muestra los resultados en una tabla.
 */

public class PlayerSearchView extends AbstractView {

	private PlayerService playerService = null;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private GenderService genderService;
	private TeamService teamService;

	private JTextField dniTF;
	private JTextField nameTF;
	private JTextField lastName1TF;
	private JTextField lastName2TF;
	private JTextField idTF;
	private JDateChooser bornDateFromDC;
	private JDateChooser bornDateToDC;

	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox genderCB;  
	private JComboBox teamCB; 

	private JTable resultsTable;
	private Results<PlayerDTO> model = null;
	private JLabel totalResultadosLabel;
	private JButton searchButton;
	private JButton newPlayerButton;


	public PlayerSearchView() {
		initServices();
		initialize();
		postInitialize();
	}

	public void initialize() {
		setName("Búsqueda de participantes");
		setLayout(new BorderLayout(0, 0));

		//FORMULARIO DE BUSQUEDA
		JPanel criteriosPanel = new JPanel();
		add(criteriosPanel, BorderLayout.NORTH);
		GridBagLayout gbl_criteriosPanel = new GridBagLayout();
		gbl_criteriosPanel.columnWidths = new int[]{68, 33, 0, 0, 0, 0, 0, 0};
		gbl_criteriosPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_criteriosPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_criteriosPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		criteriosPanel.setLayout(gbl_criteriosPanel);

		// Fila 0: Código y Fecha de nacimiento (desde)
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

		JLabel bornDateFromLabel = new JLabel("Fecha nacimiento (desde):");
		GridBagConstraints gbc_bornDateFromLabel = new GridBagConstraints();
		gbc_bornDateFromLabel.anchor = GridBagConstraints.EAST;
		gbc_bornDateFromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bornDateFromLabel.gridx = 2;
		gbc_bornDateFromLabel.gridy = 0;
		criteriosPanel.add(bornDateFromLabel, gbc_bornDateFromLabel);

		bornDateFromDC = new JDateChooser();
		GridBagConstraints gbc_bornDateFromDC = new GridBagConstraints();
		gbc_bornDateFromDC.insets = new Insets(0, 0, 5, 5);
		gbc_bornDateFromDC.fill = GridBagConstraints.HORIZONTAL;
		gbc_bornDateFromDC.gridx = 3;
		gbc_bornDateFromDC.gridy = 0;
		criteriosPanel.add(bornDateFromDC, gbc_bornDateFromDC);

		// Fila 1: DNI y Fecha de nacimiento (hasta)
		JLabel dniLabel = new JLabel("DNI:");
		GridBagConstraints gbc_dniLabel = new GridBagConstraints();
		gbc_dniLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dniLabel.anchor = GridBagConstraints.EAST;
		gbc_dniLabel.gridx = 0;
		gbc_dniLabel.gridy = 1;
		criteriosPanel.add(dniLabel, gbc_dniLabel);

		dniTF = new JTextField();
		GridBagConstraints gbc_dniTF = new GridBagConstraints();
		gbc_dniTF.anchor = GridBagConstraints.WEST;
		gbc_dniTF.insets = new Insets(0, 0, 5, 5);
		gbc_dniTF.gridx = 1;
		gbc_dniTF.gridy = 1;
		gbc_dniTF.fill = GridBagConstraints.HORIZONTAL;
		criteriosPanel.add(dniTF, gbc_dniTF);
		dniTF.setColumns(10);

		JLabel bornDateToLabel = new JLabel("Fecha nacimiento (hasta):");
		GridBagConstraints gbc_bornDateToLabel = new GridBagConstraints();
		gbc_bornDateToLabel.anchor = GridBagConstraints.EAST;
		gbc_bornDateToLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bornDateToLabel.gridx = 2;
		gbc_bornDateToLabel.gridy = 1;
		criteriosPanel.add(bornDateToLabel, gbc_bornDateToLabel);

		bornDateToDC = new JDateChooser();
		GridBagConstraints gbc_bornDateToDC = new GridBagConstraints();
		gbc_bornDateToDC.insets = new Insets(0, 0, 5, 5);
		gbc_bornDateToDC.fill = GridBagConstraints.HORIZONTAL;
		gbc_bornDateToDC.gridx = 3;
		gbc_bornDateToDC.gridy = 1;
		criteriosPanel.add(bornDateToDC, gbc_bornDateToDC);

		// Fila 2: Nombre y País
		JLabel nameLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 2;
		criteriosPanel.add(nameLabel, gbc_nameLabel);

		nameTF = new JTextField();
		GridBagConstraints gbc_nameTF = new GridBagConstraints();
		gbc_nameTF.anchor = GridBagConstraints.WEST;
		gbc_nameTF.insets = new Insets(0, 0, 5, 5);
		gbc_nameTF.gridx = 1;
		gbc_nameTF.gridy = 2;
		gbc_nameTF.fill = GridBagConstraints.HORIZONTAL;
		criteriosPanel.add(nameTF, gbc_nameTF);
		nameTF.setColumns(10);

		JLabel countryLabel = new JLabel("País:");
		GridBagConstraints gbc_countryLabel = new GridBagConstraints();
		gbc_countryLabel.anchor = GridBagConstraints.EAST;
		gbc_countryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryLabel.gridx = 2;
		gbc_countryLabel.gridy = 2;
		criteriosPanel.add(countryLabel, gbc_countryLabel);
		provinceCB = new JComboBox();
		localityCB = new JComboBox();
		countryCB = new JComboBox();
		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Todas las provincias"));

		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 3;
		gbc_countryCB.gridy = 2;
		criteriosPanel.add(countryCB, gbc_countryCB);

		// Fila 3: Primer apellido y Provincia
		JLabel lastName1Label = new JLabel("Primer apellido:");
		GridBagConstraints gbc_lastName1Label = new GridBagConstraints();
		gbc_lastName1Label.anchor = GridBagConstraints.EAST;
		gbc_lastName1Label.insets = new Insets(0, 0, 5, 5);
		gbc_lastName1Label.gridx = 0;
		gbc_lastName1Label.gridy = 3;
		criteriosPanel.add(lastName1Label, gbc_lastName1Label);

		lastName1TF = new JTextField();
		GridBagConstraints gbc_lastName1TF = new GridBagConstraints();
		gbc_lastName1TF.anchor = GridBagConstraints.WEST;
		gbc_lastName1TF.insets = new Insets(0, 0, 5, 5);
		gbc_lastName1TF.gridx = 1;
		gbc_lastName1TF.gridy = 3;
		gbc_lastName1TF.fill = GridBagConstraints.HORIZONTAL;
		criteriosPanel.add(lastName1TF, gbc_lastName1TF);
		lastName1TF.setColumns(10);

		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 2;
		gbc_provinceLabel.gridy = 3;
		criteriosPanel.add(provinceLabel, gbc_provinceLabel);

		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Todas las localidades"));

		GridBagConstraints gbc_provinceCB = new GridBagConstraints();
		gbc_provinceCB.insets = new Insets(0, 0, 5, 5);
		gbc_provinceCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinceCB.gridx = 3;
		gbc_provinceCB.gridy = 3;
		criteriosPanel.add(provinceCB, gbc_provinceCB);

		// Fila 4: Segundo apellido y Localidad
		JLabel lastName2Label = new JLabel("Segundo apellido:");
		GridBagConstraints gbc_lastName2Label = new GridBagConstraints();
		gbc_lastName2Label.anchor = GridBagConstraints.NORTHEAST;
		gbc_lastName2Label.insets = new Insets(0, 0, 5, 5);
		gbc_lastName2Label.gridx = 0;
		gbc_lastName2Label.gridy = 4;
		criteriosPanel.add(lastName2Label, gbc_lastName2Label);

		lastName2TF = new JTextField();
		GridBagConstraints gbc_lastName2TF = new GridBagConstraints();
		gbc_lastName2TF.anchor = GridBagConstraints.WEST;
		gbc_lastName2TF.insets = new Insets(0, 0, 5, 5);
		gbc_lastName2TF.gridx = 1;
		gbc_lastName2TF.gridy = 4;
		gbc_lastName2TF.fill = GridBagConstraints.HORIZONTAL;
		criteriosPanel.add(lastName2TF, gbc_lastName2TF);
		lastName2TF.setColumns(10);

		JLabel localityLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localityLabel = new GridBagConstraints();
		gbc_localityLabel.anchor = GridBagConstraints.EAST;
		gbc_localityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localityLabel.gridx = 2;
		gbc_localityLabel.gridy = 4;
		criteriosPanel.add(localityLabel, gbc_localityLabel);

		GridBagConstraints gbc_localityCB = new GridBagConstraints();
		gbc_localityCB.insets = new Insets(0, 0, 5, 5);
		gbc_localityCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityCB.gridx = 3;
		gbc_localityCB.gridy = 4;
		criteriosPanel.add(localityCB, gbc_localityCB);

		// Fila 5: Equipo y Género
		JLabel teamLabel = new JLabel("Equipo:");
		GridBagConstraints gbc_teamLabel = new GridBagConstraints();
		gbc_teamLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_teamLabel.insets = new Insets(0, 0, 5, 5);
		gbc_teamLabel.gridx = 0;
		gbc_teamLabel.gridy = 5;
		criteriosPanel.add(teamLabel, gbc_teamLabel);

		teamCB = new JComboBox();
		GridBagConstraints gbc_teamCB = new GridBagConstraints();
		gbc_teamCB.insets = new Insets(0, 0, 5, 5);
		gbc_teamCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamCB.gridx = 1;
		gbc_teamCB.gridy = 5;
		criteriosPanel.add(teamCB, gbc_teamCB);

		JLabel genderLabel = new JLabel("Género:");
		GridBagConstraints gbc_genderLabel = new GridBagConstraints();
		gbc_genderLabel.anchor = GridBagConstraints.EAST;
		gbc_genderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_genderLabel.gridx = 2;
		gbc_genderLabel.gridy = 5;
		criteriosPanel.add(genderLabel, gbc_genderLabel);

		genderCB = new JComboBox();
		GridBagConstraints gbc_genderCB = new GridBagConstraints();
		gbc_genderCB.insets = new Insets(0, 0, 5, 5);
		gbc_genderCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_genderCB.gridx = 3;
		gbc_genderCB.gridy = 5;
		criteriosPanel.add(genderCB, gbc_genderCB);

		// Fila 6: Botón de búsqueda
		searchButton = new JButton("Buscar");
		searchButton.setIcon(new ImageIcon(PlayerSearchView.class.getResource("/nuvola/16x16/1467_xmag_xmag.png")));
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.fill = GridBagConstraints.BOTH;
		gbc_searchButton.insets = new Insets(0, 0, 0, 5);
		gbc_searchButton.gridx = 3;
		gbc_searchButton.gridy = 6;
		criteriosPanel.add(searchButton, gbc_searchButton);

		JPanel resultsPanel = new JPanel();
		add(resultsPanel, BorderLayout.CENTER);
		resultsPanel.setLayout(new BorderLayout(0, 0));

		resultsTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(resultsTable);
		resultsPanel.add(scrollPane, BorderLayout.CENTER);

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
		genderCB.setRenderer(new GenderCBRenderer());
		teamCB.setRenderer(new TeamCBRenderer());

		// Cargar países
		List<Country> countries = countryService.findAll();
		DefaultComboBoxModel<Country> paisModel = new DefaultComboBoxModel<>();
		Country placeholderPais = new Country();
		placeholderPais.setId(null);
		placeholderPais.setName("Todos los países");
		paisModel.addElement(placeholderPais);
		for (Country pais : countries) {
			paisModel.addElement(pais);
		}
		countryCB.setModel(paisModel); 

		// Cargar géneros
		List<Gender> generos = genderService.findAll();
		DefaultComboBoxModel<Gender> generoModel = new DefaultComboBoxModel<>();
		Gender placeholderGenero = new Gender();
		placeholderGenero.setId(null);
		placeholderGenero.setName("Todos los géneros");
		generoModel.addElement(placeholderGenero);
		if (generos != null) {
			for (Gender genero : generos) {
				generoModel.addElement(genero);
			}
		}
		genderCB.setModel(generoModel);

		// Cargar equipos
		List<TeamDTO> equipos = teamService.findAll();
		DefaultComboBoxModel<TeamDTO> equipoModel = new DefaultComboBoxModel<>();
		TeamDTO placeholderEquipo = new TeamDTO();
		placeholderEquipo.setId(null);
		placeholderEquipo.setName("Todos los equipos");
		equipoModel.addElement(placeholderEquipo);
		if (equipos != null) {
			for (TeamDTO equipo : equipos) {
				equipoModel.addElement(equipo);
			}
		}
		teamCB.setModel(equipoModel);

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

		// setModel( new ArrayList<PlayerDTO>());

		// Configurar autocompletado
		AutoCompleteDecorator.decorate(countryCB, new CountryToStringConverter());
		AutoCompleteDecorator.decorate(provinceCB, new ProvinceToStringConverter());
		AutoCompleteDecorator.decorate(localityCB, new LocalityToStringConverter());

		// Enganchamos controladores
		PlayerSearchController playerSearchAction = new PlayerSearchController(this);
		nameTF.addKeyListener(playerSearchAction);
		teamCB.setAction(playerSearchAction);
		searchButton.setAction(playerSearchAction);
		genderCB.setAction(playerSearchAction);
		countryCB.setAction(playerSearchAction);
		provinceCB.setAction(playerSearchAction);
		localityCB.setAction(playerSearchAction);
		bornDateFromDC.addPropertyChangeListener(playerSearchAction);
		bornDateToDC.addPropertyChangeListener(playerSearchAction);

		PlayerTableMouseController mouseController = new PlayerTableMouseController();
		resultsTable.addMouseListener(mouseController);
		resultsTable.addMouseMotionListener(mouseController);
	}

	private void initServices() {
		playerService = new PlayerServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		genderService = new GenderServiceImpl();
		teamService = new TeamServiceImpl();
	}

	public PlayerCriteria getCriteria() {
		PlayerCriteria criteria = new PlayerCriteria();

		// Validar ID
		Long id = ConversionUtils.toLong(idTF.getText());
		if (id == null && idTF.getText() != null && !idTF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(PlayerSearchView.this, "El código debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
			idTF.setBackground(Color.red);
			return criteria;
		} else {
			idTF.setBackground(Color.WHITE);
			criteria.setId(id);
		}

		criteria.setDni(dniTF.getText());
		criteria.setName(nameTF.getText());
		criteria.setLastName1(lastName1TF.getText());
		criteria.setLastName2(lastName2TF.getText());

		// Fechas
		if (bornDateFromDC.getDate() != null) {
			criteria.setBornDateFrom(bornDateFromDC.getDate());
		}
		if (bornDateToDC.getDate() != null) {
			criteria.setBornDateTo(bornDateToDC.getDate());
		}

		// Obtener valores de los combobox
		TeamDTO selectedTeam = (TeamDTO) teamCB.getSelectedItem();
		Gender selectedGender = (Gender) genderCB.getSelectedItem();
		Country selectedCountry = (Country) countryCB.getSelectedItem();
		ProvinceDTO selectedProvince = (ProvinceDTO) provinceCB.getSelectedItem();
		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();

		// Establecer criterios
		if (selectedGender != null && selectedGender.getId() != null) {
			criteria.setGenderId(selectedGender.getId());
		}

		if (selectedTeam != null && selectedTeam.getId() != null) {
			criteria.setTeamId(selectedTeam.getId());
		}

		if (selectedCountry != null && selectedCountry.getId() != null) {
			criteria.setCountryId(selectedCountry.getId());
		}

		if (selectedProvince != null && selectedProvince.getId() != null) {
			criteria.setProvinceId(selectedProvince.getId());
		}

		if (selectedLocality != null && selectedLocality.getId() != null) {
			criteria.setLocalityId(selectedLocality.getId());
		}

		return criteria;
	}


	public void setModel(Results<PlayerDTO> model) {
		this.model = model;
		updateView();
	}

	public void updateView() {
		PlayerTableModel tableModel = new PlayerTableModel(model.getPage());
		resultsTable.setModel(tableModel);

		PlayerTableRenderer renderer = new PlayerTableRenderer();
		resultsTable.setDefaultRenderer(Object.class, renderer);

		resultsTable.getColumnModel().getColumn(11).setCellRenderer(new ButtonRenderer());
		resultsTable.getColumnModel().getColumn(11).setCellEditor(new PlayerButtonEditor(resultsTable));

		resultsTable.setRowHeight(25);

		totalResultadosLabel.setText(model.getTotal()+" resultados encontrados.");
	}





}

