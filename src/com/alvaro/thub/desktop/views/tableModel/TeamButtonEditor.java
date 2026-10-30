package com.alvaro.thub.desktop.views.tableModel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.model.TeamDTO;
import com.alvaro.thub.desktop.controller.TeamUpdateController;
import com.alvaro.thub.desktop.views.TeamCreateView;
import com.alvaro.thub.service.TeamService;
import com.alvaro.thub.service.impl.TeamServiceImpl;

public class TeamButtonEditor extends AbstractCellEditor implements TableCellEditor {
    
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;
    private TeamDTO equipo;
    private JTable table;
    
    public TeamButtonEditor(JTable table) {
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setOpaque(true);
        
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");
        
        editButton.addActionListener(e -> {
            TeamCreateView teamView = new TeamCreateView();
            teamView.setModel(equipo);
            teamView.setEditable(true);
            teamView.setAgreeController(new TeamUpdateController(teamView));
            MainWindow.getInstance().addClosableView(equipo.getName(), teamView);
            fireEditingStopped();
        });
        
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(table,
                    "¿Eliminar el equipo \"" + equipo.getName() + "\"?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                TeamService teamService = new TeamServiceImpl();
                if (teamService.delete(equipo.getId())) {
                    ((TeamTableModel) table.getModel()).getEquipos().remove(equipo);
                    ((TeamTableModel) table.getModel()).fireTableDataChanged();
                    JOptionPane.showMessageDialog(table, "Equipo eliminado");
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
        this.equipo = (TeamDTO) value;
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return equipo;
    }
}
