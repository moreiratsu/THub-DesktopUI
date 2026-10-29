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

import com.alvaro.thub.model.PlayerDTO;
import com.alvaro.thub.model.Country;
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
import com.alvaro.thub.desktop.controller.AbstractController;
import com.alvaro.thub.desktop.controller.CancelController;
import com.alvaro.thub.desktop.controller.Controller;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.controller.PlayerCreateController;
import com.alvaro.thub.desktop.controller.PlayerUpdateController;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.GenderCBRenderer;
import com.alvaro.thub.desktop.views.renderer.TeamCBRenderer;

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
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;

public class PlayerView extends AbstractView {
	private JTextField dniTF;
	private JTextField nameTF;
	private JTextField lastName1TF;
	private JTextField lastName2TF;
	private JPanel buttonsPanel;

	// Declaración de los combobox como variables de clase
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox genderCB;
	private JComboBox teamCB;
	private JDateChooser bornDateChooser;

	// Declaración de los servicios
	private PlayerService playerService;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private GenderService genderService;
	private TeamService teamService;
	private JButton agreeButton;
	private JButton cancelButton;

	// Variable de clase para almacenar el modelo del jugador
	private PlayerDTO playerModel = null;

	public PlayerView() {
		initServices();
		initialize();
		postInitialize();
	}

	private void initialize() {
		setName("Nuevo jugador");
		setLayout(new BorderLayout(0, 0));

		JPanel contentPanel = new JPanel();
		add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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

		// NOMBRE
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

		// PRIMER APELLIDO
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

		// SEGUNDO APELLIDO
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

		// FECHA DE NACIMIENTO
		JLabel bornDateLabel = new JLabel("Fecha nacimiento:");
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


		// PAÍS
		JLabel countryLabel = new JLabel("País:");
		GridBagConstraints gbc_countryLabel = new GridBagConstraints();
		gbc_countryLabel.anchor = GridBagConstraints.EAST;
		gbc_countryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryLabel.gridx = 1;
		gbc_countryLabel.gridy = 6;
		contentPanel.add(countryLabel, gbc_countryLabel);
		provinceCB = new JComboBox();
		localityCB = new JComboBox();
		countryCB = new JComboBox();

		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Seleccione las provincia"));
		
		GridBagConstraints gbc_countryCB = new GridBagConstraints();
		gbc_countryCB.insets = new Insets(0, 0, 5, 5);
		gbc_countryCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCB.gridx = 2;
		gbc_countryCB.gridy = 6;
		contentPanel.add(countryCB, gbc_countryCB);

		// PROVINCIA
		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 1;
		gbc_provinceLabel.gridy = 7;
		contentPanel.add(provinceLabel, gbc_provinceLabel);

		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Seleccione la localidad"));
		
		GridBagConstraints gbc_provinceCB = new GridBagConstraints();
		gbc_provinceCB.insets = new Insets(0, 0, 5, 5);
		gbc_provinceCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinceCB.gridx = 2;
		gbc_provinceCB.gridy = 7;
		contentPanel.add(provinceCB, gbc_provinceCB);

		// LOCALIDAD
		JLabel localityLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localityLabel = new GridBagConstraints();
		gbc_localityLabel.anchor = GridBagConstraints.EAST;
		gbc_localityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localityLabel.gridx = 1;
		gbc_localityLabel.gridy = 8;
		contentPanel.add(localityLabel, gbc_localityLabel);

		GridBagConstraints gbc_localityCB = new GridBagConstraints();
		gbc_localityCB.insets = new Insets(0, 0, 5, 5);
		gbc_localityCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityCB.gridx = 2;
		gbc_localityCB.gridy = 8;
		contentPanel.add(localityCB, gbc_localityCB);

		// GÉNERO
		JLabel genderLabel = new JLabel("Género:");
		GridBagConstraints gbc_genderLabel = new GridBagConstraints();
		gbc_genderLabel.anchor = GridBagConstraints.EAST;
		gbc_genderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_genderLabel.gridx = 1;
		gbc_genderLabel.gridy = 9;
		contentPanel.add(genderLabel, gbc_genderLabel);

		genderCB = new JComboBox();
		GridBagConstraints gbc_genderCB = new GridBagConstraints();
		gbc_genderCB.insets = new Insets(0, 0, 5, 5);
		gbc_genderCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_genderCB.gridx = 2;
		gbc_genderCB.gridy = 9;
		contentPanel.add(genderCB, gbc_genderCB);

		// EQUIPO
		JLabel teamLabel = new JLabel("Equipo:");
		GridBagConstraints gbc_teamLabel = new GridBagConstraints();
		gbc_teamLabel.anchor = GridBagConstraints.EAST;
		gbc_teamLabel.insets = new Insets(0, 0, 5, 5);
		gbc_teamLabel.gridx = 1;
		gbc_teamLabel.gridy = 10;
		contentPanel.add(teamLabel, gbc_teamLabel);

		teamCB = new JComboBox();
		GridBagConstraints gbc_teamCB = new GridBagConstraints();
		gbc_teamCB.insets = new Insets(0, 0, 5, 5);
		gbc_teamCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamCB.gridx = 2;
		gbc_teamCB.gridy = 10;
		contentPanel.add(teamCB, gbc_teamCB);

		// PANEL DE BOTONES
		buttonsPanel = new JPanel();
		add(buttonsPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonsPanel = new GridBagLayout();
		gbl_buttonsPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_buttonsPanel.rowHeights = new int[]{0, 0, 0};
		gbl_buttonsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		buttonsPanel.setLayout(gbl_buttonsPanel);

		// BOTÓN GUARDAR
		agreeButton = new JButton("Guardar");
		agreeButton.setIcon(new ImageIcon(PlayerView.class.getResource("/nuvola/16x16/1710_ok_yes_accept_green_ok_green_accept_yes.png")));
		GridBagConstraints gbc_agreeButton = new GridBagConstraints();
		gbc_agreeButton.insets = new Insets(0, 0, 5, 5);
		gbc_agreeButton.gridx = 4;
		gbc_agreeButton.gridy = 0;
		buttonsPanel.add(agreeButton, gbc_agreeButton);

		// BOTÓN CANCELAR
		cancelButton = new JButton("Cancelar");
		cancelButton.setIcon(new ImageIcon(PlayerView.class.getResource("/nuvola/16x16/1250_delete_delete.png")));
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
		teamCB.setRenderer(new TeamCBRenderer());

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
		placeholderGenero.setName("Seleccione género");
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
		placeholderEquipo.setName("Seleccione un equipo");
		placeholderEquipo.setInitialism("");
		equipoModel.addElement(placeholderEquipo);
		if (equipos != null) {
			for (TeamDTO equipo : equipos) {
				equipoModel.addElement(equipo);
			}
		}
		teamCB.setModel(equipoModel);

		// Inicializar combobox dependientes vacíos
		provinceCB.setModel(new DefaultComboBoxModel<>());
		localityCB.setModel(new DefaultComboBoxModel<>());

		// Controladores
		agreeButton.setAction(new PlayerCreateController(this));
		cancelButton.setAction(new CancelController(this));

		setEditable(false);
	}

	private void initServices() {
		playerService = new PlayerServiceImpl();
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
		localityService = new LocalityServiceImpl();
		genderService = new GenderServiceImpl();
		teamService = new TeamServiceImpl();
	}

	public PlayerDTO getModel() {
		if (playerModel == null) {
			playerModel = new PlayerDTO();
		}

		playerModel.setDni(StringUtils.trimToNull(dniTF.getText()));
		playerModel.setName(StringUtils.trimToNull(nameTF.getText()));
		playerModel.setLastName1(StringUtils.trimToNull(lastName1TF.getText()));
		playerModel.setLastName2(StringUtils.trimToNull(lastName2TF.getText()));
		playerModel.setBornDate(bornDateChooser.getDate());

		Gender selectedGender = (Gender) genderCB.getSelectedItem();
		if (selectedGender != null && selectedGender.getId() != null) {
			playerModel.setGenderId(selectedGender.getId());
			playerModel.setGenderName(selectedGender.getName());
		}

		TeamDTO selectedTeam = (TeamDTO) teamCB.getSelectedItem();
		if (selectedTeam != null && selectedTeam.getId() != null) {
			playerModel.setTeamId(selectedTeam.getId());
			playerModel.setTeamName(selectedTeam.getName());
		}

		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
		if (selectedLocality != null && selectedLocality.getId() != null) {
			playerModel.setLocalityId(selectedLocality.getId());
			playerModel.setLocalityName(selectedLocality.getName());
		}

		return playerModel;
	}

	public void setModel(PlayerDTO playerModel) {
		this.playerModel = playerModel;
		updateView();
	}

	public void updateView() {
		dniTF.setText(playerModel.getDni());
		nameTF.setText(playerModel.getName());
		lastName1TF.setText(playerModel.getLastName1());
		lastName2TF.setText(playerModel.getLastName2());
		bornDateChooser.setDate(playerModel.getBornDate());

		// Seleccionar género
		if (playerModel.getGenderId() != null) {
			for (int i = 0; i < genderCB.getItemCount(); i++) {
				Gender gender = (Gender) genderCB.getItemAt(i);
				if (gender.getId() != null && gender.getId().equals(playerModel.getGenderId())) {
					genderCB.setSelectedIndex(i);
					break;
				}
			}
		}

		// Seleccionar equipo
		if (playerModel.getTeamId() != null) {
			for (int i = 0; i < teamCB.getItemCount(); i++) {
				TeamDTO team = (TeamDTO) teamCB.getItemAt(i);
				if (team.getId() != null && team.getId().equals(playerModel.getTeamId())) {
					teamCB.setSelectedIndex(i);
					break;
				}
			}
		}

		// Seleccionar país
		if (playerModel.getCountryId() != null) {
			for (int i = 0; i < countryCB.getItemCount(); i++) {
				Country country = (Country) countryCB.getItemAt(i);
				if (country.getId() != null && country.getId().equals(playerModel.getCountryId())) {
					countryCB.setSelectedIndex(i);
					break;
				}
			}
		}

		// Seleccionar provincia
		if (playerModel.getProvinceId() != null) {
			for (int i = 0; i < provinceCB.getItemCount(); i++) {
				ProvinceDTO province = (ProvinceDTO) provinceCB.getItemAt(i);
				if (province.getId() != null && province.getId().equals(playerModel.getProvinceId())) {
					provinceCB.setSelectedIndex(i);
					break;
				}
			}
		}

		// Seleccionar localidad
		if (playerModel.getLocalityId() != null) {
			for (int i = 0; i < localityCB.getItemCount(); i++) {
				LocalityDTO locality = (LocalityDTO) localityCB.getItemAt(i);
				if (locality.getId() != null && locality.getId().equals(playerModel.getLocalityId())) {
					localityCB.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	public void setEditable(boolean editable) {
		dniTF.setEditable(editable);
		nameTF.setEditable(editable);
		lastName1TF.setEditable(editable);
		lastName2TF.setEditable(editable);
		bornDateChooser.setEnabled(editable);
		countryCB.setEnabled(editable);
		provinceCB.setEnabled(editable);
		localityCB.setEnabled(editable);
		genderCB.setEnabled(editable);
		teamCB.setEnabled(editable);
	}

	public void setAgreeController(AbstractController controller) {
		agreeButton.setAction(controller);
	}

	public void setCancelController(AbstractController controller) {
		cancelButton.setAction(controller);
	}
}