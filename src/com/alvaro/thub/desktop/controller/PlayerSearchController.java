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

import com.alvaro.thub.dao.criteria.PlayerCriteria;
import com.alvaro.thub.desktop.views.PlayerSearchView;
import com.alvaro.thub.desktop.views.UserSearchView;
import com.alvaro.thub.model.PlayerDTO;
import com.alvaro.thub.service.PlayerService;
import com.alvaro.thub.service.impl.PlayerServiceImpl;
import com.alvaro.thub.utils.Results;

public class PlayerSearchController extends AbstractController {

	private PlayerService playerService = null;

	private PlayerSearchView view = null;



	public PlayerSearchController(PlayerSearchView view) {
		super("Buscar", new ImageIcon(PlayerSearchController.class.getResource("/nuvola/lupa16.png")));
		this.view = view;
		this.playerService  = new PlayerServiceImpl();
	}
	
	public void doAction() {
		PlayerCriteria criteria = view.getCriteria();
		Results<PlayerDTO> resultados = playerService.findByCriteria(criteria, 1, Integer.MAX_VALUE);
		view.setModel(resultados);
	}
	
}
