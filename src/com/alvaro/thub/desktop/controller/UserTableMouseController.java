package com.alvaro.thub.desktop.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.desktop.views.UserView;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.EventTableRenderer;
import com.alvaro.thub.desktop.views.renderer.UserTableRenderer;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.model.UserDTO;

public class UserTableMouseController extends MouseAdapter {
	
	private int currentHoverRow = -1;
	
	// Método para manejar el doble clic en una celda de la tabla
		@Override
	    public void mouseClicked(MouseEvent e) {
	        if (e.getClickCount() == 2) {
	            JTable table = (JTable) e.getSource();
	            int selectedRow = table.rowAtPoint(e.getPoint());
	            int selectedColumn = table.columnAtPoint(e.getPoint());
	            
	            if (selectedRow != -1 && selectedColumn != -1) {
	                UserDTO cellValue = (UserDTO) table.getValueAt(selectedRow, selectedColumn);
	                UserView userView = new UserView();
	                userView.setModel(cellValue);
	                userView.setEditable(false);
	                userView.setAgreeController(new UserSetEditableController(userView));
	                MainWindow.getInstance().addClosableView(cellValue.getName(), userView);
	            }
	        }
	    }
		
		
		// Método para manejar el movimiento del mouse y actualizar la fila hover
		@Override
	    public void mouseMoved(MouseEvent e) {
	        JTable table = (JTable) e.getSource();
	        int row = table.rowAtPoint(e.getPoint());

	        if (row != currentHoverRow) {
	            currentHoverRow = row;
	            
	         // Actualizar EventTableRenderer
	            
	            UserTableRenderer renderer = (UserTableRenderer) table.getDefaultRenderer(Object.class);
	            if (renderer != null) {
	                renderer.setHoverRow(row);
	            }
	            
	            // Actualizar ButtonRenderer (columna 10)
	            ButtonRenderer buttonRenderer = (ButtonRenderer) table.getColumnModel().getColumn(10).getCellRenderer();
	            if (buttonRenderer != null) {
	                buttonRenderer.setHoverRow(row);
	            }
	            
	            table.repaint();
	        }
	    }
		
		
		// Método para manejar la salida del mouse de la tabla y restablecer la fila hover
		@Override
	    public void mouseExited(MouseEvent e) {
	        if (currentHoverRow != -1) {
	            currentHoverRow = -1;
	            
	            UserTableRenderer renderer = (UserTableRenderer) ((JTable) e.getSource()).getDefaultRenderer(Object.class);
	            if (renderer != null) {
	                renderer.setHoverRow(-1);
	                ((JTable) e.getSource()).repaint();
	            }
	        }
	    }
	
}
