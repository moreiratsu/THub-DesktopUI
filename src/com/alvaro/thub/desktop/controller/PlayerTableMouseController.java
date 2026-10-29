package com.alvaro.thub.desktop.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.PlayerView;
import com.alvaro.thub.desktop.views.renderer.PlayerTableRenderer;
import com.alvaro.thub.model.PlayerDTO;

public class PlayerTableMouseController extends MouseAdapter {
	
    private int currentHoverRow = -1;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            javax.swing.JTable table = (javax.swing.JTable) e.getSource();
            int selectedRow = table.rowAtPoint(e.getPoint());
            int selectedColumn = table.columnAtPoint(e.getPoint());
            
            if (selectedRow != -1 && selectedColumn != -1) {
                PlayerDTO cellValue = (PlayerDTO) table.getValueAt(selectedRow, selectedColumn);
                PlayerView playerView = new PlayerView();
                playerView.setModel(cellValue);
                playerView.setEditable(false);
                playerView.setAgreeController(new PlayerSetEditableController(playerView));
                MainWindow.getInstance().addClosableView(cellValue.getName(), playerView);
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        
        if (row != currentHoverRow) {
            currentHoverRow = row;
            
            PlayerTableRenderer renderer = (PlayerTableRenderer) table.getDefaultRenderer(Object.class);
            if (renderer != null) {
                renderer.setHoverRow(row);
                table.repaint();
            }
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (currentHoverRow != -1) {
            currentHoverRow = -1;
            
            PlayerTableRenderer renderer = (PlayerTableRenderer) ((JTable) e.getSource()).getDefaultRenderer(Object.class);
            if (renderer != null) {
                renderer.setHoverRow(-1);
                ((JTable) e.getSource()).repaint();
            }
        }
    }
}