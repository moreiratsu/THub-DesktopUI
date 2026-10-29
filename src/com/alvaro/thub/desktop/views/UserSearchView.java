package com.alvaro.thub.desktop.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.alvaro.thub.dao.criteria.UserCriteria;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.EventTableMouseController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.UserSearchController;
import com.alvaro.thub.desktop.controller.UserTableMouseController;
import com.alvaro.thub.desktop.utils.ConversionUtils;
import com.alvaro.thub.desktop.utils.CountryToStringConverter;
import com.alvaro.thub.desktop.utils.LocalityToStringConverter;
import com.alvaro.thub.desktop.utils.ProvinceToStringConverter;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.DateTableCellRenderer;
import com.alvaro.thub.desktop.views.renderer.GenderCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.RolCBRenderer;
import com.alvaro.thub.desktop.views.renderer.UserTableRenderer;
import com.alvaro.thub.desktop.views.tableModel.EventButtonEditor;
import com.alvaro.thub.desktop.views.tableModel.UserButtonEditor;
import com.alvaro.thub.desktop.views.tableModel.UserTableModel;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.Gender;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.Rol;
import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.GenderService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.RolService;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.GenderServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.RolServiceImpl;
import com.alvaro.thub.service.impl.UserServiceImpl;
import com.alvaro.thub.utils.Results;
import com.toedter.calendar.JDateChooser;


public class UserSearchView extends JPanel {

	private UserService userService = null;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private GenderService genderService;
	private RolService rolService;

	private JTextField idTF;
	private JTextField dniTF;
	private JTextField nameTF;
	private JTextField lastName1TF;
	private JTextField lastName2TF;
	private JTextField emailTF;
	private JDateChooser bornDateFromDC;
	private JDateChooser bornDateToDC;

	// Declaración de los combobox como variables de clase
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox genderCB;
	private JComboBox rolCB;

	private JTable resultsTable;
	private Results<UserDTO> model = null;
	private JLabel totalResultadosLabel;
	private JButton searchButton;

	public UserSearchView() {
		initServices();
		initialize();
		postInitialize();
	}

	public void initialize() {
		setName("Búsqueda de usuarios");
		setLayout(new BorderLayout(0, 0));

		// Panel de criterios de búsqueda
		JPanel criteriosPanel = new JPanel();
		add(criteriosPanel, BorderLayout.NORTH);
		GridBagLayout gbl_criteriosPanel = new GridBagLayout();
		gbl_criteriosPanel.columnWidths = new int[]{68, 33, 0, 0, 0, 0};
		gbl_criteriosPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_criteriosPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_criteriosPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		criteriosPanel.setLayout(gbl_criteriosPanel);

		// Código 
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

		// Fecha nacimiento (desde)
		JLabel bornDateFromLabel = new JLabel("Fecha nacimiento (desde):");
		GridBagConstraints gbc_bornDateFromLabel = new GridBagConstraints();
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

		// DNI
		JLabel dniLabel = new JLabel("DNI:");
		GridBagConstraints gbc_dniLabel = new GridBagConstraints();
		gbc_dniLabel.anchor = GridBagConstraints.EAST;
		gbc_dniLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dniLabel.gridx = 0;
		gbc_dniLabel.gridy = 1;
		criteriosPanel.add(dniLabel, gbc_dniLabel);

		dniTF = new JTextField();
		GridBagConstraints gbc_dniTF = new GridBagConstraints();
		gbc_dniTF.insets = new Insets(0, 0, 5, 5);
		gbc_dniTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_dniTF.gridx = 1;
		gbc_dniTF.gridy = 1;
		criteriosPanel.add(dniTF, gbc_dniTF);
		dniTF.setColumns(10);

		// Fecha nacimiento (hasta)
		JLabel bornDateToLabel = new JLabel("Fecha nacimiento (hasta):");
		GridBagConstraints gbc_bornDateToLabel = new GridBagConstraints();
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

		// Nombre
		JLabel nameLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 2;
		criteriosPanel.add(nameLabel, gbc_nameLabel);

		nameTF = new JTextField();
		GridBagConstraints gbc_nameTF = new GridBagConstraints();
		gbc_nameTF.insets = new Insets(0, 0, 5, 5);
		gbc_nameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTF.gridx = 1;
		gbc_nameTF.gridy = 2;
		criteriosPanel.add(nameTF, gbc_nameTF);
		nameTF.setColumns(10);

		// País
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

		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 3;
		gbc_countryCB.gridy = 2;
		criteriosPanel.add(countryCB, gbc_countryCB);

		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Todas las provincias"));

		// Primer apellido
		JLabel lastName1Label = new JLabel("Primer apellido:");
		GridBagConstraints gbc_lastName1Label = new GridBagConstraints();
		gbc_lastName1Label.anchor = GridBagConstraints.EAST;
		gbc_lastName1Label.insets = new Insets(0, 0, 5, 5);
		gbc_lastName1Label.gridx = 0;
		gbc_lastName1Label.gridy = 3;
		criteriosPanel.add(lastName1Label, gbc_lastName1Label);

		lastName1TF = new JTextField();
		GridBagConstraints gbc_lastName1TF = new GridBagConstraints();
		gbc_lastName1TF.insets = new Insets(0, 0, 5, 5);
		gbc_lastName1TF.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastName1TF.gridx = 1;
		gbc_lastName1TF.gridy = 3;
		criteriosPanel.add(lastName1TF, gbc_lastName1TF);
		lastName1TF.setColumns(10);

		// Provincia
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

		// Segundo apellido 
		JLabel lastName2Label = new JLabel("Segundo apellido:");
		GridBagConstraints gbc_lastName2Label = new GridBagConstraints();
		gbc_lastName2Label.anchor = GridBagConstraints.EAST;
		gbc_lastName2Label.insets = new Insets(0, 0, 5, 5);
		gbc_lastName2Label.gridx = 0;
		gbc_lastName2Label.gridy = 4;
		criteriosPanel.add(lastName2Label, gbc_lastName2Label);

		lastName2TF = new JTextField();
		GridBagConstraints gbc_lastName2TF = new GridBagConstraints();
		gbc_lastName2TF.insets = new Insets(0, 0, 5, 5);
		gbc_lastName2TF.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastName2TF.gridx = 1;
		gbc_lastName2TF.gridy = 4;
		criteriosPanel.add(lastName2TF, gbc_lastName2TF);
		lastName2TF.setColumns(10);

		// Localidad
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

		// Email
		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.EAST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 5;
		criteriosPanel.add(emailLabel, gbc_emailLabel);

		emailTF = new JTextField();
		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.insets = new Insets(0, 0, 5, 5);
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.gridx = 1;
		gbc_emailTF.gridy = 5;
		criteriosPanel.add(emailTF, gbc_emailTF);
		emailTF.setColumns(10);

		// Género
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

		// Rol
		JLabel rolLabel = new JLabel("Rol:");
		GridBagConstraints gbc_rolLabel = new GridBagConstraints();
		gbc_rolLabel.anchor = GridBagConstraints.EAST;
		gbc_rolLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rolLabel.gridx = 0;
		gbc_rolLabel.gridy = 6;
		criteriosPanel.add(rolLabel, gbc_rolLabel);

		rolCB = new JComboBox();
		GridBagConstraints gbc_rolCB = new GridBagConstraints();
		gbc_rolCB.insets = new Insets(0, 0, 5, 5);
		gbc_rolCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_rolCB.gridx = 1;
		gbc_rolCB.gridy = 6;
		criteriosPanel.add(rolCB, gbc_rolCB);

		// Botón de búsqueda
		searchButton = new JButton("Buscar");
		searchButton.setIcon(new ImageIcon(UserSearchView.class.getResource("/nuvola/16x16/1339_kmag_kmag.png")));
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.fill = GridBagConstraints.BOTH;
		gbc_searchButton.insets = new Insets(0, 0, 0, 5);
		gbc_searchButton.gridx = 3;
		gbc_searchButton.gridy = 7;
		criteriosPanel.add(searchButton, gbc_searchButton);

		// Resultados
		JPanel resultsPanel = new JPanel();
		add(resultsPanel, BorderLayout.CENTER);
		resultsPanel.setLayout(new BorderLayout(0, 0));

		resultsTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(resultsTable);
		resultsPanel.add(scrollPane, BorderLayout.CENTER);

		// Panel de paginación
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
		rolCB.setRenderer(new RolCBRenderer());

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

		// Cargar roles
		List<Rol> roles = rolService.findAll();
		DefaultComboBoxModel<Rol> rolModel = new DefaultComboBoxModel<>();
		Rol placeholderRol = new Rol();
		placeholderRol.setId(null);
		placeholderRol.setName("Todos los roles");
		rolModel.addElement(placeholderRol);
		if (roles != null) {
			for (Rol rol : roles) {
				rolModel.addElement(rol);
			}
		}
		rolCB.setModel(rolModel);

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
		AutoCompleteDecorator.decorate(provinceCB, new ProvinceToStringConverter());
		AutoCompleteDecorator.decorate(localityCB, new LocalityToStringConverter());

		// Enganchamos controladores
		UserSearchController userSearchAction = new UserSearchController(this);
		searchButton.setAction(userSearchAction);
		idTF.addKeyListener(userSearchAction);
		dniTF.addKeyListener(userSearchAction);
		nameTF.addKeyListener(userSearchAction);
		lastName1TF.addKeyListener(userSearchAction);
		lastName2TF.addKeyListener(userSearchAction);
		emailTF.addKeyListener(userSearchAction);
		countryCB.setAction(userSearchAction);
		provinceCB.setAction(userSearchAction);
		localityCB.setAction(userSearchAction);
		genderCB.setAction(userSearchAction);
		rolCB.setAction(userSearchAction);
		bornDateFromDC.addPropertyChangeListener(userSearchAction);
		bornDateToDC.addPropertyChangeListener(userSearchAction);
		// controlador para manejar eventos de mouse en la tabla (doble clic para abrir vista detallada, hover para resaltar fila)
		UserTableMouseController mouseController = new UserTableMouseController();
		resultsTable.addMouseListener(mouseController);
		resultsTable.addMouseMotionListener(mouseController);		

	}

	private void initServices() {
		userService = new UserServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		genderService = new GenderServiceImpl();
		rolService = new RolServiceImpl();
	}

	public UserCriteria getCriteria() {
		UserCriteria criteria = new UserCriteria();

		// Validar ID
		Long id = ConversionUtils.toLong(idTF.getText());
		if (id == null && idTF.getText() != null && !idTF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(UserSearchView.this, "El código debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
			idTF.setBackground(Color.red);
			return criteria;
		} else {
			idTF.setBackground(Color.WHITE);
			criteria.setId(id);
		}

		// Si hay ID, ignoramos el resto de criterios (como en EventSearchView)
		if (id == null) {
			criteria.setDni(dniTF.getText());
			criteria.setName(nameTF.getText());
			criteria.setEmail(emailTF.getText());

			// Obtener valores de los combobox
			LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
			Gender selectedGender = (Gender) genderCB.getSelectedItem();
			Rol selectedRol = (Rol) rolCB.getSelectedItem();

			// Establecer criterios
			if (selectedLocality != null && selectedLocality.getId() != null) {
				criteria.setLocalityId(selectedLocality.getId());
			}

			if (selectedGender != null && selectedGender.getId() != null) {
				criteria.setGenderId(selectedGender.getId());
			}

			if (selectedRol != null && selectedRol.getId() != null) {
				criteria.setRolId(selectedRol.getId());
			}
		}

		return criteria;
	}

	public void setModel(Results<UserDTO> model) {
		this.model = model;
		updateView();
	}

	public void updateView() {
		UserTableModel tableModel = new UserTableModel(model.getPage());
		resultsTable.setModel(tableModel);

		UserTableRenderer renderer = new UserTableRenderer();
		resultsTable.setDefaultRenderer(Object.class, renderer);

		// Luego SOBRESCRIBIR la columna 10 con su propio renderer y editor
		resultsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
		resultsTable.getColumnModel().getColumn(10).setCellEditor(new UserButtonEditor(resultsTable));

		totalResultadosLabel.setText(model.getTotal() + " resultados encontrados.");
	}
}