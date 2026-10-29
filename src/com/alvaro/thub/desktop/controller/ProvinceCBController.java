package com.alvaro.thub.desktop.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.alvaro.thub.model.ProvinceDTO;
import com.alvaro.thub.model.LocalityDTO;
import com.alvaro.thub.service.LocalityService;
import com.alvaro.thub.service.impl.LocalityServiceImpl;

public class ProvinceCBController extends AbstractController {
    
	private JComboBox provinceCB;
	private JComboBox localityCB;
    private String placeholderText;
    
    public ProvinceCBController(JComboBox provinceCB, JComboBox localityCB, String placeholderText) {
    	super();
		this.provinceCB = provinceCB;
		this.localityCB = localityCB;
    	this.placeholderText = placeholderText;
    }
    public void doAction() {
        ProvinceDTO selectedProvince = (ProvinceDTO) provinceCB.getSelectedItem();
        LocalityService localityService = new LocalityServiceImpl();
        
        if (selectedProvince != null && selectedProvince.getId() != null) {
            List<LocalityDTO> localidades = localityService.findByProvinceId(selectedProvince.getId());
            DefaultComboBoxModel<LocalityDTO> model = new DefaultComboBoxModel<>();
            
            LocalityDTO placeholder = new LocalityDTO();
            placeholder.setId(null);
            placeholder.setName(placeholderText);
            model.addElement(placeholder);
            
            for (LocalityDTO localidad : localidades) {
                model.addElement(localidad);
            }
            
             localityCB.setModel(model);
        } else {
             localityCB.setModel(new DefaultComboBoxModel<>());
        }
    }
}