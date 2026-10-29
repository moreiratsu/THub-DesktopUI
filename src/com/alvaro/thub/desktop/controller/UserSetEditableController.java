package com.alvaro.thub.desktop.controller;

import javax.swing.ImageIcon;

import com.alvaro.thub.desktop.views.UserView;

public class UserSetEditableController extends AbstractController {

	private UserView view = null;
	
	public UserSetEditableController(UserView view) {
		super(view, "Editar", new ImageIcon(UserSetEditableController.class.getResource("/nuvola/editarx16.png")) ); 			
	}

	@Override
	public void doAction() {
		UserView view = (UserView) getView();
		view.setEditable(true);
		view.setAgreeController(new UserUpdateController(view));
	}

}
