package com.alvaro.thub.desktop.controller;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.model.EventDTO;

public class EventTableEditController extends AbstractController {
    
    private JTable table;
    private int row;
    private EventDTO evento;
    
    public EventTableEditController(JTable table, int row, EventDTO evento) {
        super(null, "Editar", new ImageIcon(EventTableEditController.class.getResource("/nuvola/editarx16.png")));
        this.table = table;
        this.row = row;
        this.evento = evento;
    }
    
    @Override
    public void doAction() {
        EventView eventView = new EventView();
        eventView.setModel(evento);
        eventView.setEditable(true);
        eventView.setAgreeController(new EventUpdateController(eventView));
        MainWindow.getInstance().addClosableView(evento.getName(), eventView);
    }
}