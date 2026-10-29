package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.Rol;

public class RolCBRenderer extends DefaultListCellRenderer {

    public RolCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof Rol) {
            Rol rol = (Rol) value;
            if (rol.getId() == null) {
                this.setText(rol.getName()); // Mostrará "Seleccione un rol" o "Todos los roles"
            } else {
                this.setText(rol.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}