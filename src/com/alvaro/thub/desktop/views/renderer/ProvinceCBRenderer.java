package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.ProvinceDTO;

public class ProvinceCBRenderer extends DefaultListCellRenderer {

    public ProvinceCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof ProvinceDTO) {
        	ProvinceDTO provincia = (ProvinceDTO) value;
            if (provincia.getId() == null) {
                this.setText(provincia.getName()); // Mostrará "Seleccione una provincia"
            } else {
                this.setText(provincia.getName());
            }
        } else {
            this.setText("Seleccionar");
        }
        return this;
    }
}