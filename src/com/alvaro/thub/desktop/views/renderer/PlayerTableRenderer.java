package com.alvaro.thub.desktop.views.renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.alvaro.thub.model.PlayerDTO;

public class PlayerTableRenderer implements TableCellRenderer{

	private static final DateFormat FECHA_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final Color HOVER_COLOR = new Color(230, 240, 255);
	private int hoverRow = -1;

	public void setHoverRow(int row) {
		this.hoverRow = row;
	}

	public PlayerTableRenderer() {
	}

	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		PlayerDTO p = (PlayerDTO) value;
		Component c = null;
		
		if (value != null) {
			if(column == 0) {
				JLabel idLabel = new JLabel(p.getId().toString());
				c = idLabel;
			} else if(column == 1) {
				JLabel dniLabel = new JLabel(p.getDni());
				c = dniLabel;	
			}else if(column == 2) {
				JLabel nameLabel = new JLabel(p.getName());
				c = nameLabel;
			}else if(column == 3) {
				JLabel apellido1 = new JLabel(p.getLastName1());
				c = apellido1;
			} else if(column == 4) {
				JLabel apellido2 = new JLabel(p.getLastName2());
				c = apellido2;
			} else if(column == 5) {
				JLabel genderLabel = new JLabel(p.getGenderName());
				c = genderLabel;
			} else if(column == 6) {
				JLabel birthDateLabel = new JLabel(p.getBornDate().toString());
				c = birthDateLabel;
			} else if(column == 7) {
				JLabel localityLabel = new JLabel(p.getLocalityName());
				c = localityLabel;
			} else if(column == 8) {
				JLabel provinceLabel = new JLabel(p.getProvinceName());
				c = provinceLabel;
			} else if(column == 9) {
				JLabel countryLabel = new JLabel(p.getCountryName());
				c = countryLabel;
			} else if(column == 10) {
				JLabel teamLabel = new JLabel(p.getTeamName());
				c = teamLabel;
			} else {
				c = new JLabel("Próximamente...");
			}
			
            ((JComponent) c).setOpaque(true);
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

