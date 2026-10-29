package com.alvaro.thub.desktop.views;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeView extends AbstractView {

    public HomeView() {
        setName("Inicio");
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con gradiente
        JPanel tituloPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(204, 51, 51);   
                Color color2 = new Color(255, 102, 102);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        tituloPanel.setPreferredSize(new Dimension(800, 120));
        tituloPanel.setLayout(new GridBagLayout());
        
        // Texto principal
        JLabel textoLogo = new JLabel("THub Desktop");
        textoLogo.setFont(new Font("Segoe UI", Font.BOLD, 54));
        textoLogo.setForeground(Color.WHITE);
        
        // Texto secundario
        JLabel versionLabel = new JLabel("v1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        versionLabel.setForeground(new Color(204, 51, 51));
        
        // Panel para agrupar los textos
        JPanel textosPanel = new JPanel(new BorderLayout(5, 0));
        textosPanel.setOpaque(false);
        textosPanel.add(textoLogo, BorderLayout.CENTER);
        textosPanel.add(versionLabel, BorderLayout.EAST);
        
        tituloPanel.add(textosPanel);
        
        add(tituloPanel, BorderLayout.NORTH);
        
        // Panel de novedades
        JPanel novedadesPanel = new JPanel(new BorderLayout(10, 10));
        novedadesPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        
        JLabel tituloNovedades = new JLabel("📋 Últimas novedades");
        tituloNovedades.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tituloNovedades.setForeground(new Color(204, 51, 51));
        tituloNovedades.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        novedadesPanel.add(tituloNovedades, BorderLayout.NORTH);
        
        JTextArea novedadesTA = new JTextArea();
        novedadesTA.setEditable(false);
        novedadesTA.setFont(new Font("Consolas", Font.PLAIN, 13));
        novedadesTA.setBackground( Color.black);
        novedadesTA.setMargin(new Insets(20, 20, 20, 20));
        novedadesTA.setText(getNovedadesTexto());
        
        JScrollPane scrollPane = new JScrollPane(novedadesTA);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240), 2));
        novedadesPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(novedadesPanel, BorderLayout.CENTER);
    }
    
    private String getNovedadesTexto() {
        String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        return "══════════════════════════════════════════════════════════\n" +
               "               THub Desktop - Gestión de Eventos          \n" +
               "══════════════════════════════════════════════════════════\n" +
               "\n" +
               "Fecha: " + fechaActual + "\n" +
               "\n" +
               "✦ NOVEDADES DE LA VERSIÓN ✦\n" +
               "  • Gestión completa de BÚSQUEDA y CREACIÓN de EVENTOS\n" +
               "  • REGISTRO y BÚSQUEDA de participantes\n" +
               "  • CREACIÓN Y BÚSQUEDA de equipos\n" +
               "  • Resultados en JTable con renderers personalizados\n" +
               "\n" +
               "✦ Marzo/Abril 2026 ✦\n" +
               "  • Panel de estadísticas \n" +
               "  • Controladores para todas las acciones en una VISTA\n" +
               "\n" +
               "✦ PRÓXIMAMENTE ✦\n" +
               "  • Multiples idiomas (EN, ES, FR)\n" +
               "  • Modo Oscuro / Claro\n" +
               "  • Integración con IA para recomendaciones\n" +
               "\n" +
               "──────────────────────────────────────────────────────────\n" +
               "         ¡Gracias por usar THub Desktop!";
    }
}