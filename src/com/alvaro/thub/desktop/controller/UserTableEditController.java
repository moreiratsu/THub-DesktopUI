package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import com.alvaro.thub.desktop.MainWindow;
import com.alvaro.thub.desktop.views.EventView;
import com.alvaro.thub.desktop.views.UserView;
import com.alvaro.thub.model.EventDTO;
import com.alvaro.thub.model.UserDTO;

public class UserTableEditController extends AbstractController {

	private JTable table;
    private int row;
    private UserDTO user;
    
    public UserTableEditController(JTable table, int row, UserDTO user) {
        super(null, "Editar", new ImageIcon(UserTableEditController.class.getResource("/nuvola/editarx16.png")));
        this.table = table;
        this.row = row;
        this.user = user;
    }
    
    @Override
    public void doAction() {
        UserView userView = new UserView();
        userView.setModel(user);
        userView.setEditable(true);
        userView.setAgreeController(new UserUpdateController(userView));
        MainWindow.getInstance().addClosableView(user.getName(), userView);
    }

}
