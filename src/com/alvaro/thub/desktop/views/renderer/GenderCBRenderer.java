package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import com.alvaro.thub.model.Gender;

public class GenderCBRenderer extends DefaultListCellRenderer {

    public GenderCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof Gender) {
            Gender genero = (Gender) value;
            if (genero.getId() == null) {
                this.setText(genero.getName());
                this.setIcon(null);
            } else {
                this.setText(genero.getName());
                
                // Cargar el icono para este género
                ImageIcon icon = getIconForGender(genero.getName());
                if (icon != null) {
                    this.setIcon(icon);
                }
            }
        } else if (value instanceof String) {
            this.setText((String) value);
            this.setIcon(null);
        } else {
            this.setText("Seleccionar");
            this.setIcon(null);
        }
        return this;
    }
    
    // Método para obtener el icono de cada género
    private ImageIcon getIconForGender(String genderName) {
        if (genderName == null) return null;
        
        String lowerName = genderName.toLowerCase();
        String iconPath = null;
        
        // Mapear géneros a iconos
        if (lowerName.contains("masculino") || lowerName.contains("macho") || lowerName.equals("m")) {
            iconPath = "/nuvola/16x16/macho.png";
        } else if (lowerName.contains("femenino") || lowerName.contains("hembra") || lowerName.equals("f")) {
            iconPath = "/nuvola/16x16/hembra.png";
        } else {
            // Icono por defecto
            iconPath = "/nuvola/16x16/1339_kmag_kmag.png";
        }
        
        try {
            java.net.URL url = getClass().getResource(iconPath);
            if (url != null) {
                ImageIcon originalIcon = new ImageIcon(url);
                // Escalar a 16x16
                Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("Icono no encontrado: " + iconPath);
            }
        } catch (Exception e) {
            System.err.println("Error cargando icono: " + iconPath + " - " + e.getMessage());
        }
        
        return null;
    }
}