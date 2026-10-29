package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.TeamDTO;

public class TeamCBRenderer extends DefaultListCellRenderer {

    public TeamCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof TeamDTO) {
            TeamDTO team = (TeamDTO) value;
            if (team.getId() == null) {
                this.setText(team.getName()); // Mostrará "Todos los equipos" o "Seleccione un equipo"
            } else {
                this.setText(team.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}