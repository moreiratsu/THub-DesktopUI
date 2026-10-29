package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.UserSearchView;

public class OpenUserSearchViewController extends AbstractController{
	
	public OpenUserSearchViewController() {	
	}

	public void doAction() {
		UserSearchView userSearchView = new UserSearchView();
		MainWindow.getInstance().addClosableView(userSearchView.getName(), userSearchView);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
