package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alvaro.thub.desktop.views.PlayerView;
import com.alvaro.thub.model.PlayerDTO;
import com.alvaro.thub.service.PlayerService;
import com.alvaro.thub.service.impl.PlayerServiceImpl;

/**
 * Controlador para ACTUALIZAR un jugador existente.
 * 
 * Este controlador se encarga de tomar los datos del formulario de edición, validar la información 
 * y llamar al servicio para actualizar el jugador en la base de datos.
 * Después de la actualización, muestra un mensaje de éxito o error según corresponda.
 */

public class PlayerUpdateController extends AbstractController {
    
    private PlayerView view = null;
    private PlayerService playerService = null;

    public PlayerUpdateController(PlayerView view) {
        super(view, "Actualizar", new ImageIcon(PlayerUpdateController.class.getResource("/nuvola/actualizarx16.png")));
        this.view = view;
        this.playerService = new PlayerServiceImpl();
    }

    public void doAction() {
        PlayerDTO player = view.getModel();

        boolean updated = playerService.update(player);
        
        if (updated) {
            JOptionPane.showMessageDialog(view, 
                    "Jugador " + player.getName() + " actualizado correctamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            view.setEditable(false);
            view.setAgreeController(new PlayerSetEditableController(view));
            
            // Opcional: limpiar el formulario
            // limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(view, 
                    "Error al actualizar el jugador", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}