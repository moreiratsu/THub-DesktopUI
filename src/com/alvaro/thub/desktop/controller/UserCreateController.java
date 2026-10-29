package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.UserView;
import com.alvaro.thub.model.Gender;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.model.Rol;
import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.UserServiceImpl;

public class UserCreateController extends AbstractController {

	private UserView view = null;
	private UserService userService = null;

	public UserCreateController(UserView view) {
		super(view, "Guardar", new ImageIcon(EventCreateController.class.getResource("/nuvola/lupa16.png")));
		this.view = view;
		this.userService = new UserServiceImpl();
	}

	public void doAction() {
		UserDTO user = view.getModel();
		
		Long userId = userService.register(user);
		/*
		// Validaciones básicas
		if (nameTF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(UserView.this, 
					"El nombre es obligatorio", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (emailTF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(UserView.this, 
					"El email es obligatorio", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String password = new String(passwordPF.getPassword());
		if (password.isEmpty()) {
			JOptionPane.showMessageDialog(UserView.this, 
					"La contraseña es obligatoria", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		UserDTO user = new UserDTO();
		user.setDni(dniTF.getText());
		user.setName(nameTF.getText().trim());
		user.setLastName1(lastName1TF.getText());
		user.setLastName2(lastName2TF.getText());
		user.setBornDate(bornDateChooser.getDate());
		user.setPhoneNumber(phoneNumberTF.getText());
		user.setEmail(emailTF.getText().trim());
		user.setPassword(password);

		LocalityDTO selectedLocality = (LocalityDTO) localityCB.getSelectedItem();
		Gender selectedGender = (Gender) genderCB.getSelectedItem();
		Rol selectedRol = (Rol) rolCB.getSelectedItem();

		if (selectedLocality != null && selectedLocality.getId() != null) {
			user.setLocalityId(selectedLocality.getId());
			user.setLocalityName(selectedLocality.getName());
		}

		if (selectedGender != null && selectedGender.getId() != null) {
			user.setGenderId(selectedGender.getId());
			user.setGenderName(selectedGender.getName());
		}

		if (selectedRol != null && selectedRol.getId() != null) {
			user.setRolId(selectedRol.getId());
			user.setRolName(selectedRol.getName());
		}

		Long userId = userService.register(user);
		if (userId != null) {
			JOptionPane.showMessageDialog(UserView.this, 
					"Usuario creado correctamente con ID: " + userId, 
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(UserView.this, 
					"Error al crear el usuario. Es posible que el email ya exista.", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
});*/
}

}
