package com.alvaro.thub.desktop.views.tableModel;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.alvaro.thub.model.UserDTO;

public class UserTableModel extends AbstractTableModel {
	
	public static final String[] COLUMN_NAMES = new String[] {
			"Id", "DNI", "Nombre", "1er Apellido", "2º Apellido", 
			"Fecha Nac.", "Email", "Teléfono", "Género", "Localidad", "Provincia", "País", "Rol", "Acciones" 
	};
	
	private List<UserDTO> usuarios = null;
	
	public UserTableModel() {
		setUsuarios(new ArrayList<UserDTO>());
	}
	
	public UserTableModel(List<UserDTO> usuarios) {
		setUsuarios(usuarios);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		UserDTO usuario = usuarios.get(row);
		
		// Devolvemos el objeto completo para que el renderer pueda extraer los datos necesarios
		return usuario;
	}
	
	@Override
	public int getRowCount() {
		return usuarios == null ? 0 : usuarios.size();
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	public List<UserDTO> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<UserDTO> usuarios) {
		this.usuarios = usuarios;
		fireTableDataChanged(); // Notificar a la tabla que los datos han cambiado
	}

	
}