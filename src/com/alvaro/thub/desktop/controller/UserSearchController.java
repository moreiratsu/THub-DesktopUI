package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.alvaro.thub.dao.criteria.UserCriteria;
import com.alvaro.thub.desktop.views.UserSearchView;
import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.UserServiceImpl;
import com.alvaro.thub.utils.Results;

public class UserSearchController extends AbstractController {

	private UserSearchView view = null;
	private UserService userService = null;

	public UserSearchController(UserSearchView view) {
		super("Buscar", new ImageIcon(UserSearchController.class.getResource("/nuvola/lupa16.png")));
		this.view = view;
		this.userService = new UserServiceImpl();
	}

	public void doAction(){
		UserCriteria criteria = view.getCriteria();	
		Results<UserDTO> resultados = userService.findBy(criteria, 1, Integer.MAX_VALUE); 
		view.setModel(resultados);		
	}

}