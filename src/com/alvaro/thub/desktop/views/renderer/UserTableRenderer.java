package com.alvaro.thub.desktop.views.renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.alvaro.thub.model.UserDTO;

public class UserTableRenderer implements TableCellRenderer{

	private static final DateFormat FECHA_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	private static final Color HOVER_COLOR = new Color(230, 240, 255); // Elegimos aqui el color

	private int hoverRow = -1; // Fila sobre la que está el ratón

	public void setHoverRow(int row) {
		this.hoverRow = row;
	}

	public UserTableRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		UserDTO usuario = (UserDTO) value;
		Component c = null;
		if (value != null) {
			if(column == 0) {
				JLabel idLabel = new JLabel(usuario.getId().toString());
				c = idLabel;
			}else if(column == 1) {
				JLabel dniLabel = new JLabel(usuario.getDni());
				c = dniLabel;
			}else if(column == 2) {
				JLabel nameLabel = new JLabel(usuario.getName());
				c = nameLabel;
			} else if(column == 3) {
				JLabel apellido1 = new JLabel(usuario.getLastName1());
				c = apellido1;
			} else if(column == 4) {
				JLabel apellido2 = new JLabel(usuario.getLastName2());
				c = apellido2;
			} else if(column == 5) {
				JLabel fechaNac = new JLabel(usuario.getBornDate().toString());
				c = fechaNac;
			} else if(column == 6) {
				JLabel email = new JLabel(usuario.getEmail());
				c = email;
			} else if(column == 7) {
				JLabel telefono = new JLabel(usuario.getPhoneNumber());
				c = telefono;
			} else if(column == 8) {
				JLabel genero = new JLabel(usuario.getGenderName().toString());
				c = genero;
			} else if(column == 9) {
				JLabel localidad = new JLabel(usuario.getLocalityName());
				c = localidad;
			} else if(column == 10) {
				JLabel provincia = new JLabel(usuario.getProvinceName());
				c = provincia;
			} else if (column == 11) {
				JLabel pais = new JLabel(usuario.getCountryName());
				c = pais;
			} else if(column == 12) {
				JLabel rol = new JLabel(usuario.getRolName().toString());
				c = rol;
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


