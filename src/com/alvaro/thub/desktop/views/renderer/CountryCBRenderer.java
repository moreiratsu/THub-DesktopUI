package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.Country;

public class CountryCBRenderer extends DefaultListCellRenderer {

    public CountryCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof Country) {
        	Country pais = (Country) value;
            if (pais.getId() == null) {
                this.setText(pais.getName()); // Mostrará "Seleccione un país"
            } else {
                this.setText(pais.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}