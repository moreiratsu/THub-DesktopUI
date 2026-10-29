package com.alvaro.thub.desktop.controller;

import javax.swing.ImageIcon;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.View;

/**
 * Controlador para manejar la cancelación de una operación en la interfaz de usuario.
 * 
 * Este controlador se encarga de cerrar la vista sin guardar cambios cuando el usuario decide cancelar la operación.
 */

public class CancelController extends AbstractController {

	public CancelController(View view) {
		super(view, "Cancelar", new ImageIcon(TeamSearchController.class.getResource("/nuvola/cancelarx16.png"))); 
	}

	@Override
	public void doAction() {
		MainWindow.getInstance().remove(getView());
	}

}
