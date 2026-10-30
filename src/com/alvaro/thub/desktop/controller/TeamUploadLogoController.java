package com.alvaro.thub.desktop.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alvaro.thub.desktop.views.TeamCreateView;
import com.alvaro.thub.utils.FileUtils;

public class TeamUploadLogoController extends AbstractController{
	
	private TeamCreateView teamView = null;

	public TeamUploadLogoController(TeamCreateView teamView) {
		this.teamView = teamView;
		// Establece el texto del botón para que no colapse al aplicar la Action
	    putValue(NAME, "Adjuntar Foto");
	}

	public void doAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png"));

		int option = fileChooser.showOpenDialog(teamView);
		if (option == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				// 1. Leer los bytes usando la utilidad del middleware
				byte[] logoBytes = FileUtils.readFile(selectedFile.getAbsolutePath());

				// 2. Extraer la extensión del archivo (.png, .jpg, etc.)
				String name = selectedFile.getName();
				String extension = name.substring(name.lastIndexOf("."));

				// 3. Crear el icono redimensionado (64x64)
				ImageIcon icon = new ImageIcon(logoBytes);
				Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);

				// 4. Enviar los datos del logo a la vista
				teamView.setLogoData(logoBytes, extension, new ImageIcon(scaledImage));

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(teamView, "Error al cargar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		doAction();
	}

}
