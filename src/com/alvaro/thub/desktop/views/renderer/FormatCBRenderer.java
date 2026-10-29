package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.Format;

public class FormatCBRenderer extends DefaultListCellRenderer {

    public FormatCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof Format) {
            Format format = (Format) value;
            if (format.getId() == null) {
                this.setText(format.getName()); // Mostrará "Seleccione un formato"
            } else {
                this.setText(format.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}