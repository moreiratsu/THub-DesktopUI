package com.alvaro.thub.desktop.controller;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.UserView;
import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.UserServiceImpl;

public class UserUpdateController extends AbstractController {

	private UserView view = null;
	private UserService userService = null;

	public UserUpdateController(UserView view) {
		super(view, "Actualizar", new ImageIcon(UserUpdateController.class.getResource("/nuvola/guardar16.png")) ); 			
		this.view = view;
		this.userService = new UserServiceImpl();
	}

	public void doAction() {

		// Verificar que la vista tenga un modelo
		if (view.getModel() == null) {
			JOptionPane.showMessageDialog(view, "Error: No hay datos para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		UserDTO user = view.getModel();

		Long userId = view.getCurrentUserId();
		if (userId == null) {
			JOptionPane.showMessageDialog(view, "Error: El usuario no tiene ID", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		user.setId(userId);

		boolean updated = userService.update(user);

		if (updated) {
			JOptionPane.showMessageDialog(view, 
					"Usuario " + user.getName() + " actualizado correctamente.", 
					"Éxito", JOptionPane.INFORMATION_MESSAGE);

			view.setEditable(false);
			view.setAgreeController(new UserSetEditableController(view));
		} else {
			JOptionPane.showMessageDialog(view, 
					"Error al actualizar el usuario", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
