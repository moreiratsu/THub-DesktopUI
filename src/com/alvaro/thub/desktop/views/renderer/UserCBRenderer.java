package com.alvaro.thub.desktop.views.renderer;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.alvaro.thub.model.UserDTO;

public class UserCBRenderer extends DefaultListCellRenderer {

    public UserCBRenderer() {
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if (value != null && value instanceof UserDTO) {
            UserDTO user = (UserDTO) value;
            if (user.getId() == null) {
                this.setText(user.getName() + " " + user.getLastName1()); // Placeholder
            } else {
                this.setText(user.getName() + " " + user.getLastName1());
            }
        } else {
            this.setText("Seleccionar usuario");
        }
        return this;
    }
}