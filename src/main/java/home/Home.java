package home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class Home extends JFrame {
    public Home() throws MalformedURLException {
        // Configurar el JFrame principal
        setTitle("Sistema de Gestión de Proyectos");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Crear botones para abrir cada formato
        JButton jsonButton = new JButton("Visualizar JSON");
        JButton xmlButton = new JButton("Visualizar XML");
        JButton csvButton = new JButton("Visualizar CSV");

        // Personalización del tamaño y color inicial de los botones
        configureButton(jsonButton);
        configureButton(xmlButton);
        configureButton(csvButton);

        // Agregar los botones al panel
        buttonsPanel.add(jsonButton);
        buttonsPanel.add(xmlButton);
        buttonsPanel.add(csvButton);

        // Agregar el panel de botones a la parte superior del JFrame
        add(buttonsPanel, BorderLayout.NORTH);

        // Crear el panel de contenido de 400x400 para la imagen y el texto
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(400, 400));
        contentPanel.setBackground(Color.LIGHT_GRAY);

        // Crear y añadir la imagen
        ImageIcon icon = new ImageIcon(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWjP-5-AjiwguH_PcdhRZL1dUBCMnhcfm4rg&s"));  // Asegúrate de poner la ruta de tu imagen
        JLabel imagenLabel = new JLabel(icon);
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Centrar horizontalmente
        contentPanel.add(imagenLabel, BorderLayout.WEST);

        // Crear el panel para el texto
        JPanel textPanel = new JPanel(new GridBagLayout());  // Usar GridBagLayout para centrar verticalmente
        textPanel.setBackground(Color.LIGHT_GRAY);

        // Crear el JLabel para las líneas de texto
        JLabel textLabel = new JLabel("<html>Línea 1<br>Línea 2<br>Línea 3<br>Línea 4</html>");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setForeground(Color.BLACK);

        // Agregar el texto centrado en el panel de texto
        textPanel.add(textLabel);

        // Agregar el panel de texto a la derecha del panel de contenido
        contentPanel.add(textPanel, BorderLayout.CENTER);

        // Agregar el panel de contenido al JFrame
        add(contentPanel, BorderLayout.CENTER);

        // Configurar acciones para cada botón
        jsonButton.addActionListener(e -> new JSONInterface().setVisible(true));
        xmlButton.addActionListener(e -> new XMLInterface().setVisible(true));
        csvButton.addActionListener(e -> new CSVInterface().setVisible(true));
    }

    // Método para configurar el tamaño y el efecto hover de un botón
    private void configureButton(JButton boton) {
        boton.setPreferredSize(new Dimension(150, 50));  // Establecer tamaño preferido del botón
        boton.setBackground(new Color(211, 211, 211));  // Color de fondo inicial (Light Gray en RGB)
        boton.setForeground(new Color(0, 0, 0));  // Color del texto (Negro en RGB)

        // Agregar efecto hover con colores RGB
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(0, 255, 0));  // Color hover (Verde en RGB)
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(211, 211, 211));  // Color original (Light Gray en RGB)
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Home().setVisible(true);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
