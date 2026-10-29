package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.Status;

public class StatusCBRenderer extends DefaultListCellRenderer {

    public StatusCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof Status) {
            Status status = (Status) value;
            if (status.getId() == null) {
                this.setText(status.getName()); // Mostrará "Seleccione un estado" o "Todos los estados"
            } else {
                this.setText(status.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}