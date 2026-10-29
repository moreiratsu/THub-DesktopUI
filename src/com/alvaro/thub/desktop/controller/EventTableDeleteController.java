package com.alvaro.thub.desktop.controller;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.alvaro.thub.desktop.views.tableModel.EventTableModel;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.service.EventService;
import com.alvaro.thub.service.impl.EventServiceImpl;

public class EventTableDeleteController extends AbstractController {
    
    private JTable table;
    private EventDTO evento;
    
    public EventTableDeleteController(JTable table, EventDTO evento) {
        super(null, "Eliminar", new ImageIcon(EventTableDeleteController.class.getResource("/nuvola/eliminarx16.png")));
        this.table = table;
        this.evento = evento;
    }
    
    @Override
    public void doAction() {
        int confirm = JOptionPane.showConfirmDialog(table,
                "¿Eliminar el evento \"" + evento.getName() + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            EventService eventService = new EventServiceImpl();
            boolean deleted = eventService.delete(evento.getId());
            
            if (deleted) {
                EventTableModel model = (EventTableModel) table.getModel();
                model.getEventos().remove(evento);
                model.fireTableDataChanged();
                JOptionPane.showMessageDialog(table, "Evento eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(table, "Error al eliminar el evento");
            }
        }
    }
}