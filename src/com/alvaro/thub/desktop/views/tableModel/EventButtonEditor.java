package com.alvaro.thub.desktop.views.tableModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.FlowLayout;

import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.desktop.controller.EventTableDeleteController;
import com.alvaro.thub.desktop.controller.EventTableEditController;

/**
 * Este editor se encarga de manejar los eventos de los botones de editar y eliminar en la columna de acciones de la tabla de eventos. 
 * Es decir que se encarga las FUNCIONALIDADES de abrir la vista editable del evento al hacer clic en el botón "Editar", y de eliminar el evento al hacer clic en el botón "Borrar".
 */

public class EventButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	private JPanel panel;
	private JButton editButton;
	private JButton deleteButton;
	private EventDTO evento;
	private JTable table;

	public EventButtonEditor(JTable table) {
		
		this.table = table;
		
		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
		panel.setOpaque(true);

		editButton = new JButton("Editar");
		deleteButton = new JButton("Borrar");


		panel.add(editButton);
		panel.add(deleteButton);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.evento = (EventDTO) value;

		// Asignar los controladores aquí (para que tengan el evento actual)
		editButton.addActionListener(new EventTableEditController(table, row, evento));
		deleteButton.addActionListener(new EventTableDeleteController(table, evento));

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
		return panel;
	}


	@Override
	public Object getCellEditorValue() {
		return evento;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}