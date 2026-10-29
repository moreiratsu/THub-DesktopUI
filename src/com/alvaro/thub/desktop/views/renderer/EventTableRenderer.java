package com.alvaro.thub.desktop.views.renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.alvaro.thub.model.EventDTO;

/**
 * Renderer personalizado para mostrar los datos de un evento en una tabla. 
 * Cada columna muestra un atributo diferente del evento, y se aplican colores para resaltar la fila seleccionada o sobre la que está el ratón (hover).
 */

public class EventTableRenderer implements TableCellRenderer {
	
	private static final DateFormat FECHA_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	// Color para la fila cuando el ratón está encima
    private static final Color HOVER_COLOR = new Color(230, 240, 255); // Elegimos aqui el color
    
    private int hoverRow = -1; // Fila sobre la que está el ratón
    
    public void setHoverRow(int row) {
        this.hoverRow = row;
    }
	
	// TODO: Optimización Singleton
	public EventTableRenderer() {
	}

	public Component getTableCellRendererComponent(
			JTable table, Object value,
			boolean isSelected, boolean hasFocus,
			int row, int column) {
		EventDTO e = (EventDTO) value;
		Component c = null;
		if (value!=null) {
			if (column==0) {
				JLabel idLabel = new JLabel(e.getId().toString());
				c = idLabel;
			} else if (column==1) {
				JLabel nombreLabel = new JLabel(e.getName());
				c = nombreLabel;
			} else if (column==2) {
				JLabel estadoLabel = new JLabel(e.getStatusName());
				c = estadoLabel;
			} else if (column==3) {
				JLabel fechaInicioLabel = new JLabel(FECHA_DATE_FORMAT.format(e.getStartDate()));
				c = fechaInicioLabel;
			} 	else if (column==4) {
				JLabel fechaFinLabel = new JLabel(FECHA_DATE_FORMAT.format(e.getEndDate()));
				c = fechaFinLabel;
			}	else if (column==5) {
				JLabel formatoLabel = new JLabel(e.getFormatName());
				c = formatoLabel;
			} else if (column==6) {
				JLabel paisLabel = new JLabel(e.getCountryName());
				c = paisLabel;
			} else if (column==7) {
				JLabel provinciaLabel = new JLabel(e.getProvinceName());
				c = provinciaLabel;
			} else if (column==8) {
				JLabel localidadLabel = new JLabel(e.getLocalityName());
				c = localidadLabel;
			} else if (column==9) {
				JLabel organizadorLabel = new JLabel(e.getCreatorUserName());
				c = organizadorLabel;					
			} else {
				c = new JLabel("Próximamente...");
			}	
			
			
			// Aplicar colores según está SELECCIONADA UNA FILA o HOVER (SOBRE ELLA EL RATÓN)
			((JComponent) c).setOpaque(true); // Necesario para que el fondo se pinte correctamente, c en este casio es un JLabel, que es un JComponent, no un Component
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else if (row == hoverRow) {
                c.setBackground(HOVER_COLOR);
                c.setForeground(table.getForeground());
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }
			
			
		}
		return c;
	}

}

