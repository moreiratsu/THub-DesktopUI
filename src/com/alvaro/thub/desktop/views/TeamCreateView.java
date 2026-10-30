package com.alvaro.thub.desktop.views;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.model.ContentData;
import com.alvaro.thub.model.ContentMetadata;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Sport;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.ContentService;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.SportService;
import com.alvaro.thub.service.impl.TeamServiceImpl;
import com.alvaro.thub.service.impl.ContentServiceFileImpl;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.SportServiceImpl;
import com.alvaro.thub.desktop.controller.AbstractController;
import com.alvaro.thub.desktop.controller.CancelController;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.TeamCreateController;
import com.alvaro.thub.desktop.controller.TeamUploadLogoController;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.SportCBRenderer;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;

/**
 * VISTA PARA LA CREACION Y EDICION de EQUIPOS deportivos.
 * Permite ingresar el nombre, las siglas, seleccionar el deporte, país, provincia, localidad y logo del equipo.
 */
public class TeamCreateView extends AbstractView {
	private JTextField nameTF;
	private JTextField initialismTF;
	private JPanel buttonsPanel;

	// Declaración de los combobox como variables de clase
	private JComboBox sportCB;
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;

	// Declaración de los servicios
	private TeamService teamService;
	private SportService sportService;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private ContentService contentService;

	private JButton agreeButton;
	private JButton cancelButton;
	
	// Declaración de los componentes del gestor de contenidos
	private JLabel logoPreviewLabel;
	private JButton uploadLogoButton;
	private byte[] selectedLogoBytes = null; // Guardará temporalmente los bytes de la imagen seleccionada
	private String selectedLogoExtension = null; // Guardará la extensión (.png, .jpg, etc.)
	
	// Variable de clase para almacenar el modelo del equipo que se está creando o editando
	private TeamDTO teamModel = null;

	public TeamCreateView() {
		initServices();
		initialize();
		postInitialize();
	}

	private void initialize() {
		setName("Nuevo equipo");
		setLayout(new BorderLayout(0, 0));

		// PANEL DEL FORMULARIO:
		JPanel contentPanel = new JPanel();
		add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);

		// NOMBRE
		JLabel nameLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 1;
		contentPanel.add(nameLabel, gbc_nameLabel);
		nameTF = new JTextField();
		GridBagConstraints gbc_nameTF = new GridBagConstraints();
		gbc_nameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTF.insets = new Insets(0, 0, 5, 5);
		gbc_nameTF.gridx = 2;
		gbc_nameTF.gridy = 1;
		contentPanel.add(nameTF, gbc_nameTF);
		nameTF.setColumns(10);

		// SIGLAS
		JLabel initialismLabel = new JLabel("Siglas:");
		GridBagConstraints gbc_initialismLabel = new GridBagConstraints();
		gbc_initialismLabel.anchor = GridBagConstraints.EAST;
		gbc_initialismLabel.insets = new Insets(0, 0, 5, 5);
		gbc_initialismLabel.gridx = 1;
		gbc_initialismLabel.gridy = 2;
		contentPanel.add(initialismLabel, gbc_initialismLabel);
		initialismTF = new JTextField();
		GridBagConstraints gbc_initialismTF = new GridBagConstraints();
		gbc_initialismTF.anchor = GridBagConstraints.WEST;
		gbc_initialismTF.insets = new Insets(0, 0, 5, 5);
		gbc_initialismTF.gridx = 2;
		gbc_initialismTF.gridy = 2;
		contentPanel.add(initialismTF, gbc_initialismTF);
		initialismTF.setColumns(10);

		// DEPORTE
		JLabel sportLabel = new JLabel("Deporte:");
		sportLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_sportLabel = new GridBagConstraints();
		gbc_sportLabel.anchor = GridBagConstraints.EAST;
		gbc_sportLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sportLabel.gridx = 1;
		gbc_sportLabel.gridy = 3;
		contentPanel.add(sportLabel, gbc_sportLabel);
		sportCB = new JComboBox();
		GridBagConstraints gbc_sportCB = new GridBagConstraints();
		gbc_sportCB.insets = new Insets(0, 0, 5, 5);
		gbc_sportCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_sportCB.gridx = 2;
		gbc_sportCB.gridy = 3;
		contentPanel.add(sportCB, gbc_sportCB);

		// PAÍS
		JLabel countryLabel = new JLabel("País:");
		GridBagConstraints gbc_countryLabel = new GridBagConstraints();
		gbc_countryLabel.anchor = GridBagConstraints.EAST;
		gbc_countryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryLabel.gridx = 1;
		gbc_countryLabel.gridy = 4;
		contentPanel.add(countryLabel, gbc_countryLabel);
		provinceCB = new JComboBox();
		localityCB = new JComboBox();
		countryCB = new JComboBox();
		
		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Seleccione las provincia"));

		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 2;
		gbc_countryCB.gridy = 4;
		contentPanel.add(countryCB, gbc_countryCB);

		// PROVINCIA
		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 1;
		gbc_provinceLabel.gridy = 5;
		contentPanel.add(provinceLabel, gbc_provinceLabel);
		
		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Seleccione la localidad"));

		GridBagConstraints gbc_provinceCB = new GridBagConstraints();
		gbc_provinceCB.insets = new Insets(0, 0, 5, 5);
		gbc_provinceCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinceCB.gridx = 2;
		gbc_provinceCB.gridy = 5;
		contentPanel.add(provinceCB, gbc_provinceCB);

		// LOCALIDAD
		JLabel localityLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localityLabel = new GridBagConstraints();
		gbc_localityLabel.anchor = GridBagConstraints.EAST;
		gbc_localityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localityLabel.gridx = 1;
		gbc_localityLabel.gridy = 6;
		contentPanel.add(localityLabel, gbc_localityLabel);
				
		GridBagConstraints gbc_localityCB = new GridBagConstraints();
		gbc_localityCB.insets = new Insets(0, 0, 5, 5);
		gbc_localityCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityCB.gridx = 2;
		gbc_localityCB.gridy = 6;
		contentPanel.add(localityCB, gbc_localityCB);

		// LOGO DEL EQUIPO
		JLabel logoLabel = new JLabel("Logo:");
		GridBagConstraints gbc_logoLabel = new GridBagConstraints();
		gbc_logoLabel.anchor = GridBagConstraints.EAST;
		gbc_logoLabel.insets = new Insets(0, 0, 0, 5);
		gbc_logoLabel.gridx = 1;
		gbc_logoLabel.gridy = 7;
		contentPanel.add(logoLabel, gbc_logoLabel);

		JPanel logoPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
		GridBagConstraints gbc_logoPanel = new GridBagConstraints();
		gbc_logoPanel.insets = new Insets(0, 0, 0, 5);
		gbc_logoPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_logoPanel.gridx = 2;
		gbc_logoPanel.gridy = 7;
		contentPanel.add(logoPanel, gbc_logoPanel);

		uploadLogoButton = new JButton("Adjuntar Foto");
		logoPanel.add(uploadLogoButton);

		logoPreviewLabel = new JLabel("Sin imagen");
		logoPanel.add(logoPreviewLabel);

		// PANEL DE LOS BOTONES DE GUARDAR Y CANCELAR:
		buttonsPanel = new JPanel();
		add(buttonsPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonsPanel = new GridBagLayout();
		gbl_buttonsPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_buttonsPanel.rowHeights = new int[]{0, 0, 0};
		gbl_buttonsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		buttonsPanel.setLayout(gbl_buttonsPanel);

		// GUARDAR
		agreeButton = new JButton("Guardar");
		agreeButton.setIcon(new ImageIcon(TeamCreateView.class.getResource("/nuvola/16x16/1710_ok_yes_accept_green_ok_green_accept_yes.png")));
		GridBagConstraints gbc_agreeButton = new GridBagConstraints();
		gbc_agreeButton.insets = new Insets(0, 0, 5, 5);
		gbc_agreeButton.gridx = 4;
		gbc_agreeButton.gridy = 0;
		buttonsPanel.add(agreeButton, gbc_agreeButton);

		// CANCELAR
		cancelButton = new JButton("Cancelar");
		cancelButton.setIcon(new ImageIcon(TeamCreateView.class.getResource("/nuvola/16x16/1250_delete_delete.png")));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancelButton.gridx = 6;
		gbc_cancelButton.gridy = 0;
		buttonsPanel.add(cancelButton, gbc_cancelButton);
	}

	private void postInitialize() {
		// Configurar renderers
		sportCB.setRenderer(new SportCBRenderer());
		countryCB.setRenderer(new CountryCBRenderer());
		provinceCB.setRenderer(new ProvinceCBRenderer());
		localityCB.setRenderer(new LocalityCBRenderer());

		// Cargar deportes
		List<Sport> deportes = sportService.findAll();
		DefaultComboBoxModel<Sport> sportModel = new DefaultComboBoxModel<>();
		Sport placeholderSport = new Sport();
		placeholderSport.setId(null);
		placeholderSport.setName("Seleccione un deporte");
		sportModel.addElement(placeholderSport);
		if (deportes != null) {
			for (Sport deporte : deportes) {
				sportModel.addElement(deporte);
			}
		}
		sportCB.setModel(sportModel);

		// Cargar países
		List<Country> countries = countryService.findAll();
		DefaultComboBoxModel<Country> paisModel = new DefaultComboBoxModel<>();
		Country placeholderPais = new Country();
		placeholderPais.setId(null);
		placeholderPais.setName("Seleccione un país");
		paisModel.addElement(placeholderPais);
		if (countries != null) {
			for (Country pais : countries) {
				paisModel.addElement(pais);
			}
		}
		countryCB.setModel(paisModel);

		// Inicializar combobox dependientes vacíos
		provinceCB.setModel(new DefaultComboBoxModel<>());
		localityCB.setModel(new DefaultComboBoxModel<>());

		// Controladores
		agreeButton.setAction(new TeamCreateController(this));
		cancelButton.setAction(new CancelController(this));
		uploadLogoButton.setAction(new TeamUploadLogoController(this));

		setEditable(false);

		// Inicializamos la vista con un modelo vacío
		setModel(new TeamDTO());
	}

	private void initServices() {
		teamService = new TeamServiceImpl();
		sportService = new SportServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		contentService = new ContentServiceFileImpl();
	}

	/**
	 * Recoge los datos ingresados en el formulario y los devuelve en un objeto TeamDTO.
	 * @return TeamDTO con los datos del formulario
	 */
	public TeamDTO getModel() {
		teamModel.setName(StringUtils.trimToNull(nameTF.getText()));
		teamModel.setInitialism(StringUtils.trimToNull(initialismTF.getText()));

		Sport selectedSport = (Sport) sportCB.getSelectedItem();
		if (selectedSport != null && selectedSport.getId() != null) {
			teamModel.setSportId(selectedSport.getId());
			teamModel.setSportName(selectedSport.getName());
		}

		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
		if (selectedLocality != null && selectedLocality.getId() != null) {
			teamModel.setLocalityId(selectedLocality.getId());
			teamModel.setLocalityName(selectedLocality.getName());
		}

		return teamModel;
	}

	/**
	 * Asigna un modelo de equipo a la vista y actualiza los campos del formulario.
	 * @param teamModel
	 */
	public void setModel(TeamDTO teamModel) {		
		this.teamModel = teamModel;
		updateView();
	}

	/**
	 * Actualiza los campos del formulario con los datos del modelo actual.
	 */
	public void updateView() {
		nameTF.setText(teamModel.getName());
		initialismTF.setText(teamModel.getInitialism());

		// Seleccionar deporte
		int i = 0;
		ComboBoxModel sportCBModel = sportCB.getModel();
		Sport sport = null;
		while (i < sportCBModel.getSize()) {
			sport = (Sport) sportCBModel.getElementAt(i);
			if (sport.getId() == (teamModel.getSportId())) {
				sportCBModel.setSelectedItem(sport);
			}
			i++;
		}

		// Seleccionar país 
		if (teamModel.getCountryId() != null) {
			int j = 0;
			ComboBoxModel countryModel = countryCB.getModel();
			while (j < countryModel.getSize()) {
				Country country = (Country) countryModel.getElementAt(j);
				if (country.getId() != null && country.getId().equals(teamModel.getCountryId())) {
					countryModel.setSelectedItem(country);
				}
				j++;
			}
		}

		// Seleccionar provincia 
		if (teamModel.getProvinceId() != null) {
			int k = 0;
			ComboBoxModel provinceModel = provinceCB.getModel();
			while (k < provinceModel.getSize()) {
				ProvinceDTO province = (ProvinceDTO) provinceModel.getElementAt(k);
				if (province.getId() != null && province.getId().equals(teamModel.getProvinceId())) {
					provinceModel.setSelectedItem(province);
				}
				k++;
			}
		}

		// Seleccionar localidad 
		int l = 0;
		ComboBoxModel localityModel = localityCB.getModel();
		while (l < localityModel.getSize()) {
			LocalityDTO locality = (LocalityDTO) localityModel.getElementAt(l);
			if (locality.getId() != null && locality.getId().equals(teamModel.getLocalityId())) {
				localityModel.setSelectedItem(locality);
			}
			l++;
		}

		// CARGAR LOGO EXISTENTE SI EL EQUIPO YA EXISTE
		if (teamModel != null && teamModel.getId() != null) {
			String[] formatosPosibles = {".png", ".jpg", ".jpeg"};
			boolean logoEncontrado = false;

			for (String extension : formatosPosibles) {
				try {
					ContentMetadata metadata = new ContentMetadata();
					metadata.setEntityType("TEAM");
					metadata.setEntityId(String.valueOf(teamModel.getId()));
					metadata.setResourceId("logo");
					metadata.setFormat(extension);

					ContentData data = contentService.get(metadata);
					if (data != null && data.getRaw() != null && data.getRaw().length > 0) {
						ImageIcon icon = new ImageIcon(data.getRaw());
						Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
						logoPreviewLabel.setIcon(new ImageIcon(scaledImage));
						logoPreviewLabel.setText("");
						logoEncontrado = true;
						break;
					}
				} catch (Exception e) {
					// Probar la siguiente extensión
				}
			}

			if (!logoEncontrado) {
				logoPreviewLabel.setIcon(null);
				logoPreviewLabel.setText("Sin logo");
			}
		} else {
			logoPreviewLabel.setIcon(null);
			logoPreviewLabel.setText("Sin imagen");
		}
	}

	/**
	 * Método invocado por UploadLogoTeamController para refrescar la imagen en la vista.
	 */
	public void setLogoData(byte[] bytes, String extension, ImageIcon previewIcon) {
		this.selectedLogoBytes = bytes;
		this.selectedLogoExtension = extension;
		this.logoPreviewLabel.setIcon(previewIcon);
		this.logoPreviewLabel.setText("");
	}

	/**
	 * Devuelve los bytes de la imagen seleccionada para que el controlador la guarde.
	 */
	public byte[] getSelectedLogoBytes() {
		return selectedLogoBytes;
	}

	/**
	 * Devuelve la extensión (.png, .jpg) del archivo cargado.
	 */
	public String getSelectedLogoExtension() {
		return selectedLogoExtension;
	}

	public void setEditable(boolean editable) {
		nameTF.setEditable(editable);
		initialismTF.setEditable(editable);
		sportCB.setEnabled(editable);
		countryCB.setEnabled(editable);
		provinceCB.setEnabled(editable);
		localityCB.setEnabled(editable);
		uploadLogoButton.setEnabled(editable);
	}

	public void setAgreeController(AbstractController controller) {
		agreeButton.setAction(controller);
	}

	public void setCancelController(AbstractController controller) {
		cancelButton.setAction(controller);
	}

	public void setUploadLogoController(AbstractController controller) {
		uploadLogoButton.setAction(controller);
	}
}