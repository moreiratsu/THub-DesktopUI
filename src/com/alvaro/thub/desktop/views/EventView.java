package com.alvaro.thub.desktop.views;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;

import com.alvaro.thub.model.Country;
import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Format;
import com.alvaro.thub.model.Status;
import com.alvaro.thub.model.Event;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.service.CountryService;
import com.alvaro.thub.service.ProvinceService;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.FormatService;
import com.alvaro.thub.service.StatusService;
import com.alvaro.thub.service.EventService;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.CountryServiceImpl;
import com.alvaro.thub.service.impl.ProvinceServiceImpl;
import com.alvaro.thub.service.impl.LocalityServiceImpl;
import com.alvaro.thub.service.impl.FormatServiceImpl;
import com.alvaro.thub.service.impl.StatusServiceImpl;
import com.alvaro.thub.service.impl.EventServiceImpl;
import com.alvaro.thub.service.impl.UserServiceImpl;
import com.alvaro.thub.desktop.controller.AbstractController;
import com.alvaro.thub.desktop.controller.CancelController;
import com.alvaro.thub.desktop.controller.CountryCBController;
import com.alvaro.thub.desktop.controller.EventCreateController;
import com.alvaro.thub.desktop.controller.ProvinceCBController;
import com.alvaro.thub.desktop.views.renderer.CountryCBRenderer;
import com.alvaro.thub.desktop.views.renderer.ProvinceCBRenderer;
import com.alvaro.thub.desktop.views.renderer.LocalityCBRenderer;
import com.alvaro.thub.desktop.views.renderer.FormatCBRenderer;
import com.alvaro.thub.desktop.views.renderer.StatusCBRenderer;

import java.awt.Insets;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import com.toedter.calendar.JDateChooser;

/**
 * Vista para crear o editar un evento. Contiene campos para nombre, fechas, ubicación, formato y estado.
 */

public class EventView extends AbstractView {

	private JTextField nombreTextField;

	// Declaración de los combobox como variables de clase
	private JComboBox countryCB;
	private JComboBox provinceCB;
	private JComboBox localityCB;
	private JComboBox formatoCB;
	private JComboBox statusCB;

	// Declaración de los DateChooser para fechas
	private JDateChooser fechaInicioDateChooser;
	private JDateChooser fechaFinDateChooser;

	// Declaración de los servicios
	private EventService eventService;
	private CountryService countryService;
	private ProvinceService provinceService;
	private LocalityService localityService;
	private FormatService formatService;
	private StatusService statusService;
	private UserService userService; // Para obtener el usuario logueado

	private JButton cancelButton;
	private JButton agreeButton;

	// Variable de clase para almacenar el modelo del evento
	private EventDTO eventModel = null;

	// Obtener el usuario logueado (esto depende de tu sistema de autenticación)
	// Por ejemplo: currentUserId = SessionManager.getCurrentUserId();
	private Long currentUserId = 2L; // Temporalmente para pruebas

	public EventView() {
		initServices();
		initialize(); 
		postInitialize();
	}

	private void initialize() {
		setName("Nuevo Evento");
		setLayout(new BorderLayout(0, 0));

		// PANEL DEL FORMULARIO:
		JPanel formularioCreacionEventPanel = new JPanel();
		add(formularioCreacionEventPanel, BorderLayout.CENTER);
		GridBagLayout gbl_formularioCreacionEventPanel = new GridBagLayout();
		gbl_formularioCreacionEventPanel.columnWidths = new int[]{41, 86, 23, 187, 47, 0};
		gbl_formularioCreacionEventPanel.rowHeights = new int[]{20, 14, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_formularioCreacionEventPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_formularioCreacionEventPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		formularioCreacionEventPanel.setLayout(gbl_formularioCreacionEventPanel);

		// NOMBRE
		JLabel nombreLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_nombreLabel = new GridBagConstraints();
		gbc_nombreLabel.anchor = GridBagConstraints.EAST;
		gbc_nombreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nombreLabel.gridx = 1;
		gbc_nombreLabel.gridy = 1;
		formularioCreacionEventPanel.add(nombreLabel, gbc_nombreLabel);

		nombreTextField = new JTextField();
		GridBagConstraints gbc_nombreTextField = new GridBagConstraints();
		gbc_nombreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTextField.gridx = 2;
		gbc_nombreTextField.gridy = 1;
		formularioCreacionEventPanel.add(nombreTextField, gbc_nombreTextField);
		nombreTextField.setColumns(10);

		// FECHA DE INICIO
		JLabel fechaInicioLabel = new JLabel("Fecha de Inicio:");
		GridBagConstraints gbc_fechaInicioLabel = new GridBagConstraints();
		gbc_fechaInicioLabel.anchor = GridBagConstraints.EAST;
		gbc_fechaInicioLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fechaInicioLabel.gridx = 3;
		gbc_fechaInicioLabel.gridy = 1;
		formularioCreacionEventPanel.add(fechaInicioLabel, gbc_fechaInicioLabel);

		fechaInicioDateChooser = new JDateChooser();
		GridBagConstraints gbc_fechaInicioDateChooser = new GridBagConstraints();
		gbc_fechaInicioDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_fechaInicioDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaInicioDateChooser.gridx = 4;
		gbc_fechaInicioDateChooser.gridy = 1;
		formularioCreacionEventPanel.add(fechaInicioDateChooser, gbc_fechaInicioDateChooser);

		// PAÍS
		JLabel paisLabel = new JLabel("País:");
		GridBagConstraints gbc_paisLabel = new GridBagConstraints();
		gbc_paisLabel.anchor = GridBagConstraints.EAST;
		gbc_paisLabel.insets = new Insets(0, 0, 5, 5);
		gbc_paisLabel.gridx = 1;
		gbc_paisLabel.gridy = 2;
		formularioCreacionEventPanel.add(paisLabel, gbc_paisLabel);

		countryCB = new JComboBox();
		provinceCB = new JComboBox();
		localityCB = new JComboBox();
		

		countryCB.addActionListener(new CountryCBController(countryCB, provinceCB, "Seleccione las provincia"));

		GridBagConstraints gbc_paisComboBox = new GridBagConstraints();
		gbc_paisComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_paisComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_paisComboBox.gridx = 2;
		gbc_paisComboBox.gridy = 2;
		formularioCreacionEventPanel.add(countryCB, gbc_paisComboBox);

		// FECHA DE FIN
		JLabel fechaFinLabel = new JLabel("Fecha de Fin:");
		GridBagConstraints gbc_fechaFinLabel = new GridBagConstraints();
		gbc_fechaFinLabel.anchor = GridBagConstraints.EAST;
		gbc_fechaFinLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fechaFinLabel.gridx = 3;
		gbc_fechaFinLabel.gridy = 2;
		formularioCreacionEventPanel.add(fechaFinLabel, gbc_fechaFinLabel);

		fechaFinDateChooser = new JDateChooser();
		GridBagConstraints gbc_fechaFinDateChooser = new GridBagConstraints();
		gbc_fechaFinDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_fechaFinDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaFinDateChooser.gridx = 4;
		gbc_fechaFinDateChooser.gridy = 2;
		formularioCreacionEventPanel.add(fechaFinDateChooser, gbc_fechaFinDateChooser);

		// PROVINCIA
		JLabel provinceLabel = new JLabel("Provincia:");
		GridBagConstraints gbc_provinceLabel = new GridBagConstraints();
		gbc_provinceLabel.anchor = GridBagConstraints.EAST;
		gbc_provinceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_provinceLabel.gridx = 1;
		gbc_provinceLabel.gridy = 3;
		formularioCreacionEventPanel.add(provinceLabel, gbc_provinceLabel);

		provinceCB.addActionListener(new ProvinceCBController(provinceCB, localityCB, "Seleccione la localidad"));

		GridBagConstraints gbc_provinciaComboBox = new GridBagConstraints();
		gbc_provinciaComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_provinciaComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_provinciaComboBox.gridx = 2;
		gbc_provinciaComboBox.gridy = 3;
		formularioCreacionEventPanel.add(provinceCB, gbc_provinciaComboBox);

		// FORMATO
		JLabel formatLabel = new JLabel("Formato:");
		GridBagConstraints gbc_formatLabel = new GridBagConstraints();
		gbc_formatLabel.anchor = GridBagConstraints.EAST;
		gbc_formatLabel.insets = new Insets(0, 0, 5, 5);
		gbc_formatLabel.gridx = 3;
		gbc_formatLabel.gridy = 3;
		formularioCreacionEventPanel.add(formatLabel, gbc_formatLabel);

		formatoCB = new JComboBox();
		GridBagConstraints gbc_formatoComboBox = new GridBagConstraints();
		gbc_formatoComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_formatoComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_formatoComboBox.gridx = 4;
		gbc_formatoComboBox.gridy = 3;
		formularioCreacionEventPanel.add(formatoCB, gbc_formatoComboBox);

		// LOCALIDAD
		JLabel localidadLabel = new JLabel("Localidad:");
		GridBagConstraints gbc_localidadLabel = new GridBagConstraints();
		gbc_localidadLabel.anchor = GridBagConstraints.EAST;
		gbc_localidadLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localidadLabel.gridx = 1;
		gbc_localidadLabel.gridy = 4;
		formularioCreacionEventPanel.add(localidadLabel, gbc_localidadLabel);

		GridBagConstraints gbc_localidadComboBox = new GridBagConstraints();
		gbc_localidadComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_localidadComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_localidadComboBox.gridx = 2;
		gbc_localidadComboBox.gridy = 4;
		formularioCreacionEventPanel.add(localityCB, gbc_localidadComboBox);

		// ESTADO
		JLabel statusLabel = new JLabel("Estado:");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.EAST;
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.gridx = 3;
		gbc_statusLabel.gridy = 4;
		formularioCreacionEventPanel.add(statusLabel, gbc_statusLabel);

		statusCB = new JComboBox();
		GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
		gbc_statusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_statusComboBox.gridx = 4;
		gbc_statusComboBox.gridy = 4;
		formularioCreacionEventPanel.add(statusCB, gbc_statusComboBox);

		// PANEL DE BOTONES:
		JPanel buttonsNewEventPannel = new JPanel();
		add(buttonsNewEventPannel, BorderLayout.SOUTH);
		GridBagLayout gbl_buttonsNewEventPannel = new GridBagLayout();
		gbl_buttonsNewEventPannel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_buttonsNewEventPannel.rowHeights = new int[]{0, 0, 0};
		gbl_buttonsNewEventPannel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonsNewEventPannel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		buttonsNewEventPannel.setLayout(gbl_buttonsNewEventPannel);


		// BOTON GUARDAR
		agreeButton = new JButton("Guardar");
		agreeButton.setIcon(new ImageIcon(EventView.class.getResource("/nuvola/16x16/1710_ok_yes_accept_green_ok_green_accept_yes.png")));
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 0, 5, 5);
		gbc_createButton.gridx = 8;
		gbc_createButton.gridy = 0;
		buttonsNewEventPannel.add(agreeButton, gbc_createButton);


		// BOTON CANCELAR
		cancelButton = new JButton("Cancelar");
		cancelButton.setIcon(new ImageIcon(EventView.class.getResource("/nuvola/16x16/1250_delete_delete.png")));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancelButton.gridx = 10;
		gbc_cancelButton.gridy = 0;
		buttonsNewEventPannel.add(cancelButton, gbc_cancelButton);
	}

	private void postInitialize() {
		// Configurar renderers
		countryCB.setRenderer(new CountryCBRenderer());
		provinceCB.setRenderer(new ProvinceCBRenderer());
		localityCB.setRenderer(new LocalityCBRenderer());
		formatoCB.setRenderer(new FormatCBRenderer());
		statusCB.setRenderer(new StatusCBRenderer());

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

		// Cargar formatos
		List<Format> formatos = formatService.findAll();
		DefaultComboBoxModel<Format> formatoModel = new DefaultComboBoxModel<>();
		Format placeholderFormato = new Format();
		placeholderFormato.setId(null);
		placeholderFormato.setName("Seleccione un formato");
		formatoModel.addElement(placeholderFormato);
		if (formatos != null) {
			for (Format formato : formatos) {
				formatoModel.addElement(formato);
			}
		}
		formatoCB.setModel(formatoModel);

		// Cargar estados
		List<Status> estados = statusService.findAll();
		DefaultComboBoxModel<Status> statusModel = new DefaultComboBoxModel<>();
		Status placeholderStatus = new Status();
		placeholderStatus.setId(null);
		placeholderStatus.setName("Seleccione un estado");
		statusModel.addElement(placeholderStatus);
		if (estados != null) {
			for (Status estado : estados) {
				statusModel.addElement(estado);
			}
		}
		statusCB.setModel(statusModel);

		// Inicializar combobox dependientes vacíos
		provinceCB.setModel(new DefaultComboBoxModel<>());
		localityCB.setModel(new DefaultComboBoxModel<>());


		// Controladores
		agreeButton.setAction(new EventCreateController(this));
		cancelButton.setAction(new CancelController(this));

		setEditable(true);

		// TODO: dependiendo del rol del usuario logueado, podríamos establecer un controlador u otro (por ejemplo, si el usuario solo tiene permiso de lectura,
		// no debería haber ningún controlador o debería ser un controlador que muestre un mensaje de error indicando que no tiene permisos para realizar esa acción)
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

	/**
	 * Metodo para limpiar el formulario y dejarlo listo para crear un nuevo evento.
	 * Se utiliza principalmente después de crear un evento exitosamente, 
	 * para que el usuario pueda crear otro evento sin tener que cerrar y volver a abrir la vista.
	 */
	private void limpiarFormulario() {
		nombreTextField.setText("");
		fechaInicioDateChooser.setDate(null);
		fechaFinDateChooser.setDate(null);
		countryCB.setSelectedIndex(0);
		formatoCB.setSelectedIndex(0);
		statusCB.setSelectedIndex(0);
		provinceCB.setModel(new DefaultComboBoxModel<>());
		localityCB.setModel(new DefaultComboBoxModel<>());
	}

	/**
	 * Devuelve un objeto Event (no DTO) para usar en el servicio (create/update)
	 * Este es el que debe usarse para guardar en la base de datos
	 */
	public Event getModel() {
	    Event event = new Event();
	    
	    event.setName(StringUtils.trimToNull(nombreTextField.getText()));
	    event.setStartDate(fechaInicioDateChooser.getDate());
	    event.setEndDate(fechaFinDateChooser.getDate());

	    Format selectedFormat = (Format) formatoCB.getSelectedItem();
	    if (selectedFormat != null && selectedFormat.getId() != null) {
	        event.setFormatId(selectedFormat.getId());
	    }

	    Status selectedStatus = (Status) statusCB.getSelectedItem();
	    if (selectedStatus != null && selectedStatus.getId() != null) {
	        event.setStatusId(selectedStatus.getId());
	    }

	    Country selectedCountry = (Country) countryCB.getSelectedItem();
	    if (selectedCountry != null && selectedCountry.getId() != null) {
	        event.setCountryId(selectedCountry.getId());
	    }

	    // Guardar provincia y localidad
	    ProvinceDTO selectedProvince = (ProvinceDTO) provinceCB.getSelectedItem();
	    if (selectedProvince != null && selectedProvince.getId() != null) {
	        event.setProvinceId(selectedProvince.getId());
	    }

	    LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
	    if (selectedLocality != null && selectedLocality.getId() != null) {
	        event.setLocalityId(selectedLocality.getId());
	    }

	    event.setCreatorUserId(currentUserId);

	    return event;
	}


	public void setModel(EventDTO eventDTO) {
		if (eventDTO == null) {
	        this.eventModel = new EventDTO();
	    } else {
	        this.eventModel = eventDTO;
	    }
	    updateView();
	}

		public void updateView() {
		    if (eventModel == null) {
		        return;
		    }
		    nombreTextField.setText(eventModel.getName());
		    fechaInicioDateChooser.setDate(eventModel.getStartDate());
		    fechaFinDateChooser.setDate(eventModel.getEndDate());

		    // Seleccionar formato
		    for (int i = 0; i < formatoCB.getItemCount(); i++) {
		        Format format = (Format) formatoCB.getItemAt(i);
		        if (format.getId() != null && format.getId().equals(eventModel.getFormatId())) {
		            formatoCB.setSelectedIndex(i);
		            break;
		        }
		    }

		    // Seleccionar estado
		    for (int i = 0; i < statusCB.getItemCount(); i++) {
		        Status status = (Status) statusCB.getItemAt(i);
		        if (status.getId() != null && status.getId().equals(eventModel.getStatusId())) {
		            statusCB.setSelectedIndex(i);
		            break;
		        }
		    }

		    // Seleccionar país
		    if (eventModel.getCountryId() != null) {
		        for (int i = 0; i < countryCB.getItemCount(); i++) {
		            Country country = (Country) countryCB.getItemAt(i);
		            if (country.getId() != null && country.getId().equals(eventModel.getCountryId())) {
		                countryCB.setSelectedIndex(i);
		                break;
		            }
		        }
		    }

		    // AÑADIDO: Seleccionar provincia
		    if (eventModel.getProvinceId() != null) {
		        for (int i = 0; i < provinceCB.getItemCount(); i++) {
		            ProvinceDTO province = (ProvinceDTO) provinceCB.getItemAt(i);
		            if (province.getId() != null && province.getId().equals(eventModel.getProvinceId())) {
		                provinceCB.setSelectedIndex(i);
		                break;
		            }
		        }
		    }

		    // AÑADIDO: Seleccionar localidad
		    if (eventModel.getLocalityId() != null) {
		        for (int i = 0; i < localityCB.getItemCount(); i++) {
		            LocalityDTO locality = (LocalityDTO) localityCB.getItemAt(i);
		            if (locality.getId() != null && locality.getId().equals(eventModel.getLocalityId())) {
		                localityCB.setSelectedIndex(i);
		                break;
		            }
		        }
		    }
		}

		public Long getCurrentEventId() {
		    return eventModel != null ? eventModel.getId() : null;
		}
		
	public void setEditable(boolean editable) {
		nombreTextField.setEditable(editable);
		fechaInicioDateChooser.setEnabled(editable);
		fechaFinDateChooser.setEnabled(editable);
		countryCB.setEnabled(editable);
		provinceCB.setEnabled(editable);
		localityCB.setEnabled(editable);
		formatoCB.setEnabled(editable);
		statusCB.setEnabled(editable);
		// TODO falta usuario, pero no se si se va a poder editar
	}

	public void setAgreeController(AbstractController controller) {
		agreeButton.setAction(controller);
	}

	public void setCancelController(AbstractController controller) {
		cancelButton.setAction(controller);
	}
}