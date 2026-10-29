package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.LocalityDTO;

public class LocalityCBRenderer extends DefaultListCellRenderer {

    public LocalityCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof LocalityDTO) {
        	LocalityDTO localidad = (LocalityDTO) value;
            if (localidad.getId() == null) {
                this.setText(localidad.getName()); // Mostrará "Seleccione una localidad"
            } else {
                this.setText(localidad.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}
