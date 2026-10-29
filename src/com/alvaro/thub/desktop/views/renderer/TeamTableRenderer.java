package com.alvaro.thub.desktop.views.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.alvaro.thub.model.TeamDTO;

/**
 * Renderer personalizado para mostrar (PINTAR) los datos de un equipo en una tabla.
 * recibe un objeto TeamDTO entero y lo trocea para mostrar cada campo en su columna correspondiente.
 */

public class TeamTableRenderer implements TableCellRenderer{

	private static final Color HOVER_COLOR = new Color(230, 240, 255);
	private int hoverRow = -1; 
	
	public void setHoverRow(int row) {
		this.hoverRow = row;
	}
	
	public TeamTableRenderer() {
	}

	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		TeamDTO equipo = (TeamDTO) value;
		Component c = null;
		if (value != null) {
			if(column == 0) {
				JLabel idLabel = new JLabel(equipo.getId().toString());
				c = idLabel;
			}else if(column == 1) {
				JLabel nameLabel = new JLabel(equipo.getName());
				c = nameLabel;
			}else if(column == 2) {
				JLabel initialismLabel = new JLabel(equipo.getInitialism());
				c = initialismLabel;
			} else if(column == 3) {
				JLabel sportLabel = new JLabel(equipo.getSportName());
				c = sportLabel;
			} else if(column == 4) {
				JLabel locationLabel = new JLabel(equipo.getLocalityName());
				c = locationLabel;
			} else if(column == 5) {
				JLabel provinceLabel = new JLabel(equipo.getProvinceName());
				c = provinceLabel;
			} else if(column == 6) {
				JLabel countryLabel = new JLabel(equipo.getCountryName());
				c = countryLabel;
			} else {
				c = new JLabel("Próximamente...");
			}
			
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
