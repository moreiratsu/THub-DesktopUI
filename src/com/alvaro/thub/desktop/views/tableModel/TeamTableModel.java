package com.alvaro.thub.desktop.views.tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.alvaro.thub.model.TeamDTO;

/**
 * Modelo de tabla para mostrar equipos deportivos.
 * Cada fila representa un equipo y cada columna muestra un atributo del equipo.
 */

public class TeamTableModel extends AbstractTableModel {
    
    public static final String[] COLUMN_NAMES = new String[] {
            "Id", "Nombre", "Siglas", "Deporte", "Localidad", "Provincia", "País", "Acciones"
    };
    
    private List<TeamDTO> equipos = null;
    
    public TeamTableModel() {
        setEquipos(new ArrayList<TeamDTO>());
    }
    
    public TeamTableModel(List<TeamDTO> equipos) {
        setEquipos(equipos);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        TeamDTO equipo = equipos.get(row);
        
       // Devolvemos el objeto completo para que el renderer pueda extraer los datos necesarios
		return equipo;
    }
    
    @Override
    public int getRowCount() {
        return equipos == null ? 0 : equipos.size();
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
        return column == 7; 
    }
    
    public List<TeamDTO> getEquipos() {
        return equipos;
    }
    
    public void setEquipos(List<TeamDTO> equipos) {
        this.equipos = equipos;
        fireTableDataChanged(); 
    }
}
