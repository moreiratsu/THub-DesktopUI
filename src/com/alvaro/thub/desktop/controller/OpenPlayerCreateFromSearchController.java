package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.PlayerView;
import com.alvaro.thub.desktop.views.View;

public class OpenPlayerCreateFromSearchController extends AbstractController {
    
    
    public OpenPlayerCreateFromSearchController() {
		super(null, "Nuevo", new ImageIcon(PlayerCreateController.class.getResource("/nuvola/16x16/1294_doctor_medical_dentist_health_health_medical_dentist_doctor.png")));
		
	}

	public void doAction() {
        PlayerView playerView = new PlayerView();
        playerView.setEditable(true);
        MainWindow.getInstance().addClosableView("Nuevo Participante", playerView);
    }
}
