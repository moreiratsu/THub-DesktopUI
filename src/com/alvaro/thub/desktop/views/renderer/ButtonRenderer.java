package com.alvaro.thub.desktop.views.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Este renderer se encarga de "PINTAR" la columna de acciones en la tabla de eventos, añadiendo botones de editar y eliminar,
 * o cualquier otra acción que se quiera implementar. Es un ejemplo básico, se podrían añadir más botones o personalizar su apariencia.
 * pero no tiene ninguna funcionalidad, para eso está el ButtonEditor, que se encarga de manejar los eventos de esos botones.
 */

// extiende de JPanel porque necesitamos un contenedor para los botones que queramos añadir.
public class ButtonRenderer extends JPanel implements TableCellRenderer {
	
	private static final Color HOVER_COLOR = new Color(230, 240, 255);
    private int hoverRow = -1;
    
    public void setHoverRow(int row) {
        this.hoverRow = row;
    }
	
	public ButtonRenderer() {
		// creamos el panel con un FlowLayout para que los botones se coloquen uno al lado del otro, centrados verticalmente.
		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0)); 
        setOpaque(true);
		
		// aquí añadimos los botones que queramos.
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Borrar");
        
        // Ajustar tamaño de los botones para que quepan bien
        editButton.setPreferredSize(new java.awt.Dimension(70, 25));
        deleteButton.setPreferredSize(new java.awt.Dimension(70, 25));
        
        add(editButton);
        add(deleteButton);
        
		/** Cargar imagenes seria asi
		editButton.setIcon(new ImageIcon(getClass().getResource("/nuvola/16x16/editarx16.png")));
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/nuvola/16x16/eliminarx16.png")));*/

		/** quitar el borde para que se vea solo el icono
		editButton.setBorder(null);
		deleteButton.setBorder(null);*/

		/** tooltip para saber qué hace cada botón
		editButton.setToolTipText("Editar");
		deleteButton.setToolTipText("Eliminar");*/
		

	}


	// Este método se llama cada vez que la tabla necesita renderizar una celda en la columna de acciones.
	// lo que hace simplemente es devolver el panel con los botones, y se encarga de cambiar el fondo si la fila está seleccionada o no.
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else if (row == hoverRow && hoverRow != -1) {
            setBackground(HOVER_COLOR);
        } else {
            setBackground(table.getBackground());
        }
        
        for (Component comp : getComponents()) {
            comp.setBackground(getBackground());
        }
        
        return this;
    }

      
}