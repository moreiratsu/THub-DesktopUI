package com.alvaro.thub.desktop.views;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Gender;
import com.alvaro.thub.model.Rol;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.GenderService;
import com.alvaro.thub.service.RolService;
import com.alvaro.thub.service.impl.UserServiceImpl;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.GenderServiceImpl;
import com.alvaro.thub.service.impl.RolServiceImpl;
import com.alvaro.thub.desktop.controller.CancelController;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.EventCreateController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.UserCreateController;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.GenderCBRenderer;
import com.alvaro.thub.desktop.views.renderer.RolCBRenderer;

import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

/**
 * Vista para la creación de un nuevo usuario.
 * Permite ingresar datos personales, seleccionar país, provincia, localidad, género y rol.
 * Al guardar, se validan los campos obligatorios y se registra el usuario a través del UserService.
 */

public class UserView extends AbstractView {

	private JTextField dniTF;
	private JTextField nameTF;
	private JTextField lastName1TF;
	private JTextField lastName2TF;
	private JTextField phoneNumberTF;
	private JTextField emailTF;
	private JPasswordField passwordPF;
	private JPanel buttonsPanel;

	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox genderCB;      
	private JComboBox rolCB; 

	private JDateChooser bornDateChooser;

	private UserService userService;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private GenderService genderService;
	private RolService rolService;

	private JButton cancelButton;
	private JButton agreeButton;

	private UserDTO userModel = null;

	public UserView() {
		initServices();
		initialize();
		postInitialize();
	}

	private void initialize() {
		setName("Creación de usuario");
		setLayout(new BorderLayout(0, 0));

		JPanel contentPanel = new JPanel();
		add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 15 filas
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE}; // 14 pesos
		contentPanel.setLayout(gbl_contentPanel);

		// DNI
		JLabel dniLabel = new JLabel("DNI:");
		GridBagConstraints gbc_dniLabel = new GridBagConstraints();
		gbc_dniLabel.anchor = GridBagConstraints.EAST;
		gbc_dniLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dniLabel.gridx = 1;
		gbc_dniLabel.gridy = 1;
		contentPanel.add(dniLabel, gbc_dniLabel);

		dniTF = new JTextField();
		GridBagConstraints gbc_dniTF = new GridBagConstraints();
		gbc_dniTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_dniTF.insets = new Insets(0, 0, 5, 5);
		gbc_dniTF.gridx = 2;
		gbc_dniTF.gridy = 1;
		contentPanel.add(dniTF, gbc_dniTF);
		dniTF.setColumns(10);

		// Nombre
		JLabel nameLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 2;
		contentPanel.add(nameLabel, gbc_nameLabel);

		nameTF = new JTextField();
		GridBagConstraints gbc_nameTF = new GridBagConstraints();
		gbc_nameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTF.insets = new Insets(0, 0, 5, 5);
		gbc_nameTF.gridx = 2;
		gbc_nameTF.gridy = 2;
		contentPanel.add(nameTF, gbc_nameTF);
		nameTF.setColumns(10);

		// Primer apellido
		JLabel lastName1Label = new JLabel("Primer apellido:");
		GridBagConstraints gbc_lastName1Label = new GridBagConstraints();
		gbc_lastName1Label.anchor = GridBagConstraints.EAST;
		gbc_lastName1Label.insets = new Insets(0, 0, 5, 5);
		gbc_lastName1Label.gridx = 1;
		gbc_lastName1Label.gridy = 3;
		contentPanel.add(lastName1Label, gbc_lastName1Label);

		lastName1TF = new JTextField();
		GridBagConstraints gbc_lastName1TF = new GridBagConstraints();
		gbc_lastName1TF.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastName1TF.insets = new Insets(0, 0, 5, 5);
		gbc_lastName1TF.gridx = 2;
		gbc_lastName1TF.gridy = 3;
		contentPanel.add(lastName1TF, gbc_lastName1TF);
		lastName1TF.setColumns(10);

		// Segundo apellido
		JLabel lastName2Label = new JLabel("Segundo apellido:");
		GridBagConstraints gbc_lastName2Label = new GridBagConstraints();
		gbc_lastName2Label.anchor = GridBagConstraints.EAST;
		gbc_lastName2Label.insets = new Insets(0, 0, 5, 5);
		gbc_lastName2Label.gridx = 1;
		gbc_lastName2Label.gridy = 4;
		contentPanel.add(lastName2Label, gbc_lastName2Label);

		lastName2TF = new JTextField();
		GridBagConstraints gbc_lastName2TF = new GridBagConstraints();
		gbc_lastName2TF.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastName2TF.insets = new Insets(0, 0, 5, 5);
		gbc_lastName2TF.gridx = 2;
		gbc_lastName2TF.gridy = 4;
		contentPanel.add(lastName2TF, gbc_lastName2TF);
		lastName2TF.setColumns(10);

		// Fecha de nacimiento
		JLabel bornDateLabel = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_bornDateLabel = new GridBagConstraints();
		gbc_bornDateLabel.anchor = GridBagConstraints.EAST;
		gbc_bornDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bornDateLabel.gridx = 1;
		gbc_bornDateLabel.gridy = 5;
		contentPanel.add(bornDateLabel, gbc_bornDateLabel);

		bornDateChooser = new JDateChooser();
		GridBagConstraints gbc_bornDateChooser = new GridBagConstraints();
		gbc_bornDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_bornDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_bornDateChooser.gridx = 2;
		gbc_bornDateChooser.gridy = 5;
		contentPanel.add(bornDateChooser, gbc_bornDateChooser);

		// Teléfono
		JLabel phoneNumberLabel = new JLabel("Teléfono:");
		GridBagConstraints gbc_phoneNumberLabel = new GridBagConstraints();
		gbc_phoneNumberLabel.anchor = GridBagConstraints.EAST;
		gbc_phoneNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberLabel.gridx = 1;
		gbc_phoneNumberLabel.gridy = 6;
		contentPanel.add(phoneNumberLabel, gbc_phoneNumberLabel);

		phoneNumberTF = new JTextField();
		GridBagConstraints gbc_phoneNumberTF = new GridBagConstraints();
		gbc_phoneNumberTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneNumberTF.insets = new Insets(0, 0, 5, 5);
		gbc_phoneNumberTF.gridx = 2;
		gbc_phoneNumberTF.gridy = 6;
		contentPanel.add(phoneNumberTF, gbc_phoneNumberTF);
		phoneNumberTF.setColumns(10);

		// Email
		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.EAST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 7;
		contentPanel.add(emailLabel, gbc_emailLabel);

		emailTF = new JTextField();
		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.insets = new Insets(0, 0, 5, 5);
		gbc_emailTF.gridx = 2;
		gbc_emailTF.gridy = 7;
		contentPanel.add(emailTF, gbc_emailTF);
		emailTF.setColumns(10);

		// Contraseña
		JLabel passwordLabel = new JLabel("Contraseña:");
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.EAST;
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.gridx = 1;
		gbc_passwordLabel.gridy = 8;
		contentPanel.add(passwordLabel, gbc_passwordLabel);

		passwordPF = new JPasswordField();
		GridBagConstraints gbc_passwordPF = new GridBagConstraints();
		gbc_passwordPF.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordPF.insets = new Insets(0, 0, 5, 5);
		gbc_passwordPF.gridx = 2;
		gbc_passwordPF.gridy = 8;
		contentPanel.add(passwordPF, gbc_passwordPF);
		passwordPF.setColumns(10);

		// País
		JLabel countryLabel = new JLabel("País:");
		GridBagConstraints gbc_countryLabel = new GridBagConstraints();
		gbc_countryLabel.anchor = GridBagConstraints.EAST;
		gbc_countryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryLabel.gridx = 1;
		gbc_countryLabel.gridy = 9;
		contentPanel.add(countryLabel, gbc_countryLabel);

		countryCB = new JComboBox();
		provinceCB = new JComboBox();
		localityCB = new JComboBox();

		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Seleccione las provincia"));

		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 2;
		gbc_countryCB.gridy = 9;
		contentPanel.add(countryCB, gbc_countryCB);

		// Provincia
		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 1;
		gbc_provinceLabel.gridy = 10;
		contentPanel.add(provinceLabel, gbc_provinceLabel);

		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Seleccione la localidad"));

		GridBagConstraints gbc_provinceCB = new GridBagConstraints();
		gbc_provinceCB.insets = new Insets(0, 0, 5, 5);
		gbc_provinceCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinceCB.gridx = 2;
		gbc_provinceCB.gridy = 10;
		contentPanel.add(provinceCB, gbc_provinceCB);

		// Localidad
		JLabel localityLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localityLabel = new GridBagConstraints();
		gbc_localityLabel.anchor = GridBagConstraints.EAST;
		gbc_localityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localityLabel.gridx = 1;
		gbc_localityLabel.gridy = 11;
		contentPanel.add(localityLabel, gbc_localityLabel);

		GridBagConstraints gbc_localityCB = new GridBagConstraints();
		gbc_localityCB.insets = new Insets(0, 0, 5, 5);
		gbc_localityCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityCB.gridx = 2;
		gbc_localityCB.gridy = 11;
		contentPanel.add(localityCB, gbc_localityCB);

		// Género 
		JLabel genderLabel = new JLabel("Género:");
		GridBagConstraints gbc_genderLabel = new GridBagConstraints();
		gbc_genderLabel.anchor = GridBagConstraints.EAST;
		gbc_genderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_genderLabel.gridx = 1;
		gbc_genderLabel.gridy = 12;
		contentPanel.add(genderLabel, gbc_genderLabel);

		genderCB = new JComboBox();
		GridBagConstraints gbc_genderCB = new GridBagConstraints();
		gbc_genderCB.insets = new Insets(0, 0, 5, 5);
		gbc_genderCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_genderCB.gridx = 2;
		gbc_genderCB.gridy = 12;
		contentPanel.add(genderCB, gbc_genderCB);

		// Rol 
		JLabel rolLabel = new JLabel("Rol:");
		GridBagConstraints gbc_rolLabel = new GridBagConstraints();
		gbc_rolLabel.anchor = GridBagConstraints.EAST;
		gbc_rolLabel.insets = new Insets(0, 0, 0, 5);
		gbc_rolLabel.gridx = 1;
		gbc_rolLabel.gridy = 13;
		contentPanel.add(rolLabel, gbc_rolLabel);

		rolCB = new JComboBox();
		GridBagConstraints gbc_rolCB = new GridBagConstraints();
		gbc_rolCB.insets = new Insets(0, 0, 0, 5);
		gbc_rolCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_rolCB.gridx = 2;
		gbc_rolCB.gridy = 13;
		contentPanel.add(rolCB, gbc_rolCB);

		// Panel de botones 
		buttonsPanel = new JPanel();
		add(buttonsPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonsPanel = new GridBagLayout();
		gbl_buttonsPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_buttonsPanel.rowHeights = new int[]{0, 0, 0};
		gbl_buttonsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		buttonsPanel.setLayout(gbl_buttonsPanel);

		// Botón guardar
		JButton agreeButton = new JButton("Guardar");
		agreeButton.setIcon(new ImageIcon(UserView.class.getResource("/nuvola/16x16/1710_ok_yes_accept_green_ok_green_accept_yes.png")));
		GridBagConstraints gbc_agreeButton = new GridBagConstraints();
		gbc_agreeButton.insets = new Insets(0, 0, 5, 5);
		gbc_agreeButton.gridx = 4;
		gbc_agreeButton.gridy = 0;
		buttonsPanel.add(agreeButton, gbc_agreeButton);

		// Botón cancelar
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setIcon(new ImageIcon(UserView.class.getResource("/nuvola/16x16/1250_delete_delete.png")));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancelButton.gridx = 6;
		gbc_cancelButton.gridy = 0;
		buttonsPanel.add(cancelButton, gbc_cancelButton);
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
		placeholderPais.setName("Seleccione un país");
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
		placeholderGenero.setName("Seleccione un género");
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
		placeholderRol.setName("Seleccione un rol");
		rolModel.addElement(placeholderRol);
		if (roles != null) {
			for (Rol rol : roles) {
				rolModel.addElement(rol);
			}
		}
		rolCB.setModel(rolModel);

		// Inicializar combobox dependientes vacíos
		provinceCB.setModel(new DefaultComboBoxModel<>());
		localityCB.setModel(new DefaultComboBoxModel<>());

		// Controladores
		agreeButton.setAction(new UserCreateController(this));
		cancelButton.setAction(new CancelController(this));

		setEditable(false);

		setModel(new UserDTO());
	}

	private void initServices() {
		userService = new UserServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		genderService = new GenderServiceImpl();
		rolService = new RolServiceImpl();
	}

	public UserDTO getModel() {
		UserDTO user = new UserDTO();
		user.setDni(StringUtils.trimToNull(dniTF.getText()));
		user.setName(StringUtils.trimToNull(nameTF.getText().trim()));
		user.setLastName1(StringUtils.trimToNull(lastName1TF.getText()));
		user.setLastName2(StringUtils.trimToNull(lastName2TF.getText()));
		user.setBornDate(bornDateChooser.getDate());
		user.setPhoneNumber(StringUtils.trimToNull(phoneNumberTF.getText()));
		user.setEmail(StringUtils.trimToNull(emailTF.getText()));
		user.setPassword(new String(passwordPF.getPassword()));

		Country selectedCountry = (Country) countryCB.getSelectedItem();
		if (selectedCountry != null && selectedCountry.getId() != null) {
			user.setCountryId(selectedCountry.getId());
		}

		ProvinceDTO selectedProvince = (ProvinceDTO) provinceCB.getSelectedItem();
		if (selectedProvince != null && selectedProvince.getId() != null) {
			user.setProvinceId(selectedProvince.getId());
		}

		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
		if (selectedLocality != null && selectedLocality.getId() != null) {
			user.setLocalityId(selectedLocality.getId());
		}

		return user;
	}

	public void setModel(UserDTO user) {
		this.userModel = user;
		updateView();
	}

	public void updateView() {

		dniTF.setText(userModel.getDni());
		nameTF.setText(userModel.getName());
		lastName1TF.setText(userModel.getLastName1());
		lastName2TF.setText(userModel.getLastName2());
		bornDateChooser.setDate(userModel.getBornDate());
		phoneNumberTF.setText(userModel.getPhoneNumber());
		emailTF.setText(userModel.getEmail());
		passwordPF.setText(userModel.getPassword());

		// Seleccionar país
		for (int i = 0; i < countryCB.getItemCount(); i++) {
			Country pais = (Country) countryCB.getItemAt(i);
			if (pais.getId().equals(userModel.getCountryId())) {
				countryCB.setSelectedIndex(i);
				break;
			}
		}
		// Seleccionar provincia
		for (int i = 0; i < provinceCB.getItemCount(); i++) {
			ProvinceDTO provincia = (ProvinceDTO) provinceCB.getItemAt(i);
			if (provincia.getId().equals(userModel.getProvinceId())) {
				provinceCB.setSelectedIndex(i);
				break;
			}
		}
		// Seleccionar localidad
		for (int i = 0; i < localityCB.getItemCount(); i++) {
			LocalityDTO localidad = (LocalityDTO) localityCB.getItemAt(i);
			if (localidad.getId().equals(userModel.getLocalityId())) {
				localityCB.setSelectedIndex(i);
				break;
			}
		}
	}

	public Long getCurrentUserId() {
		return userModel != null ? userModel.getId() : null;
	}


	public void setEditable(boolean editable) {
		dniTF.setEditable(editable);
		nameTF.setEditable(editable);
		lastName1TF.setEditable(editable);
		lastName2TF.setEditable(editable);
		bornDateChooser.setEnabled(editable);
		phoneNumberTF.setEditable(editable);
		emailTF.setEditable(editable);
		passwordPF.setEditable(editable);
		countryCB.setEnabled(editable);
		provinceCB.setEnabled(editable);
		localityCB.setEnabled(editable);
	}

	public void setAgreeController(ActionListener controller) {
		agreeButton.addActionListener(controller);
	}

	public void setCancelController(ActionListener controller) {
		cancelButton.addActionListener(controller);
	}

}



