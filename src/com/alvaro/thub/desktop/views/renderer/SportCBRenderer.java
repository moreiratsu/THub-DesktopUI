package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import com.alvaro.thub.model.Sport;

public class SportCBRenderer extends DefaultListCellRenderer {

    public SportCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof Sport) {
            Sport deporte = (Sport) value;
            if (deporte.getName() == null) {
                this.setText(deporte.getName()); // Mostrará "Seleccione un deporte"
                this.setIcon(null);
            } else {
                this.setText(deporte.getName());
                
                // Cargar el icono para este deporte
                ImageIcon icon = getIconForSport(deporte.getName());
                if (icon != null) {
                    this.setIcon(icon);
                }
            }
        } else {
            this.setText("Seleccionar");
            this.setIcon(null);
        }
        return this;
    }
    
    // Método para obtener el icono de cada deporte
    private ImageIcon getIconForSport(String sportName) {
		if (sportName == null) return null;
        
        String lowerName = sportName.toLowerCase();
        String iconPath = null;
        
        // Mapear deportes a iconos - TODOS EN LA MISMA CARPETA 16x16
        if (lowerName.equals("fútbol") || lowerName.equals("futbol")) { //aqui uso equals para que no coja futbolin
            iconPath = "/nuvola/16x16/futbol-americano.png";
        } else if (lowerName.contains("baloncesto")) {
            iconPath = "/nuvola/16x16/baloncesto.png";
        } else if (lowerName.contains("tenis")) {
            iconPath = "/nuvola/16x16/tenis.png";
        } else if (lowerName.contains("natación")) {
            iconPath = "/nuvola/16x16/piscina.png";
        } else if (lowerName.contains("atletismo")) {
            iconPath = "/nuvola/16x16/correr.png";
        } else if (lowerName.contains("boxeo")) {
            iconPath = "/nuvola/16x16/guantes-de-boxeo.png";
        } else if (lowerName.contains("voleibol")) {
            iconPath = "/nuvola/16x16/voleibol.png";  
        } else if (lowerName.contains("waterpolo")) {
            iconPath = "/nuvola/16x16/waterpolo.png";
        } else if (lowerName.contains("pádel")) {
            iconPath = "/nuvola/16x16/raqueta-de-padel.png";
        } else if (lowerName.contains("judo")) {
            iconPath = "/nuvola/16x16/karate.png";
        } else if (lowerName.contains("ping pong") || lowerName.contains("ping-pong")) {
            iconPath = "/nuvola/16x16/ping-pong.png";  
        } else if (lowerName.contains("futbolin")) {
            iconPath = "/nuvola/16x16/futbolin.png";
        } else if (lowerName.contains("tiro con arco")) {
            iconPath = "/nuvola/16x16/tiro-al-arco.png";
        } else {
            // Icono por defecto - usa un icono que seguro existe
            iconPath = "/nuvola/16x16/1339_kmag_kmag.png"; // El icono de búsqueda como fallback
        }
        
        try {
            java.net.URL url = getClass().getResource(iconPath);
            if (url != null) {
                ImageIcon originalIcon = new ImageIcon(url);
                // Escalar a 16x16 por si acaso
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