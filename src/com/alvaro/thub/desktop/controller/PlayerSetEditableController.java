package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.alvaro.thub.desktop.views.PlayerView;

/**
 * Este controlador se encarga de cambiar la vista del jugador a modo editable.
 * 
 * permitiendo al usuario modificar los datos del jugador. Además, asigna un nuevo controlador 
 * para manejar la acción de guardar los cambios realizados.
 */

public class PlayerSetEditableController extends AbstractController {

    private PlayerView view = null;

    public PlayerSetEditableController(PlayerView view) {
        super(view, "Editar", new ImageIcon(PlayerSetEditableController.class.getResource("/nuvola/editarx16.png")));
    }

    public void doAction() {
        PlayerView view = (PlayerView) getView();
        view.setEditable(true);
        view.setAgreeController(new PlayerUpdateController(view));
    }
}