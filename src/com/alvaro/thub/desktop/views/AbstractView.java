package com.alvaro.thub.desktop.views;

import javax.swing.JPanel;

/**
 * Implementación común de base de las vistas.
 */
public abstract class AbstractView extends JPanel implements View {

	private String name = null;
	
	public AbstractView() {
	}
	
	public AbstractView(String name) {
		setName(name);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
}
