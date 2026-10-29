package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.alvaro.thub.desktop.views.View;


public abstract class AbstractController extends AbstractAction implements ItemListener, KeyListener, PropertyChangeListener, Controller {

private View view = null;
	
	public AbstractController() {			
	}
	
	public AbstractController(String name) {
		super(name);
	}
	
	public AbstractController(String name, Icon icon) {
		super(name, icon);
	}
	
	public AbstractController(View view, String name, Icon icon) {
		super(name, icon);
		this.view = view;
	}
	
	public AbstractController(View view) {
		this.view = view;
	}

	@Override
	public View getView() {
		return this.view;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
    	doAction();
    }
    
	// buscar cuando seleccione una opcion en un combobox
	@Override
    public void itemStateChanged(ItemEvent e) {
		doAction();
    }

	// buscar cuando escriba por lo menos 3 caracteres en un textField
	@Override
	public void keyTyped(KeyEvent e) {
		 doAction();		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		 doAction();
	}


	@Override
	public void keyReleased(KeyEvent e) {
		doAction();		
	}

	// buscar cuando se cambia un jdatechooser
		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if (e.getPropertyName().equalsIgnoreCase("date")) {
				doAction();
			}
			
		}
}
