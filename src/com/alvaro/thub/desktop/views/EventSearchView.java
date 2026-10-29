package com.alvaro.thub.desktop.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.alvaro.thub.dao.criteria.EventCriteria;
import com.alvaro.thub.dao.criteria.UserCriteria;
import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.EventSearchController;
import com.alvaro.thub.desktop.controller.EventSetEditableController;
import com.alvaro.thub.desktop.controller.EventTableMouseController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.TeamSetEditableController;
import com.alvaro.thub.desktop.controller.TeamTableMouseController;
import com.alvaro.thub.desktop.utils.ConversionUtils;
import com.alvaro.thub.desktop.utils.CountryToStringConverter;
import com.alvaro.thub.desktop.utils.LocalityToStringConverter;
import com.alvaro.thub.desktop.utils.ProvinceToStringConverter;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.DateTableCellRenderer;
import com.alvaro.thub.desktop.views.renderer.EventTableRenderer;
import com.alvaro.thub.desktop.views.renderer.FormatCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.StatusCBRenderer;
import com.alvaro.thub.desktop.views.renderer.UserCBRenderer;
import com.alvaro.thub.desktop.views.tableModel.EventButtonEditor;
import com.alvaro.thub.desktop.views.tableModel.EventTableModel;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.Event;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.model.Format;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.Status;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.EventService;
import com.alvaro.thub.service.FormatService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.StatusService;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.EventServiceImpl;
import com.alvaro.thub.service.impl.FormatServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.StatusServiceImpl;
import com.alvaro.thub.service.impl.UserServiceImpl;
import com.alvaro.thub.utils.Results;
import com.toedter.calendar.JDateChooser;

/**
 * Vista para la búsqueda de eventos.
 * Permite filtrar por código, nombre, país, provincia, localidad, formato, estado, creador y rango de fechas.
 * Muestra los resultados en una tabla con paginación.
 * Al hacer doble clic en un evento se abre su vista detallada.
 */

public class EventSearchView extends AbstractView {

	private EventService eventService = null;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private FormatService formatService;
	private StatusService statusService;
	private UserService userService;

	private JTextField nameTF;
	private JTextField idTF;
	private JDateChooser startDateFromDC;
	private JDateChooser startDateToDC;

	// Declaración de los combobox como variables de clase
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox formatCB;
	private JComboBox statusCB;
	private JComboBox creatorUserCB;


	private JTable resultsTable;	
	private Results<EventDTO> model = null; 
	private JLabel totalResultadosLabel;
	private JButton searchButton;

	public EventSearchView() {
		initServices();
		initialize();
		postInitialize();
	}

	public void initialize() {
		setName("Búsqueda de eventos");
		setLayout(new BorderLayout(0, 0));

		//FORMULARIO DE BUSQUEDA
		JPanel criteriosPanel = new JPanel();
		add(criteriosPanel, BorderLayout.NORTH);
		GridBagLayout gbl_criteriosPanel = new GridBagLayout();
		gbl_criteriosPanel.columnWidths = new int[]{68, 33, 0, 0, 0, 0};
		gbl_criteriosPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_criteriosPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_criteriosPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		criteriosPanel.setLayout(gbl_criteriosPanel);

		// CODIGO
		JLabel idLabel = new JLabel("Código:");
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.anchor = GridBagConstraints.EAST;
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 0;
		gbc_idLabel.gridy = 1;
		criteriosPanel.add(idLabel, gbc_idLabel);

		idTF = new JTextField();
		GridBagConstraints gbc_idTF = new GridBagConstraints();
		gbc_idTF.insets = new Insets(0, 0, 5, 5);
		gbc_idTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTF.gridx = 1;
		gbc_idTF.gridy = 1;
		criteriosPanel.add(idTF, gbc_idTF);
		idTF.setColumns(10);

		// PAIS
		JLabel countryLabel = new JLabel("País:");
		GridBagConstraints gbc_countryLabel = new GridBagConstraints();
		gbc_countryLabel.anchor = GridBagConstraints.EAST;
		gbc_countryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryLabel.gridx = 2;
		gbc_countryLabel.gridy = 1;
		criteriosPanel.add(countryLabel, gbc_countryLabel);
		provinceCB = new JComboBox();
		localityCB = new JComboBox();
		countryCB = new JComboBox();
		
		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 3;
		gbc_countryCB.gridy = 1;
		criteriosPanel.add(countryCB, gbc_countryCB);

		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Todas las provincias"));

		// NOMBRE
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

		// PROVINCIA
		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 2;
		gbc_provinceLabel.gridy = 2;
		criteriosPanel.add(provinceLabel, gbc_provinceLabel);

		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Todas las localidades"));
		
		GridBagConstraints gbc_provinceCB = new GridBagConstraints();
		gbc_provinceCB.insets = new Insets(0, 0, 5, 5);
		gbc_provinceCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinceCB.gridx = 3;
		gbc_provinceCB.gridy = 2;
		criteriosPanel.add(provinceCB, gbc_provinceCB);

		// CREADOR
		JLabel creatorLabel = new JLabel("Creado por:");
		GridBagConstraints gbc_creatorLabel = new GridBagConstraints();
		gbc_creatorLabel.anchor = GridBagConstraints.EAST;
		gbc_creatorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_creatorLabel.gridx = 0;
		gbc_creatorLabel.gridy = 3;
		criteriosPanel.add(creatorLabel, gbc_creatorLabel);

		creatorUserCB = new JComboBox();
		GridBagConstraints gbc_creatorCB = new GridBagConstraints();
		gbc_creatorCB.insets = new Insets(0, 0, 5, 5);
		gbc_creatorCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_creatorCB.gridx = 1;
		gbc_creatorCB.gridy = 3;
		criteriosPanel.add(creatorUserCB, gbc_creatorCB);

		// LOCALIDAD
		JLabel localityLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localityLabel = new GridBagConstraints();
		gbc_localityLabel.anchor = GridBagConstraints.EAST;
		gbc_localityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localityLabel.gridx = 2;
		gbc_localityLabel.gridy = 3;
		criteriosPanel.add(localityLabel, gbc_localityLabel);

		GridBagConstraints gbc_localityCB = new GridBagConstraints();
		gbc_localityCB.insets = new Insets(0, 0, 5, 5);
		gbc_localityCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityCB.gridx = 3;
		gbc_localityCB.gridy = 3;
		criteriosPanel.add(localityCB, gbc_localityCB);

		// FECHA DE INICIO
		JLabel startDateFromLabel = new JLabel("Fecha desde:");
		GridBagConstraints gbc_startDateFromLabel = new GridBagConstraints();
		gbc_startDateFromLabel.anchor = GridBagConstraints.EAST;
		gbc_startDateFromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startDateFromLabel.gridx = 0;
		gbc_startDateFromLabel.gridy = 4;
		criteriosPanel.add(startDateFromLabel, gbc_startDateFromLabel);

		startDateFromDC = new JDateChooser();
		GridBagConstraints gbc_startDateFromDC = new GridBagConstraints();
		gbc_startDateFromDC.insets = new Insets(0, 0, 5, 5);
		gbc_startDateFromDC.fill = GridBagConstraints.HORIZONTAL;
		gbc_startDateFromDC.gridx = 1;
		gbc_startDateFromDC.gridy = 4;
		criteriosPanel.add(startDateFromDC, gbc_startDateFromDC);

		// FORMATO
		JLabel formatLabel = new JLabel("Formato:");
		GridBagConstraints gbc_formatLabel = new GridBagConstraints();
		gbc_formatLabel.anchor = GridBagConstraints.EAST;
		gbc_formatLabel.insets = new Insets(0, 0, 5, 5);
		gbc_formatLabel.gridx = 2;
		gbc_formatLabel.gridy = 4;
		criteriosPanel.add(formatLabel, gbc_formatLabel);

		formatCB = new JComboBox();
		GridBagConstraints gbc_formatCB = new GridBagConstraints();
		gbc_formatCB.insets = new Insets(0, 0, 5, 5);
		gbc_formatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_formatCB.gridx = 3;
		gbc_formatCB.gridy = 4;
		criteriosPanel.add(formatCB, gbc_formatCB);

		// FECHA DE FIN
		JLabel startDateToLabel = new JLabel("Fecha hasta:");
		GridBagConstraints gbc_startDateToLabel = new GridBagConstraints();
		gbc_startDateToLabel.anchor = GridBagConstraints.EAST;
		gbc_startDateToLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startDateToLabel.gridx = 0;
		gbc_startDateToLabel.gridy = 5;
		criteriosPanel.add(startDateToLabel, gbc_startDateToLabel);

		startDateToDC = new JDateChooser();
		GridBagConstraints gbc_startDateToDC = new GridBagConstraints();
		gbc_startDateToDC.insets = new Insets(0, 0, 5, 5);
		gbc_startDateToDC.fill = GridBagConstraints.HORIZONTAL;
		gbc_startDateToDC.gridx = 1;
		gbc_startDateToDC.gridy = 5;
		criteriosPanel.add(startDateToDC, gbc_startDateToDC);

		// ESTADO
		JLabel statusLabel = new JLabel("Estado:");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.EAST;
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.gridx = 2;
		gbc_statusLabel.gridy = 5;
		criteriosPanel.add(statusLabel, gbc_statusLabel);

		statusCB = new JComboBox();
		GridBagConstraints gbc_statusCB = new GridBagConstraints();
		gbc_statusCB.insets = new Insets(0, 0, 5, 5);
		gbc_statusCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusCB.gridx = 3;
		gbc_statusCB.gridy = 5;
		criteriosPanel.add(statusCB, gbc_statusCB);

		// BOTON BUSCAR
		searchButton = new JButton("Buscar");		
		searchButton.setIcon(new ImageIcon(EventSearchView.class.getResource("/nuvola/16x16/1339_kmag_kmag.png")));
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.fill = GridBagConstraints.BOTH;
		gbc_searchButton.insets = new Insets(0, 0, 0, 5);
		gbc_searchButton.gridx = 3;
		gbc_searchButton.gridy = 6;
		criteriosPanel.add(searchButton, gbc_searchButton);

		// RESULTADOS DE LA BUSQUEDA
		JPanel resultsPanel = new JPanel();
		add(resultsPanel, BorderLayout.CENTER);
		resultsPanel.setLayout(new BorderLayout(0, 0));

		resultsTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(resultsTable);
		resultsPanel.add(scrollPane, BorderLayout.CENTER);

		// PANEL DE PAGINACION
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
		formatCB.setRenderer(new FormatCBRenderer());
		statusCB.setRenderer(new StatusCBRenderer());
		creatorUserCB.setRenderer(new UserCBRenderer());

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

		// Cargar formatos
		List<Format> formatos = formatService.findAll();
		DefaultComboBoxModel<Format> formatoModel = new DefaultComboBoxModel<>();
		Format placeholderFormato = new Format();
		placeholderFormato.setId(null);
		placeholderFormato.setName("Todos los formatos");
		formatoModel.addElement(placeholderFormato);
		if (formatos != null) {
			for (Format formato : formatos) {
				formatoModel.addElement(formato);
			}
		}
		formatCB.setModel(formatoModel);

		// Cargar estados
		List<Status> estados = statusService.findAll();
		DefaultComboBoxModel<Status> statusModel = new DefaultComboBoxModel<>();
		Status placeholderStatus = new Status();
		placeholderStatus.setId(null);
		placeholderStatus.setName("Todos los estados");
		statusModel.addElement(placeholderStatus);
		if (estados != null) {
			for (Status estado : estados) {
				statusModel.addElement(estado);
			}
		}
		statusCB.setModel(statusModel);

		// Cargar usuarios (organizadores)
		try {
			UserCriteria criteria = new UserCriteria();
			List<UserDTO> usuarios = userService.findBy(criteria, 0, Integer.MAX_VALUE).getPage();

			DefaultComboBoxModel<UserDTO> userModel = new DefaultComboBoxModel<>();

			UserDTO placeholderUser = new UserDTO();
			placeholderUser.setId(null);
			placeholderUser.setName("Todos los usuarios");
			userModel.addElement(placeholderUser);

			if (usuarios != null && !usuarios.isEmpty()) {
				for (UserDTO user : usuarios) {
					userModel.addElement(user);
				}
			}
			creatorUserCB.setModel(userModel);

		} catch (Exception e) {
			// Si hay error, mostrar combo vacío
			creatorUserCB.setModel(new DefaultComboBoxModel<>());
			System.err.println("Error al cargar usuarios: " + e.getMessage());
		}

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

		// el controlador lo ponemos aqui, en el postinitialize por si en algun momento se nos corrige dentro del initialize	
		// Enganchamos controladores de búsqueda a los campos del formulario
		EventSearchController eventSearchController = new EventSearchController(this);
		searchButton.setAction(eventSearchController);
		idTF.addKeyListener(eventSearchController);
		nameTF.addKeyListener(eventSearchController);
		countryCB.setAction(eventSearchController);
		provinceCB.setAction(eventSearchController);
		localityCB.setAction(eventSearchController);
		statusCB.setAction(eventSearchController);
		formatCB.setAction(eventSearchController);
		creatorUserCB.setAction(eventSearchController);
		startDateFromDC.addPropertyChangeListener(eventSearchController);
		startDateToDC.addPropertyChangeListener(eventSearchController);

		// controlador para manejar eventos de mouse en la tabla (doble clic para abrir vista detallada, hover para resaltar fila)
		EventTableMouseController mouseController = new EventTableMouseController();
		resultsTable.addMouseListener(mouseController);
		resultsTable.addMouseMotionListener(mouseController);		

	}

	private void initServices() {
		eventService = new EventServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		formatService = new FormatServiceImpl();
		statusService = new StatusServiceImpl();
		userService = new UserServiceImpl();
	}


	public EventCriteria getCriteria() {
		EventCriteria criteria = new EventCriteria();
		// Validar ID
		Long id = ConversionUtils.toLong(idTF.getText());
		if (id == null && idTF.getText() != null && !idTF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(EventSearchView.this, "El código debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
			idTF.setBackground(Color.red);
			return criteria;
		} else {
			idTF.setBackground(Color.WHITE);
			criteria.setId(id);
		}

		criteria.setName(nameTF.getText());

		// Rango de fechas
		if (startDateFromDC.getDate() != null) {
			criteria.setStartDate(startDateFromDC.getDate());
		}
		if (startDateToDC.getDate() != null) {
			criteria.setEndDate(startDateToDC.getDate());
		}

		// Obtener valores de los combobox
		Country selectedCountry = (Country) countryCB.getSelectedItem();
		ProvinceDTO selectedProvince = (ProvinceDTO) provinceCB.getSelectedItem();
		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
		Format selectedFormat = (Format) formatCB.getSelectedItem();
		Status selectedStatus = (Status) statusCB.getSelectedItem();
		UserDTO selectedUser = (UserDTO) creatorUserCB.getSelectedItem();

		// Establecer criterios de localización
		if (selectedLocality != null && selectedLocality.getId() != null) {
			ProvinceDTO province = provinceService.findById(selectedLocality.getProvinceId());
			if (province != null) {
				criteria.setCountryId(province.getCountryId());
			}
		} else if (selectedProvince != null && selectedProvince.getId() != null) {
			criteria.setCountryId(selectedProvince.getCountryId());
		} else if (selectedCountry != null && selectedCountry.getId() != null) {
			criteria.setCountryId(selectedCountry.getId());
		}

		// Establecer otros criterios
		if (selectedFormat != null && selectedFormat.getId() != null) {
			criteria.setFormatId(selectedFormat.getId());
		}

		if (selectedStatus != null && selectedStatus.getId() != null) {
			criteria.setStatusId(selectedStatus.getId());
		}

		if (selectedUser != null && selectedUser.getId() != null) {
			criteria.setCreatorUserId(selectedUser.getId());
		}

		return criteria;
	}	


	public void setModel(Results<EventDTO> model) {
		this.model = model;
		updateView();
	}

	public void updateView(){
		EventTableModel tableModel = new EventTableModel(model.getPage());
		resultsTable.setModel(tableModel);

		// Crear el renderer ANTES de asignarlo
		EventTableRenderer renderer = new EventTableRenderer();
		resultsTable.setDefaultRenderer(Object.class, renderer);

		// Luego SOBRESCRIBIR la columna 10 con su propio renderer y editor
		resultsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
		resultsTable.getColumnModel().getColumn(10).setCellEditor(new EventButtonEditor(resultsTable));

		// Ajustar la altura de las filas para que quepan los botones
		resultsTable.setRowHeight(25); // 25 píxeles de altura

		totalResultadosLabel.setText(model.getTotal()+" resultados encontrados.");


	}


}
