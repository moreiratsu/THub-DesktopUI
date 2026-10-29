package com.alvaro.thub.desktop.controller;

import com.alvaro.thub.desktop.views.View;

/**
 * Interface comun a todos los controladores.
 * 
 */
public interface Controller {
	
	public View getView();

	public void doAction();

}
