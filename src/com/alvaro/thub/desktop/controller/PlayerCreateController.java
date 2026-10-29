package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.PlayerView;
import com.alvaro.thub.model.PlayerDTO;
import com.alvaro.thub.service.PlayerService;
import com.alvaro.thub.service.impl.PlayerServiceImpl;

/**
 * Controlador para manejar la CREACION DE UN NUEVO JUGADOR.
 * 
 * Este controlador se encarga de recoger los datos del formulario, 
 * llamar al servicio para crear el jugador y manejar la respuesta.
 */

public class PlayerCreateController extends AbstractController {

    private PlayerView view = null;
    
    private PlayerService playerService = null;
    
    public PlayerCreateController(PlayerView view) {
        super(view, "Guardar", new ImageIcon(PlayerCreateController.class.getResource("/nuvola/guardarx16.png")));
        this.view = view;
        this.playerService = new PlayerServiceImpl();
    }
    
    public void doAction() {
        PlayerDTO player = view.getModel();
        
        Long playerId = playerService.create(player);
        
        if (playerId != null) {
            JOptionPane.showMessageDialog(view, 
                    "Jugador creado correctamente con ID: " + playerId, 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            view.setEditable(false);
            view.setAgreeController(new PlayerSetEditableController(view));
            
            // Opcional: limpiar el formulario
            // limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(view, 
                    "Error al crear el jugador", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
