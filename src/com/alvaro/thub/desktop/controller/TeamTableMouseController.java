package com.alvaro.thub.desktop.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.TeamCreateView;
import com.alvaro.thub.desktop.views.renderer.ButtonRenderer;
import com.alvaro.thub.desktop.views.renderer.TeamTableRenderer;
import com.alvaro.thub.model.TeamDTO;

/**
 * Controlador para manejar eventos del ratón en la tabla de equipos.
 * Clicks, movimiento...
 */

public class TeamTableMouseController extends MouseAdapter {
    // TODO: 2026/04/16
	
	private int currentHoverRow = -1; 
	
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            javax.swing.JTable table = (javax.swing.JTable) e.getSource();
            int selectedRow = table.rowAtPoint(e.getPoint());
            int selectedColumn = table.columnAtPoint(e.getPoint());
            
            if (selectedRow != -1 && selectedColumn != -1) {
                TeamDTO cellValue = (TeamDTO) table.getValueAt(selectedRow, selectedColumn);
                TeamCreateView teamView = new TeamCreateView();
                teamView.setModel(cellValue);
                teamView.setEditable(false);
                teamView.setAgreeController(new TeamSetEditableController(teamView));
                MainWindow.getInstance().addClosableView(cellValue.getName(), teamView);
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());

        if (row != currentHoverRow) {
            currentHoverRow = row;
            
            TeamTableRenderer renderer = (TeamTableRenderer) table.getDefaultRenderer(Object.class);
            if (renderer != null) {
                renderer.setHoverRow(row);
            }
            
            ButtonRenderer buttonRenderer = (ButtonRenderer) table.getColumnModel().getColumn(7).getCellRenderer();
            if (buttonRenderer != null) {
                buttonRenderer.setHoverRow(row);
            }
            
            table.repaint();
        }
    }
	
	@Override
    public void mouseExited(MouseEvent e) {
        if (currentHoverRow != -1) {
            currentHoverRow = -1;
            
            TeamTableRenderer renderer = (TeamTableRenderer) ((JTable) e.getSource()).getDefaultRenderer(Object.class);
            if (renderer != null) {
                renderer.setHoverRow(-1);
                ((JTable) e.getSource()).repaint();
            }
        }
    }
}
