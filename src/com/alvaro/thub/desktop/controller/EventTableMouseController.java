package com.alvaro.thub.desktop.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.EventTableRenderer;
import com.alvaro.thub.model.Event;
import com.alvaro.thub.model.EventDTO;

/**
 * Controlador para manejar los eventos de mouse en la tabla de eventos. 
 * Permite abrir una vista detallada del evento al hacer doble clic en una celda.
 * También maneja el efecto hover para resaltar la fila sobre la que está el ratón.
 */

public class EventTableMouseController extends MouseAdapter {
    
	private int currentHoverRow = -1; // Fila sobre la que está el ratón
	
	
	// Método para manejar el doble clic en una celda de la tabla
	@Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JTable table = (JTable) e.getSource();
            int selectedRow = table.rowAtPoint(e.getPoint());
            int selectedColumn = table.columnAtPoint(e.getPoint());
            
            if (selectedRow != -1 && selectedColumn != -1) {
                EventDTO cellValue = (EventDTO) table.getValueAt(selectedRow, selectedColumn);
                EventView eventView = new EventView();
                eventView.setModel(cellValue);
                eventView.setEditable(false);
                eventView.setAgreeController(new EventSetEditableController(eventView));
                MainWindow.getInstance().addClosableView(cellValue.getName(), eventView);
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
            
            EventTableRenderer renderer = (EventTableRenderer) table.getDefaultRenderer(Object.class);
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
            
            EventTableRenderer renderer = (EventTableRenderer) ((JTable) e.getSource()).getDefaultRenderer(Object.class);
            if (renderer != null) {
                renderer.setHoverRow(-1);
                ((JTable) e.getSource()).repaint();
            }
        }
    }
}