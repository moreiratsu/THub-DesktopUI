package com.alvaro.thub.desktop.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.alvaro.thub.service.StatisticsService;
import com.alvaro.thub.service.impl.StatisticsServiceImpl;

public class ParticipantsByAgeChartView extends AbstractView {

    private StatisticsService statisticsService = null;
    
    // Componentes para filtros
    private JTextField matchIdTextField;
    private JTextField eventIdTextField;
    private JComboBox<String> tipoEstadisticaComboBox;
    private JPanel chartPanel;
    
    public ParticipantsByAgeChartView() {
        initialize();
        initServices();
        postInitialize();
    }
    
    private void initialize() {
    	setName("Participación por edad");
        setLayout(new BorderLayout(0, 0));
        
        JPanel contentPanel = new JPanel();
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        
        chartPanel = new JPanel();
        contentPanel.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setLayout(new BorderLayout(0, 0));
        
        JPanel filterPanel = new JPanel();
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // Tipo de estadística
        JLabel tipoLabel = new JLabel("Tipo:");
        filterPanel.add(tipoLabel);
        
        String[] tipos = {"Por Enfrentamiento", "Por Evento"};
        tipoEstadisticaComboBox = new JComboBox<>(tipos);
        filterPanel.add(tipoEstadisticaComboBox);
        
        // ID del enfrentamiento
        JLabel matchIdLabel = new JLabel("ID Enfrentamiento:");
        filterPanel.add(matchIdLabel);
        
        matchIdTextField = new JTextField(10);
        matchIdTextField.setToolTipText("Introduce el ID del enfrentamiento");
        filterPanel.add(matchIdTextField);
        
        // ID del evento
        JLabel eventIdLabel = new JLabel("ID Evento:");
        filterPanel.add(eventIdLabel);
        
        eventIdTextField = new JTextField(10);
        eventIdTextField.setToolTipText("Introduce el ID del evento");
        filterPanel.add(eventIdTextField);
        
        // Botón buscar
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarEstadisticas();
            }
        });
        buscarButton.setIcon(new ImageIcon(ParticipantsByAgeChartView.class.getResource("/nuvola/16x16/1910_resume_resume_tool_tool.png")));
        filterPanel.add(buscarButton);
        
        // Botón limpiar
        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        filterPanel.add(limpiarButton);
        
        // Establecer valores por defecto
        matchIdTextField.setText("1");
        eventIdTextField.setText("1");
        tipoEstadisticaComboBox.setSelectedIndex(0);
    }
    
    private void initServices() {
        statisticsService = new StatisticsServiceImpl();
    }
    
    private void postInitialize() {
        // Cargar estadísticas iniciales
        buscarEstadisticas();
    }
    
    private void buscarEstadisticas() {
        try {
            Map<String, Integer> participantesPorEdad = null;
            String tipo = (String) tipoEstadisticaComboBox.getSelectedItem();
            
            if ("Por Enfrentamiento".equals(tipo)) {
                // Obtener participantes por edad en un enfrentamiento
                Long matchId = Long.parseLong(matchIdTextField.getText().trim());
                participantesPorEdad = statisticsService.countParticipantsByAgeInMatch(matchId);
                
                if (participantesPorEdad == null || participantesPorEdad.isEmpty()) {
                    mostrarMensajeSinDatos("No se encontraron participantes para el enfrentamiento ID: " + matchId);
                    return;
                }
                
            } else {
                // Obtener participantes por edad en un evento
                Long eventId = Long.parseLong(eventIdTextField.getText().trim());
                participantesPorEdad = statisticsService.countParticipantsByAgeInEvent(eventId);
                
                if (participantesPorEdad == null || participantesPorEdad.isEmpty()) {
                    mostrarMensajeSinDatos("No se encontraron participantes para el evento ID: " + eventId);
                    return;
                }
            }
            
            // Crear el dataset con los resultados
            DefaultPieDataset ds = new DefaultPieDataset();
            int total = 0;
            
            for (Map.Entry<String, Integer> entry : participantesPorEdad.entrySet()) {
                if (entry.getValue() > 0) {
                    ds.setValue(entry.getKey(), entry.getValue());
                    total += entry.getValue();
                }
            }
            
            if (ds.getItemCount() == 0) {
                mostrarMensajeSinDatos("No hay participantes en los rangos de edad");
                return;
            }
            
            // Crear el gráfico
            String titulo = "Participantes por rango de edad";
            if ("Por Enfrentamiento".equals(tipo)) {
                titulo = "Participantes por edad - Enfrentamiento ID: " + matchIdTextField.getText();
            } else {
                titulo = "Participantes por edad - Evento ID: " + eventIdTextField.getText();
            }
            
            JFreeChart chart = ChartFactory.createPieChart(titulo, ds);
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionOutlinesVisible(false);
            plot.setNoDataMessage("No hay datos disponibles");
            plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})")); // Muestra: Rango: cantidad (porcentaje)
            
            ChartPanel panel = new ChartPanel(chart);
            panel.setMouseWheelEnabled(true);
            
            // Añadir leyenda con el total
            JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel totalLabel = new JLabel("Total de participantes: " + total);
            totalPanel.add(totalLabel);
            
            // Actualizar el panel central
            chartPanel.removeAll();
            chartPanel.add(panel, BorderLayout.CENTER);
            chartPanel.add(totalPanel, BorderLayout.SOUTH);
            chartPanel.revalidate();
            chartPanel.repaint();
            
        } catch (NumberFormatException e) {
            mostrarMensajeSinDatos("Error: Los IDs deben ser números válidos");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeSinDatos("Error al cargar las estadísticas: " + e.getMessage());
        }
    }
    
    private void mostrarMensajeSinDatos(String mensaje) {
        chartPanel.removeAll();
        JLabel label = new JLabel(mensaje, JLabel.CENTER);
        chartPanel.add(label, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private void limpiarCampos() {
        matchIdTextField.setText("1");
        eventIdTextField.setText("1");
        tipoEstadisticaComboBox.setSelectedIndex(0);
        buscarEstadisticas();
    }
}