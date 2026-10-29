package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.alvaro.thub.desktop.views.tableModel.UserTableModel;
import com.alvaro.thub.model.UserDTO;
import com.alvaro.thub.service.UserService;
import com.alvaro.thub.service.impl.UserServiceImpl;

public class UserTableDeleteController extends AbstractController {

	private JTable table;
	private UserDTO user;
	
	public UserTableDeleteController(JTable table, UserDTO user) {
		super(null, "Borrar", new ImageIcon(UserTableDeleteController.class.getResource("/nuvola/borrarx16.png")));
		this.table = table;
		this.user = user;
	}
	
	@Override
	public void doAction() {
		int confirm = JOptionPane.showConfirmDialog(table, 
				"¿Estás seguro de que quieres eliminar a " + user.getName() + "?", 
				"Confirmar eliminación", 
				JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			
			UserService userService = new UserServiceImpl();
			boolean deleted = userService.delete(user.getId());
			
			if (deleted) {
				UserTableModel model = (UserTableModel) table.getModel();
				model.getUsuarios().remove(user);
				model.fireTableDataChanged();
				JOptionPane.showMessageDialog(table, "Usuario eliminado correctamente");
				} else {
					JOptionPane.showMessageDialog(table, "Error al eliminar el usuario");
				}
		}
	}
}
