package com.alvaro.thub.desktop.views.tableModel;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.PlayerView;
import com.alvaro.thub.model.PlayerDTO;
import com.alvaro.thub.desktop.controller.PlayerUpdateController;
import com.alvaro.thub.service.PlayerService;
import com.alvaro.thub.service.impl.PlayerServiceImpl;

public class PlayerButtonEditor extends AbstractCellEditor implements TableCellEditor {
    
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    private PlayerDTO jugador;
    private JTable table;
    
    public PlayerButtonEditor(JTable table) {
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setOpaque(true);
        
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");
        
        editButton.addActionListener(e -> {
            PlayerView playerView = new PlayerView();
            playerView.setModel(jugador);
            playerView.setEditable(true);
            playerView.setAgreeController(new PlayerUpdateController(playerView));
            MainWindow.getInstance().addClosableView(jugador.getName(), playerView);
            fireEditingStopped();
        });
        
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(table,
                    "¿Eliminar el jugador \"" + jugador.getName() + " " + jugador.getLastName1() + "\"?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                PlayerService playerService = new PlayerServiceImpl();
                if (playerService.delete(jugador.getId())) {
                    ((PlayerTableModel) table.getModel()).getJugadores().remove(jugador);
                    ((PlayerTableModel) table.getModel()).fireTableDataChanged();
                    JOptionPane.showMessageDialog(table, "Jugador eliminado");
                } else {
                    JOptionPane.showMessageDialog(table, "Error al eliminar");
                }
            }
            fireEditingStopped();
        });
        
        panel.add(editButton);
        panel.add(deleteButton);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.jugador = (PlayerDTO) value;
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return jugador;
    }
}