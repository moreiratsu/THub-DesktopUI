package com.alvaro.thub.desktop.views.tableModel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import com.alvaro.thub.desktop.controller.UserTableDeleteController;
import com.alvaro.thub.desktop.controller.UserTableEditController;
import com.alvaro.thub.model.UserDTO;

public class UserButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	private JPanel panel;
	private JButton editButton;
	private JButton deleteButton;
	private UserDTO user;
	private JTable table;
	
	public UserButtonEditor(JTable table) {
		
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
		this.user = (UserDTO) value;

		// Asignar los controladores aquí (para que tengan el usuario actual)
		editButton.addActionListener(new UserTableEditController(table, row, user));
		deleteButton.addActionListener(new UserTableDeleteController(table, user));

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
		
		return panel;
	}
	
	@Override
	public Object getCellEditorValue() {
		return user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
