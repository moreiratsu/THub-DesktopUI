package com.alvaro.thub.desktop.views.tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.alvaro.thub.model.EventDTO;

public class EventTableModel extends AbstractTableModel {

	public static final String[] COLUMN_NAMES = new String[] {
			"Id", "Nombre", "Estado", "Fecha de Inicio", "Fecha de Fin", "Formato", "País", "Provincia", "Localidad","Organizador", "Acciones"
	};

	private List<EventDTO> eventos = null;

	public EventTableModel() {
		setEventos(new ArrayList<EventDTO>());
	}

	public EventTableModel(List<EventDTO> eventos) {
		setEventos(eventos);
	}

	@Override
	public Object getValueAt(int row, int col) {
		EventDTO evento = eventos.get(row);

		// Devolvemos el objeto completo para que el renderer pueda extraer los datos necesarios
		return evento;
	}


@Override
public int getRowCount() {
	return eventos == null ? 0 : eventos.size();
}

@Override
public int getColumnCount() {
	return COLUMN_NAMES.length;
}

@Override
public String getColumnName(int column) {
	return COLUMN_NAMES[column];
}

public List<EventDTO> getEventos() {
	return eventos;
}

// permite editar esa columna con la clase ButtonEditor, que abrirá la vista editable del evento al hacer clic en el botón "Editar"
@Override
public boolean isCellEditable(int row, int column) {
    return column == 10; // Solo la columna de acciones es editable
}

public void setEventos(List<EventDTO> eventos) {
	this.eventos = eventos;
	fireTableDataChanged(); // Notificar a la tabla que los datos han cambiado
}
}