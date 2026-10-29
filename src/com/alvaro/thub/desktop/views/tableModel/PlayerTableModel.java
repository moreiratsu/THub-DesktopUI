package com.alvaro.thub.desktop.views.tableModel;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.alvaro.thub.model.PlayerDTO;

public class PlayerTableModel extends AbstractTableModel {
    
    public static final String[] COLUMN_NAMES = new String[] {
            "Id", "DNI", "Nombre", "1er Apellido", "2º Apellido", 
            "Género", "Fecha Nac.", "Localidad", "Provincia", "País", "Equipo" ,"Acciones"
    };
    
    private List<PlayerDTO> jugadores = null;
    
    public PlayerTableModel() {
        setJugadores(new ArrayList<PlayerDTO>());
    }
    
    public PlayerTableModel(List<PlayerDTO> jugadores) {
    	setJugadores(jugadores);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        PlayerDTO player = jugadores.get(row);
        
        // Devolvemos el objeto completo para que el renderer pueda extraer los datos necesarios
        return player;
    }
    
    
    @Override
    public int getRowCount() {
        return jugadores == null ? 0 : jugadores.size();
    }
    
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 11; 
    }
    
    public List<PlayerDTO> getJugadores() {
        return jugadores;
    }
    
    public void setJugadores(List<PlayerDTO> jugadores) {
        this.jugadores = jugadores;
        fireTableDataChanged();
    }
}